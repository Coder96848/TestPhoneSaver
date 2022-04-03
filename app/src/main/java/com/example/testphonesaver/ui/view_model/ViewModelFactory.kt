package com.example.testphonesaver.ui.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testphonesaver.util.ContactPhoneValidator

class ViewModelFactory(
    private val application: Application,
    private val validator: ContactPhoneValidator
) : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ViewModel(application, validator) as T
    }
}