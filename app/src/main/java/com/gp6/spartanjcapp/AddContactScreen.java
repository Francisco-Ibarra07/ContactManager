package com.gp6.spartanjcapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddContactScreen extends AppCompatActivity {

    //Firebase variables
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private DatabaseReference myDatabase;

    //User input variables
    private Button finishButton;
    private EditText newContactPhoneNumber;
    private EditText newContactFirstName;
    private EditText newContactLastName;
    private EditText newContactEmailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact_screen);


        initializeInstanceVariables();

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check if input is valid first
                if(validateInputInfo()){

                    //Get text input
                    String firstName = newContactFirstName.getText().toString();
                    String lastName = newContactLastName.getText().toString();
                    String email = newContactEmailAddress.getText().toString();
                    String phone = newContactPhoneNumber.getText().toString();

                    //Create contact object
                    final Contact addMe = new Contact(firstName, lastName, email, phone);

                    //Get unique ID in database to store Contact object
                    String id = myDatabase.push().getKey();

                    //Add to database
                    myDatabase.child(id).setValue(addMe).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //If successful display message and return to home page
                            if(task.isSuccessful()){
                                Toast.makeText(getBaseContext(), "New contact " + addMe.getDisplayName() + " created!", Toast.LENGTH_LONG).show();

                                //Go to home page
                                finish();

                            }
                            else {
                                Toast.makeText(getBaseContext(), "Unable to add " + addMe.getDisplayName() + " contact", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private boolean validateInputInfo(){

        String firstName = newContactFirstName.getText().toString();
        String lastName = newContactLastName.getText().toString();
        String email = newContactEmailAddress.getText().toString();
        String phone = newContactPhoneNumber.getText().toString();

        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty()){
            Toast.makeText(this, "You must fill all fields before continuing!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }


    private void initializeInstanceVariables(){

        //Initialize Firebase variables
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        myDatabase = FirebaseDatabase.getInstance().getReference(firebaseAuth.getUid());

        //Initialize input variables
        newContactPhoneNumber = findViewById(R.id.addcontact_phonenumber);
        finishButton = findViewById(R.id.addcontact_finishbttn);
        newContactFirstName = ((EditText) findViewById(R.id.addcontact_firstname));
        newContactLastName = ((EditText) findViewById(R.id.addcontact_lastname));
        newContactEmailAddress = ((EditText) findViewById(R.id.addcontact_email));
    }

}
