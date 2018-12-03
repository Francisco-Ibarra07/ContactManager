package com.gp6.spartanjcapp;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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

/**
 * This class represents the home page screen of the contact application. It initializes variables
 * such as:
 *      1) Firebase authentication, database, and reference variables
 *      2) Text editors used when the user types in something
 *      3) Buttons that are used to detect when a button has been pressed
 *      4) Lists in order to display content such as a contact list
 *
 *
 */
public class HomePage extends AppCompatActivity {

    //User interaction instance variables
    private Button qrCodeScanButton;
    private Button addContactManuallyButton;
    private Button overlayButton;
    private ListView contactListView;
    private boolean overlayClicked;
    private List<Contact> contactList;

    //Firebase variables
    private FirebaseAuth currentFirebaseAuth;
    private DatabaseReference myDatabase;
    private FirebaseUser currentFirebaseUser;


    /**
     * This is a default method used by Android Studio in order to create this screen
     *
     * @param savedInstanceState default Android Studio variable
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        final Activity activity = this;

        /**
         * This method initializes all instance variables defined above
         */
        initializeInstanceVariables();


        /**
         * Perform action when one of the items in the contact list is pressed.
         * Take user to Contact View page/activity while also sending contact information
         * of the contact that was clicked on.
         */
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


        /**
         * Perform action when the RED overlay button is pressed.
         * Display's two more options for the user:
         *      1) Add contact manually
         *      2) Scan a QR code to add a contact
         */
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
            }
        });

        /**
         * QR Scan method which when pressed, opens up the camera and begins to listen for a QR code
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
         */
        addContactManuallyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, AddContactScreen.class);
                startActivity(intent);
            }
        });

    }


    /**
     * This method initializes all instance variables defined above
     */
    private void initializeInstanceVariables(){
        //Initialize firebase variables
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        currentFirebaseAuth = FirebaseAuth.getInstance();
        myDatabase = FirebaseDatabase.getInstance().getReference(currentFirebaseAuth.getUid());

        qrCodeScanButton = findViewById(R.id.qrScanButton);
        overlayButton = findViewById(R.id.overlayButton);
        addContactManuallyButton = findViewById(R.id.addManuallyButton);
        contactListView = (ListView) findViewById(R.id.contactsListHomePage);
        overlayClicked = false;

        contactList = new ArrayList<>();
    }

    /**
     * This method is used in order to READ from Firebase and display the contact list
     * that has been saved on the user's account. Contact list is updated at the creation
     * of the home page
     *
     */
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
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    /**
     * This method utilizes the ZXing library and grabs the feedback sent when a user is in
     * the process of scanning a QR code
     *
     * @param requestCode Used by ZXing to see request
     * @param resultCode  Used by ZXing to see result
     * @param data  Data read
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        //If 'result' returns null, the scan was cancelled
        if(result.getContents() == null){
            Toast.makeText(this, "Scan cancelled", Toast.LENGTH_LONG).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
