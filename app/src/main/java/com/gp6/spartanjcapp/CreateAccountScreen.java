package com.gp6.spartanjcapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.UserProfileChangeRequest;

public class CreateAccountScreen extends AppCompatActivity {


    private Button finishButton;
    private EditText phoneNumber;
    private EditText firstName;
    private EditText lastName;
    private EditText emailAddress;
    private EditText password;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_screen);

        //Initialize instance variables
        initializeUserInputVariables();

        //Initializes Firebase authorization
        firebaseAuth = FirebaseAuth.getInstance();

        //When "Finish" button is pressed
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Validate sign up information first once "Finish" button is pressed
                if(validateSignUpInput())
                    //Create user account on firebase and send user to homepage if account is
                    //successfully created
                    createUserAccount();
            }
        });
    }

    /**
     * This method is called in order to send the user to the home page. This will happen when
     * the user has successfully created an account.
     *
     */
    private void openHomePage(){
        Toast.makeText(this, "Account created!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    /**
     * This method simply initializes all the instance variables to their respective button/text boxes.
     * Whatever the user inputs into the text boxes can be accessed via these instance variables
     *
     */
    private void initializeUserInputVariables(){
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        finishButton = findViewById(R.id.finishButton);
        firstName = ((EditText) findViewById(R.id.firstName));
        lastName = ((EditText) findViewById(R.id.lastName));
        emailAddress = ((EditText) findViewById(R.id.emailAddress));
        password = ((EditText) findViewById(R.id.password));
    }

    /**
     * This methods checks for only three things:
     *  1) To see if email box was not left empty
     *  2) To see if password input was at least 6 characters long
     *  3) To see if the phone number that was inputted is not left blank and is at least 10 numbers long (###)###-####
     *
     * The rest of the input is checked by Firebase itself. Firebase will throw an exception
     * if there is anything wrong with these two inputs
     *
     * @return true if both checks are passed
     */
    private boolean validateSignUpInput(){

        String first = firstName.getText().toString();
        String last = lastName.getText().toString();
        String email = emailAddress.getText().toString();
        String pass = password.getText().toString();

        //Check to make sure fields are not empty when information is submitted
        if(first.isEmpty() || last.isEmpty() || email.isEmpty() || pass.isEmpty()){
            Toast.makeText(this, "You must fill all fields before continuing!", Toast.LENGTH_LONG).show();
            return false;
        }

        //Check to make sure is at least 6 characters long
        //Note: Reason being because Firebase requires a password of at least 6 characters
        else if(!(password.length() >= 6)) {
            Toast.makeText(this, "Your password must be at least 6 characters long!", Toast.LENGTH_LONG).show();
            return false;
        }

        //Check to make sure a phone number was inputted
        else if(!(phoneNumber.length() >= 10)) {
            Toast.makeText(this, "Be sure to input your phone number correctly! (###)###-####", Toast.LENGTH_LONG).show();
            return false;
        }
        //Return true if all tests are passed
        else
            return true;
    }

    /**
     * This methods calls Firebase function 'createUserWithEmailAndPassword()' in order to create an account with the inputted data
     *
     * If successful:
     *  - User will have a new account on Firebase and will be taken to the Home page
     *
     * If unsuccessful:
     *  - Firebase will throw an exception with a message of what was wrong with the user's input
     *  - The message will be logged and displayed on the screen for the user to see
     */
    private void createUserAccount() {
        firebaseAuth.createUserWithEmailAndPassword(emailAddress.getText().toString(), password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    firebaseUser.updateEmail(emailAddress.getText().toString());
                    firebaseUser.updatePassword(password.getText().toString());

                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                            .setDisplayName(firstName.getText().toString() + lastName.getText().toString())
                            .build();

                    firebaseUser.updateProfile(profileChangeRequest);

                    //Take user to home page of application and prompt success message
                    openHomePage();
                } else {
                    //If it fails, display an error message to the user
                    Log.w("createUser:failure", task.getException());
                }
            }
        });
    }
}
