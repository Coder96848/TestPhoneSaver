package com.example.testphonesaver.ui.contact_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.testphonesaver.model.Contact
import com.example.testphonesaver.util.ContactPhoneValidator
import com.example.testphonesaver.util.ValidateEnum

class ContactViewModel(
    application: Application,
    private val validator: ContactPhoneValidator
) : AndroidViewModel(application) {

    val savedContact = MutableLiveData<Contact>()
    val contactList = MutableLiveData<MutableList<Contact>>()
    val warning = MutableLiveData<ValidateEnum>()

    init {
        contactList.value = mutableListOf()
        warning.value = ValidateEnum.VALIDATED
    }

    fun addContact(newContact: Contact) {
        savedContact.value = newContact
        val contacts = contactList.value ?: mutableListOf()
        warning.value = validator.validate(newContact, contacts)
        if (validator.validate(newContact, contacts) == ValidateEnum.VALIDATED) {
            contacts.add(newContact)
            contactList.value = contacts
        }
    }

    fun removeContact(contact: Contact) {
        val contacts = contactList.value ?: mutableListOf()
        contacts.remove(contact)
        contactList.value = contacts
    }
}