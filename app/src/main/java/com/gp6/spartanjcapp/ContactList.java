package com.gp6.spartanjcapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ContactList extends ArrayAdapter<Contact>{
    private Activity context;
    List<Contact> contacts;

    public ContactList(Activity context, List<Contact> artists) {
        super(context, R.layout.layout_contact_list, artists);
        this.context = context;
        this.contacts = artists;
    }


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
