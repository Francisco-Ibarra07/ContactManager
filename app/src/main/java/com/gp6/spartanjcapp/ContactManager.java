package com.gp6.spartanjcapp;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gp6.spartanjcapp.Contact;

public class ContactManager {

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private String userID;


    /**
     * Constructor for the ContactManager object. This object handles adding,
     * deleting, and editing contacts while at the same time, updating these actions
     * on Firebase
     *
     * @param currentUser the user that is currently signed in
     */
    public ContactManager(FirebaseUser currentUser){
        this.firebaseUser = currentUser;
        userID = currentUser.getUid();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    /**
     * This method adds a new contact object to the user's contacts.
     * Stores contact on Firebase
     *
     * @param addMe contact object to add to database
     */
    public void addNewContact(Contact addMe){
        
    }

    /**
     * This method deletes the given contact object off of the user's contacts.
     * Stores update on Firebase
     *
     * @param deleteMe contact object to add to database
     */
    public void deleteContact(Contact deleteMe){

    }

    /**
     * This method edits the given contact object.
     * Stores update on Firebase
     *
     * @param editMe contact object to add to database
     */
    public void editContact(Contact editMe){

    }

}
