package com.gp6.spartanjcapp;

/**
 * This class defines a "Contact" object. Each user's information, when signed up, gets stored
 * in this object type. With this information, our ContactManager class will be able to keep a
 * list of Contact objects and store them conveniently on Firebase
 */
public class Contact {

    private String name;
    private String lastName;
    private String email;
    private int phoneNumber;

    public Contact(String name, String email, int phoneNumber){
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public void setFirstName(String name){
        this.name = name;
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

    public String getName(){
        return this.name;
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

