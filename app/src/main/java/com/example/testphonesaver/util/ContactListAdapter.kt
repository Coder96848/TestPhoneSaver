package com.example.testphonesaver.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.testphonesaver.R
import com.example.testphonesaver.model.Contact

class ContactListAdapter: RecyclerView.Adapter<ContactListAdapter.ContactListViewHolder>() {

    private var onLongClickListener: ItemViewLongClickListener? = null
    private var contacts: MutableList<Contact> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_list_item, parent, false)

        return ContactListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactListViewHolder, position: Int) {
        holder.contactName.text = contacts.toList()[position].name
        holder.contactPhone.text = contacts.toList()[position].phone
        holder.itemFrame.setOnLongClickListener {
            onLongClickListener?.onItemViewLongClicked(position)
            true
        }
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    fun getItem(position: Int): Contact? {
        return if (position < 0 || position >= itemCount) null else contacts.toList()[position]
    }

    fun changeItems(newContacts: MutableList<Contact>) {
        contacts = newContacts
        notifyDataSetChanged()
    }

    fun setOnItemViewLongClicked(listener: ItemViewLongClickListener) {
        onLongClickListener = listener
    }

    class ContactListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val contactName: TextView = itemView.findViewById(R.id.contactName)
        val contactPhone: TextView = itemView.findViewById(R.id.contactPhone)
        val itemFrame: LinearLayout = itemView.findViewById(R.id.itemFrame)
    }

    interface ItemViewLongClickListener {
        fun onItemViewLongClicked(position: Int)
    }

}