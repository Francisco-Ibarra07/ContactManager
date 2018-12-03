package com.gp6.spartanjcapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * This class defines a ContactList type that stores multiple contacts in one data structure.
 * It extends the ArrayAdapter<> class in order to display this contact list in a neat fashion on
 * the home page
 *
 */
public class ContactList extends ArrayAdapter<Contact>{
    //Instance variables
    private Activity context;
    List<Contact> contacts;

    /**
     * Constructor for a ContactList object which also makes a call to the
     * super class (Array Adapter)
     *
     * @param context The current context
     * @param artists List of contacts to be passed into ArrayAdapter
     */
    public ContactList(Activity context, List<Contact> artists) {
        super(context, R.layout.layout_contact_list, artists);
        this.context = context;
        this.contacts = artists;
    }


    /**
     * This method is used in order to make a Contact List object displayable when using
     * Array Adapters. In our case, on the home page a Contact List object is displayed in the
     * format:
     *      "Display Name"
     *          "+Phone number"
     * @param position index in array
     * @param convertView default value used by Android Studio
     * @param parent default value used by Android Studio
     * @return formatted list of contacts
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_contact_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewDisplayName);
        TextView textViewGenre = (TextView) listViewItem.findViewById(R.id.textViewPhoneNumber);

        Contact currentContact = contacts.get(position);
        textViewName.setText(currentContact.getDisplayName());
        textViewGenre.setText("+" + currentContact.getPhoneNumber());

        return listViewItem;
    }
}
