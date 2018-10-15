package com.gp6.spartanjcapp;

/**
 * This class defines a "Contact" object. Each user's information, when signed up, gets stored
 * in this object type. With this information, our ContactManager class will be able to keep a
 * list of Contact objects and store them conveniently on Firebase
 */
public class Contact {

    private String firstName;
    private String lastName;
    private String email;
    private int phoneNumber;

    public Contact(){
        firstName = "";
        lastName = "";
        email = "";
        phoneNumber = 0;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPhoneNumber(int phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public String getEmail(){
        return this.email;
    }

    public int getPhoneNumber(){
        return this.phoneNumber;
    }
}

