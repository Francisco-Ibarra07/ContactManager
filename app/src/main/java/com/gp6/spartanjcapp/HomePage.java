package com.gp6.spartanjcapp;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

/*
    TODO
        1) Overlay button does not do anything when clicked
        2) Replaced fake names with real contacts
        3) Be able to pass in uId to contact view activity
        4) Be able to press overlay button and choose add via scan or manually (
            (If that does not work, do it through hamburger window)

 */
public class HomePage extends AppCompatActivity {

    private Button qrCodeScanButton;
    private Button overlayButton;
    private TextView displayName;
    private FirebaseAuth currentFirebaseAuth;
    private FirebaseUser currentFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        final Activity activity = this;

        //Initialize buttons
        initializeUserInputVariables();

        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        currentFirebaseAuth = FirebaseAuth.getInstance();

        String userEmail = currentFirebaseUser.getEmail();
        String currentUid = currentFirebaseUser.getUid();
        String userDisplayName = getIntent().getStringExtra("DISPLAY_NAME");
        String phoneNumber = getIntent().getStringExtra("PHONE_NUMBER");
        //Set display name under profile photo
        displayName.setText(userDisplayName);

//        //Grab all contacts from user given a user id
//        ArrayList<String> updatedContactList = getContactsListOfUser(currentUid);
//
//        //Create adapter and display updated contact list onto ListView
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_homepage_listview, updatedContactList);
//        ListView listView = (ListView) findViewById(R.id.contactsListHomePage);
//        listView.setAdapter(adapter);

        //When overlay button is pressed do action
        overlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getBaseContext(), "Signed out", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(activity, ContactView.class);
//                //intent.putExtra("CONTACT_UID", "Value passed from homepage");
//                startActivity(intent);
            }
        });


//        qrCodeScanButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                IntentIntegrator integrator = new IntentIntegrator(activity);
//                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
//                integrator.setPrompt("Scan");
//                integrator.setCameraId(0);
//                integrator.setBeepEnabled(false);
//                integrator.setBarcodeImageEnabled(false);
//                integrator.initiateScan();
//            }
//        });
    }

    private void initializeUserInputVariables(){
        //qrCodeScanButton = findViewById(R.id.qrScanButton);
        overlayButton = findViewById(R.id.overlayButton);
        displayName = findViewById(R.id.displayNameTextView);
    }

    private void updateContactList(ArrayList<String> updatedContactList){



    }
    //private ArrayList<String> getContactsListOfUser(String userUid){    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        //If 'result' returns null, the scan was cancelled
        if(result.getContents() == null){
            Toast.makeText(this, "Scan cancelled", Toast.LENGTH_LONG).show();
        }
        //Else get the contents of 'result'. 'result.getContents()' contains the reading of the qr code
        else{

            /*
                pseudocode:

                String decodedData[] = decodeScannedData(result.getContents()); //grabs scanned data and places all info into a nice format; stored in array
                addNewScannedUser(decodedData); //Adds new user to home page given the decoded data
             */


        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
