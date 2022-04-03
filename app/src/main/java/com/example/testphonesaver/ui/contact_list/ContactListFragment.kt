package com.example.testphonesaver.ui.contact_list

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testphonesaver.MainActivity
import com.example.testphonesaver.R
import com.example.testphonesaver.model.Contact
import com.example.testphonesaver.util.CommonConsts
import com.example.testphonesaver.util.ContactListAdapter
import com.example.testphonesaver.util.ContactPhoneValidator
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ContactListFragment : Fragment() {

    companion object {
        fun newInstance() = ContactListFragment()
    }

    private lateinit var viewModel: ContactViewModel
    private lateinit var contactListRecyclerView: RecyclerView
    private lateinit var addContactButton: FloatingActionButton
    private lateinit var contactListObserver: Observer<MutableList<Contact>>
    private val adapter = ContactListAdapter()
    private val validator = ContactPhoneValidator()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.contact_list_fragment, container, false)

        contactListRecyclerView = view.findViewById(R.id.contactListRecyclerView)

        contactListRecyclerView.layoutManager = LinearLayoutManager(
            context,
            RecyclerView.VERTICAL,
            false
        )

        adapter.setOnItemViewLongClicked(object : ContactListAdapter.ItemViewLongClickListener {
            override fun onItemViewLongClicked(position: Int) {

                val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
                builder.setTitle(getString(R.string.warning))
                builder.setMessage(getString(R.string.remove_question))

                builder.setPositiveButton(getString(R.string.positive_answer)) { dialog, id ->
                    val item = adapter.getItem(position)
                    if (item != null) {
                        viewModel.removeContact(item)
                    }
                    dialog.dismiss()
                }

                builder.setNegativeButton(getString(R.string.negative_answer)) { dialog, id ->
                    dialog.dismiss()
                }
                builder.setCancelable(true)

                builder.create().show()
            }
        })

        contactListRecyclerView.adapter = adapter

        addContactButton = view.findViewById(R.id.addButton)
        addContactButton.setOnClickListener {
            findNavController().navigate(R.id.action_contactListFragment_to_newContactFragment)
        }



        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            activity as MainActivity,
            ContactViewModelFactory(activity?.application!!, validator)
        ).get(ContactViewModel::class.java)

        contactListObserver = Observer<MutableList<Contact>> {
            adapter.changeItems(it)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.contactList.observe(this.viewLifecycleOwner, contactListObserver)
    }

    override fun onStop() {
        super.onStop()
        viewModel.contactList.removeObserver(contactListObserver)
    }
}