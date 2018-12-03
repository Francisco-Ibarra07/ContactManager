package com.gp6.spartanjcapp;

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


/**
 * This class defines the screen that gives a user the ability to add a contact manually.
 * The information inputted by the user on this screen is used by Firebase to make a new account.
 * The new contact information is checked by both our code and Firebase to validate it.
 * Once validated, the new contact is displayed on the home page
 *
 */
public class AddContactScreen extends AppCompatActivity {

    //Firebase variables
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private DatabaseReference myDatabase;

    //User input variables
    private Button finishButton;
    private Button backButton;
    private EditText newContactPhoneNumber;
    private EditText newContactFirstName;
    private EditText newContactLastName;
    private EditText newContactEmailAddress;

    /**
     * This is a default method used by Android Studio in order to create this screen
     *
     * @param savedInstanceState default Android Studio variable
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact_screen);


        /**
         * This method initializes all instance variables defined above
         */
        initializeInstanceVariables();

        /**
         * When the finish button is pressed, a call is made to verify the user information AND
         * another call is made to Firebase to push the validated data onto the database.
         *
         */
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

        /**
         * This is a simple button that when pressed, it sends the user back to the home page
         *
         */
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * This method checks the input of the user's email, password, first name, and last name.
     * It checks for any errors and checks to see if sign up credentials are correct.
     * If correct, user is taken to Home Page of the app.
     *
     * NOTE: On our code, we really only check to see if none
     * @return true if input meets the requirements
     */
    private boolean validateInputInfo(){

        //Get user input
        String firstName = newContactFirstName.getText().toString();
        String lastName = newContactLastName.getText().toString();
        String email = newContactEmailAddress.getText().toString();
        String phone = newContactPhoneNumber.getText().toString();

        //Check to make sure no boxes were left empty
        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty()){
            Toast.makeText(this, "You must fill all fields before continuing!", Toast.LENGTH_LONG).show();
            return false;
        }
        //Check to see if phone number is valid
        else if(!(phone.length() >= 10)){
            Toast.makeText(this, "Make sure to input a valid 10 digit phone number!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }


    /**
     * This method initializes all instance variables defined above
     *
     */
    private void initializeInstanceVariables(){

        //Initialize Firebase variables
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        myDatabase = FirebaseDatabase.getInstance().getReference(firebaseAuth.getUid());

        //Initialize input variables
        newContactPhoneNumber = findViewById(R.id.addcontact_phonenumber);
        finishButton = findViewById(R.id.addcontact_finishbttn);
        backButton = findViewById(R.id.addContactScreenBackBttn);
        newContactFirstName = ((EditText) findViewById(R.id.addcontact_firstname));
        newContactLastName = ((EditText) findViewById(R.id.addcontact_lastname));
        newContactEmailAddress = ((EditText) findViewById(R.id.addcontact_email));
    }
}
