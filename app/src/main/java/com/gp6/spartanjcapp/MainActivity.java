package com.gp6.spartanjcapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureLoginButton();
        configureCreateAccountButton();
    }

    /**
     * This method defines the action of the 'Log in' button.
     * Switches activity to log in screen
     *
     * TODO: Check if username and password is correct
     */
    private void configureLoginButton(){
        Button logInButton = (Button) findViewById(R.id.loginButton);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HomePage.class));
            }
        });
    }

    /**
     * This method defines the 'Create an account' button.
     * Switches activity to sign up screen
     */
    private void configureCreateAccountButton(){
        Button signUpButton = (Button) findViewById(R.id.createAccountButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUpScreen.class));
            }
        });
    }
}
