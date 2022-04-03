package com.example.testphonesaver.ui

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.testphonesaver.MainActivity
import com.example.testphonesaver.R
import com.example.testphonesaver.model.Contact
import com.example.testphonesaver.ui.view_model.ViewModel
import com.example.testphonesaver.util.ValidateEnum

class NewContactFragment : Fragment() {

    companion object {
        fun newInstance() = NewContactFragment()
    }

    private lateinit var viewModel: ViewModel
    private lateinit var editContactName: EditText
    private lateinit var editContactPhone: EditText
    private lateinit var saveButton: Button
    private lateinit var warningObserver: Observer<ValidateEnum>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.new_contact_fragment, container, false)

        editContactName = view.findViewById(R.id.editContactName)
        editContactPhone = view.findViewById(R.id.editContactPhone)
        saveButton = view.findViewById(R.id.saveContactButton)
        saveButton.setOnClickListener {
            viewModel.addContact(
                Contact(
                    editContactName.text.toString(),
                    editContactPhone.text.toString()
                )
            )
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider((activity as MainActivity)).get(ViewModel::class.java)

        warningObserver = Observer<ValidateEnum> {

            val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
            builder.setTitle(getString(R.string.warning))
            builder.setPositiveButton(getString(R.string.ok)) { dialog, id -> dialog.dismiss() }
            builder.setNeutralButton(getString(R.string.return_anyway)) { dialog, id ->
                dialog.dismiss()
                findNavController().navigate(R.id.action_newContactFragment_to_contactListFragment)
            }
            builder.setCancelable(true)

            when (viewModel.warning.value) {
                ValidateEnum.VALIDATED -> {
                    findNavController().navigate(R.id.action_newContactFragment_to_contactListFragment)
                    viewModel.warning.value = ValidateEnum.UNDEFINE
                }
                ValidateEnum.WRONG_PHONE -> {
                    builder.setMessage(getString(R.string.wrong_phone_number))
                    builder.create().show()
                    viewModel.warning.value = ValidateEnum.UNDEFINE
                }
                ValidateEnum.EMPTY_FIELDS -> {
                    builder.setMessage(getString(R.string.empty_fields))
                    builder.create().show()
                    viewModel.warning.value = ValidateEnum.UNDEFINE
                }
                ValidateEnum.SAME_PHONES -> {
                    builder.setMessage(getString(R.string.same_number))
                    builder.create().show()
                    viewModel.warning.value = ValidateEnum.UNDEFINE
                }
                ValidateEnum.UNDEFINE -> {

                }
                null -> {

                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.warning.observe(this.viewLifecycleOwner, warningObserver)
    }

    override fun onStop() {
        super.onStop()
        viewModel.warning.removeObserver(warningObserver)
    }

}