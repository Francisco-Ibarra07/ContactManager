package com.gp6.spartanjcapp;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
    TODO
        (DONE) 1) Overlay button does not do anything when clicked
        (DONE) 3) Be able to pass in uId to contact view activity
        (DONE) 4) Be able to press overlay button and choose add via scan or manually (
                (If that does not work, do it through hamburger window)
        (DONE) 5) Make adding contacts manually possible
        (DONE) 6) Test to be able to add one contact onto one of the user's contact list on Firebase
        7) Make list view clickable
        8) Generate QR code to a user
        9) Replaced fake names in "Contact View" activity with real contacts
            Pass in contact object
            Use that information to fill our contact view page
        10) Get info from scanned QR code and add to database
        11) Add hamburger icon side
        12) Documentation

 */
public class HomePage extends AppCompatActivity {

    private Button qrCodeScanButton;
    private Button addContactManuallyButton;
    private Button overlayButton;
    private ListView contactListView;
    private TextView displayName;
    private boolean overlayClicked;
    private List<Contact> contactList;

    private FirebaseAuth currentFirebaseAuth;
    private DatabaseReference myDatabase;
    private FirebaseUser currentFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        final Activity activity = this;

        //Initialize buttons
        initializeUserInputVariables();

        String userEmail = currentFirebaseUser.getEmail();
        String currentUid = currentFirebaseUser.getUid();
        String userDisplayName = getIntent().getStringExtra("DISPLAY_NAME");
        String phoneNumber = getIntent().getStringExtra("PHONE_NUMBER");

        //Set display name under profile photo
        displayName.setText(userDisplayName);


        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Contact contact = contactList.get(position);

                Intent intent = new Intent(getApplicationContext(), ContactView.class);
                intent.putExtra("CONTACT_VIEW_PHONE", contact.getPhoneNumber());
                intent.putExtra("CONTACT_VIEW_EMAIL", contact.getEmail());
                intent.putExtra("CONTACT_VIEW_FIRSTNAME", contact.getFirstName());
                intent.putExtra("CONTACT_VIEW_LASTNAME", contact.getLastName());

                startActivity(intent);
            }
        });
        overlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overlayClicked = !overlayClicked;

                if(overlayClicked){
                    qrCodeScanButton.setVisibility(View.VISIBLE);
                    addContactManuallyButton.setVisibility(View.VISIBLE);
                }
                else{
                    qrCodeScanButton.setVisibility(View.INVISIBLE);
                    addContactManuallyButton.setVisibility(View.INVISIBLE);
                }

//                FirebaseAuth.getInstance().signOut();
//                Toast.makeText(getBaseContext(), "Signed out", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(activity, ContactView.class);
//                //intent.putExtra("CONTACT_UID", "Value passed from homepage");
//                startActivity(intent);
            }
        });

        /**
         * QR Scan method which when pressed, opens up the camera and begins to listen for a QR code
         *
         */
        qrCodeScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

        /**
         * Add contacts manually method that provides user with a new activity in order to add a contact
         *
         */
        addContactManuallyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, AddContactScreen.class);
                startActivity(intent);
            }
        });
    }

    private void initializeUserInputVariables(){
        //Initialize firebase variables
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        currentFirebaseAuth = FirebaseAuth.getInstance();
        myDatabase = FirebaseDatabase.getInstance().getReference(currentFirebaseAuth.getUid());

        qrCodeScanButton = findViewById(R.id.qrScanButton);
        overlayButton = findViewById(R.id.overlayButton);
        addContactManuallyButton = findViewById(R.id.addManuallyButton);
        displayName = findViewById(R.id.displayNameTextView);
        contactListView = (ListView) findViewById(R.id.contactsListHomePage);
        overlayClicked = false;

        contactList = new ArrayList<>();
    }

    private void generateQRCode(){

    }

    @Override
    protected void onStart() {

        super.onStart();

        //Add value listener to database
        myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                contactList.clear();
                for(DataSnapshot contactSnapshot : dataSnapshot.getChildren()){
                    Contact addMe = contactSnapshot.getValue(Contact.class);

                    contactList.add(addMe);
                }

                //Sort contacts in alphabetical order
                Collections.sort(contactList);

                ContactList adapter = new ContactList(HomePage.this, contactList);

                //Set list view on home page with info contained in adapter
                contactListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

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
