package com.example.testphonesaver.ui.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.testphonesaver.model.Contact
import com.example.testphonesaver.util.ContactPhoneValidator
import com.example.testphonesaver.util.ValidateEnum

class ViewModel(
    application: Application,
    private val validator: ContactPhoneValidator
) : AndroidViewModel(application) {

    val savedContact = MutableLiveData<Contact>()
    val contactList = MutableLiveData<MutableList<Contact>>()
    val warning = MutableLiveData<ValidateEnum>()

    init {
        contactList.value = mutableListOf()
        warning.value = ValidateEnum.UNDEFINE
    }

    fun addContact(newContact: Contact) {
        savedContact.value = newContact
        val contacts = contactList.value ?: mutableListOf()
        val validated = validator.validate(newContact, contacts)
        warning.value = validated
        if (validated == ValidateEnum.VALIDATED) {
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