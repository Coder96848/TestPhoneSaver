package com.example.testphonesaver

import com.example.testphonesaver.model.Contact
import com.example.testphonesaver.util.ContactPhoneValidator
import com.example.testphonesaver.util.ValidateEnum
import org.junit.Test

import org.junit.Assert.*

class UnitTest {

    private val contact = Contact("Name", "+79990002211")
    private val contact2 = Contact("Name", "+71112223344")
    private val contactEmpty = Contact("", "")
    private val contactEmptyName = Contact("", "+79990002211")
    private val contactEmptyPhone = Contact("Name", "")
    private val contactWrongPhonePlus = Contact("Name", "111111111111")
    private val contactWrongPhoneSeven = Contact("Name", "+11111111111")
    private val contactWrongPhoneSize = Contact("Name", "+71291")
    private val contactWrongPhoneLiteral = Contact("Name", "ваымвам")
    private val contacts = mutableListOf<Contact>()
    private val contactPhoneValidator = ContactPhoneValidator()


    @Test
    fun validateCorrectNumberTest() {
        assertEquals(ValidateEnum.VALIDATED, contactPhoneValidator.validate(contact, contacts))
        assertEquals(ValidateEnum.VALIDATED, contactPhoneValidator.validate(contact2, contacts))
    }


    @Test
    fun validateNotCorrectNumberTest() {
        assertEquals(
            ValidateEnum.WRONG_PHONE,
            contactPhoneValidator.validate(contactWrongPhonePlus, contacts)
        )

        assertEquals(
            ValidateEnum.WRONG_PHONE,
            contactPhoneValidator.validate(contactWrongPhoneSeven, contacts)
        )

        assertEquals(
            ValidateEnum.WRONG_PHONE,
            contactPhoneValidator.validate(contactWrongPhoneSize, contacts)
        )

        assertEquals(
            ValidateEnum.WRONG_PHONE,
            contactPhoneValidator.validate(contactWrongPhoneLiteral, contacts)
        )
    }

    @Test
    fun validateEmptyTest() {
        assertEquals(
            ValidateEnum.EMPTY_FIELDS,
            contactPhoneValidator.validate(contactEmpty, contacts)
        )

        assertEquals(
            ValidateEnum.EMPTY_FIELDS,
            contactPhoneValidator.validate(contactEmptyName, contacts)
        )

        assertEquals(
            ValidateEnum.EMPTY_FIELDS,
            contactPhoneValidator.validate(contactEmptyPhone, contacts)
        )
    }

    @Test
    fun validateSameNumberTest() {
        contacts.add(contact)
        assertEquals(ValidateEnum.SAME_PHONES, contactPhoneValidator.validate(contact, contacts))
        assertEquals(ValidateEnum.VALIDATED, contactPhoneValidator.validate(contact2, contacts))
    }
}