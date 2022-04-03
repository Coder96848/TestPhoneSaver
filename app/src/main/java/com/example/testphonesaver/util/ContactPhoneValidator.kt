package com.example.testphonesaver.util

import com.example.testphonesaver.model.Contact

class ContactPhoneValidator {

    fun validate(contact: Contact, contacts: MutableList<Contact>): ValidateEnum {

        if (contact.name.isEmpty() || contact.phone.isEmpty()) return ValidateEnum.EMPTY_FIELDS
        if (verifyPhoneNumber(contact.phone)) return ValidateEnum.WRONG_PHONE
        if (contacts.find { it.phone == contact.phone } != null) return ValidateEnum.SAME_PHONES

        return ValidateEnum.VALIDATED
    }

    private fun verifyPhoneNumber(phone: String): Boolean {
        return !phone.substring(1).any { it.isDigit() }
                || !phone.startsWith("+7")
                || phone.length != 12
    }
}
