package com.gp6.spartanjcapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ContactView extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);

        /*
            TODO
                Add back button
                Edit on top right
                Notes
                Add favorites
         */

        String[] array = {"Francisco Ibarra","+1234567890", "test@mail.com",};
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, array);
        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);
    }

}
