package com.example.testphonesaver.ui.contact_list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testphonesaver.util.ContactPhoneValidator

class ContactViewModelFactory(
    private val application: Application,
    private val validator: ContactPhoneValidator
) : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ContactViewModel(application, validator) as T
    }
}