package com.salmon.spicysalmon.models;

import com.salmon.spicysalmon.Util;

import java.util.LinkedHashMap;

public abstract class User{
    private String password;
    private String firstName;
    private String lastName;
    private final String SOCIALSECURITYNUMBER;

    public User(String socialSecurityNumber, String password, String firstName, String lastName) {
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.SOCIALSECURITYNUMBER = socialSecurityNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSocialSecurityNumber() {
        return SOCIALSECURITYNUMBER;
    }

    @Override
    public String toString() {
        return    "Name: " + this.firstName + " " + this.lastName + Util.EOL
                + "SSN: " + this.SOCIALSECURITYNUMBER;
        /*return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", SOCIALSECURITYNUMBER='" + SOCIALSECURITYNUMBER + '\'' +
                '}';
                */
    }

    public boolean verifyPassword(String testPassword) {
        return this.password.equals(testPassword);
    }

    public void changePassword(String testPassword, String newPassword) throws Exception {
        if (this.password.equals(testPassword)) {
            this.password = newPassword;
        } else {
            throw new Exception("Incorrect old password. Try again.");
        }
    }
}
