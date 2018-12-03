package com.gp6.spartanjcapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * This class defines the startup login page when the app is first opened.
 * It uses Firebase in order to authenticate a user that is trying to log into his/her account.
 * This class also gives the user the option to create a new account; which means a new activity
 * is called.
 */
public class MainActivity extends AppCompatActivity {

    //Instance variables to store user input
    private EditText userEmail, userPassword;
    private Button loginButton;
    private TextView createAccount;

    //Holds a key in order to access Firebase
    private FirebaseAuth firebaseAuth;

    /**
     * This is a default method used by Android Studio in order to create this screen
     *
     * @param savedInstanceState default Android Studio variable
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializes all instance variables
        initializeUserInputVariables();

        //Initializes Firebase authorization
        firebaseAuth = FirebaseAuth.getInstance();

        //If login button pressed, validate credentials
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //When the user clicks 'login', if credentials are correct the user will be
                //taken to the home page
                validateLoginCredentials();
            }
        });

        //If 'create an account' button is pressed, switch to account sign up activity
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateAccountScreen();
            }
        });
    }

    /**
     * This method initializes all instance variables.
     * Instance variables include all buttons/textboxes that the user can
     * fill in or press.
     */
    private void initializeUserInputVariables(){
        userEmail = (EditText)findViewById(R.id.userEmailInput);
        userPassword = (EditText)findViewById(R.id.userPasswordInput);
        loginButton = (Button)findViewById(R.id.loginButton);
        createAccount = (TextView) findViewById(R.id.createAccountTextView);
    }

    /**
     * This method checks the input of the user's email and password.
     * Checks for any errors and checks to see if login credentials are correct.
     * If correct, user is taken to Home Page of the app
     */
    private void validateLoginCredentials(){

        //Get user input
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();

        //Check to make sure that BOTH user email and password are filled in
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Be sure to input both your username and password!", Toast.LENGTH_LONG).show();
            return;
        }

        //Check to see if password is at least 6 characters long (Required to be able to store in Firebase)
        if(password.length() < 6){
            Toast.makeText(this, "Password must be at least 6 characters long!", Toast.LENGTH_LONG).show();
            return;
        }

        //Check to see if login credentials are correct on Firebase
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(getBaseContext(), "Authentication failed. Check username or password!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getBaseContext(), "Authentication Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, HomePage.class));
                }
            }
        });
    }

    /**
     * This method is used to send the user to a new screen in which they can use to make a
     * new account.
     *
     */
    private void openCreateAccountScreen(){
        Intent intent = new Intent(this, CreateAccountScreen.class);
        startActivity(intent);
    }
}
