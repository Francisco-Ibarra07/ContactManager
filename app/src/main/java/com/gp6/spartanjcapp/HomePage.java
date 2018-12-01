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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.lang.reflect.Array;

public class HomePage extends AppCompatActivity {

    private Button qrCodeScanButton;
    private Button overlayButton;
    private FirebaseAuth loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        final Activity activity = this;

        //Initialize buttons
        //initializeUserInputVariables();

        String[] array = {"Francisco Ibarra","Joe Swanson", "Erick Hernandez", "Mr. Robot", "Joey",
                         "Albert", "Jose Hernandez"};
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_homepage_listview, array);
        ListView listView = (ListView) findViewById(R.id.contactsListHomePage);
        listView.setAdapter(adapter);

//        overlayButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//
////                Intent intent = new Intent(activity, ContactView.class);
////                //intent.putExtra("CONTACT_UID", "Value passed from homepage");
////                startActivity(intent);
//            }
//        });


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
        //overlayButton = findViewById(R.id.overlayHomeBtn);
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
