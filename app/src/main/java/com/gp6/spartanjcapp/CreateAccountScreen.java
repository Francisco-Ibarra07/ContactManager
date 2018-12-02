package com.gp6.spartanjcapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;

public class CreateAccountScreen extends AppCompatActivity {

    private Button finishButton;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_screen);

        finishButton = findViewById(R.id.finishButton);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName = ((EditText) findViewById(R.id.firstName)).getText().toString();
                lastName = ((EditText) findViewById(R.id.lastName)).getText().toString();
                emailAddress = ((EditText) findViewById(R.id.emailAddress)).getText().toString();
                password = ((EditText) findViewById(R.id.password)).getText().toString();
                if(firstName.length()>0 && lastName.length()>0 && emailAddress.length()>0 && password.length()>0) {
                    openHomePage();
                }
            }
        });
    }

    private void openHomePage(){
        Intent intent = new Intent(this, HomePage.class);
        intent.putExtra("User Name", firstName+" "+lastName);
        intent.putExtra("Email Address", emailAddress);
        startActivity(intent);
    }
}
