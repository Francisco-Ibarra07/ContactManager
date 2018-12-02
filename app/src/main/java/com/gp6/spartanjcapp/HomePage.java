package com.gp6.spartanjcapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button qrCodeScanButton;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    final Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        qrCodeScanButton = (Button)findViewById(R.id.qrScanButton);

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

        drawerLayout = (DrawerLayout) findViewById(R.id.home_page_activity);
        toggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_qr:
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
                break;
        }
        int id = item.getItemId();

        if (id == R.id.nav_edit_user) {

        } else if (id == R.id.nav_qr) {
            NavigationView nav = findViewById(R.id.nav_qr);
            nav.setNavigationItemSelectedListener(this);
            nav.setOnClickListener(new View.OnClickListener() {
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

        } else if (id == R.id.groups) {

        } else if (id == R.id.nav_duplicates) {

        } else if (id == R.id.nav_block) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_log_out) {
            openMainActivity();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.home_page_activity);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
