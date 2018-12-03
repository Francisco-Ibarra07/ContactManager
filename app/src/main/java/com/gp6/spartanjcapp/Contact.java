package com.gp6.spartanjcapp;

/**
 * This class defines a "Contact" object. Each user's information, when signed up, gets stored
 * in this object type. This class implements the Comparable<> interface which is used primarily
 * to give the ability of a List<Contact> to be sortable
 *
 */
public class Contact implements Comparable<Contact>{

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    /**
     * Empty constructor needed in order for Firebase to read this object
     */
    public Contact(){

    }

    /**
     * Overloaded constructor used to define a Contact object.
     *
     * @param firstName first name of user
     * @param lastName last name of user
     * @param email inputted email of user
     * @param phoneNumber inputted phone number of user
     */
    public Contact(String firstName, String lastName, String email, String phoneNumber){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    /**
     * This method is used to make a Contact comparable to another contact.
     * In our case we are using the display name to be the main comparator.
     *
     * @param o The other contact
     * @return which contact is greater alphabetically
     */
    @Override
    public int compareTo(Contact o) {
        return this.getDisplayName().compareTo(o.getDisplayName());
    }

    /** BELOW ARE GETTERS AND SETTERS  */

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

    public String getDisplayName() {
        return this.firstName + " " + this.lastName;
    }

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

