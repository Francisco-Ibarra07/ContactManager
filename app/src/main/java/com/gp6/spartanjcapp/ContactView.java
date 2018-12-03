package com.gp6.spartanjcapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ContactView extends AppCompatActivity {

    private Button backButton;
    private TextView displayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);

        backButton = findViewById(R.id.contactViewReturnButton);
        displayName = findViewById(R.id.contactsInfoTextView);

        /*
            TODO
                Add back button
                Edit on top right
                Notes
                Add favorites
         */
        String contactPhoneNumber = getIntent().getStringExtra("CONTACT_VIEW_PHONE");
        String contactEmail = getIntent().getStringExtra("CONTACT_VIEW_EMAIL");
        String contactFirstName = getIntent().getStringExtra("CONTACT_VIEW_FIRSTNAME");
        String contactLastName = getIntent().getStringExtra("CONTACT_VIEW_LASTNAME");


        //Contact's display name
        displayName.setText(contactFirstName + " " + contactLastName + "'s Contact Info");

        String[] array = {"First Name: " + contactFirstName, "Last Name: " + contactLastName, "+" + contactPhoneNumber, contactEmail};
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, array);
        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
