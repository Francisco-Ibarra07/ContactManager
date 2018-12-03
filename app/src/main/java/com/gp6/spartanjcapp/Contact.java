package com.gp6.spartanjcapp;

/**
 * This class defines a "Contact" object. Each user's information, when signed up, gets stored
 * in this object type.
 */
public class Contact implements Comparable<Contact>{

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public Contact(){

    }

    public Contact(String firstName, String lastName, String email, String phoneNumber){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int compareTo(Contact o) {
        return this.getDisplayName().compareTo(o.getDisplayName());
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

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getDisplayName() { return this.firstName + " " + this.lastName; }

    public String getLastName(){
        return this.lastName;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }
}

