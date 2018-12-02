package com.gp6.spartanjcapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AddContactScreen extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private Button finishButton;
    private EditText phoneNumber;
    private EditText firstName;
    private EditText lastName;
    private EditText emailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact_screen);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();



    }


    private void initializeUserInputVariables(){

        phoneNumber = findViewById(R.id.addcontact_phonenumber);
        finishButton = findViewById(R.id.addcontact_finishbttn);
        firstName = ((EditText) findViewById(R.id.addcontact_firstname));
        lastName = ((EditText) findViewById(R.id.addcontact_lastname));
        emailAddress = ((EditText) findViewById(R.id.addcontact_email));
    }

}
