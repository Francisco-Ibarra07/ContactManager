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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText userEmail, userPassword;
    private Button loginButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUserInputVariables();

        firebaseAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //When the user clicks 'login', if credentials are correct the user will be
                //taken to the home page
                validateLoginCredentials();
            }
        });
    }

    private void initializeUserInputVariables(){
        userEmail = (EditText)findViewById(R.id.userEmailInput);
        userPassword = (EditText)findViewById(R.id.userPasswordInput);
        loginButton = (Button)findViewById(R.id.loginButton);
    }

    /**
     * This method checks the input of the user's email and password.
     * Checks for any errors and checks to see if login credentials are correct.
     *
     */
    private void validateLoginCredentials(){

        //Get user input
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();

        //Check to make sure that BOTH user email and password are filled in
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Be sure to input both your username and password!", Toast.LENGTH_SHORT).show();
            return;
        }

        //Check to see if login credentials are correct on firebase
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
}
