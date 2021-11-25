package com.salmon.spicysalmon.models;

import java.util.LinkedHashMap;

public abstract class User{
    private String password;
    private String firstName;
    private String lastName;
    private final String socialSecurityNumber;


    public User(String socialSecurityNumber, String password, String firstName, String lastName) {
        this.socialSecurityNumber = socialSecurityNumber;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
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
        return socialSecurityNumber;
    }


    // call this on a user so no need for user ID
    public boolean verifyPassword(String testPassword) {
        return this.password.equals(testPassword);
    }

    /*public void changePassword(String oldPassword, String newPassword, int UserId){
        if (OldPassword == NewPassword){
            System.out.println("Error : You wrote the same password (old password)");
        }
        UserId.setPassword = NewPassword;
    }*/
    public void changePassword(String testPassword, String newPassword) throws Exception {
        if (this.password.equals(testPassword)) {
            this.password = newPassword;
        } else {
            throw new Exception("Incorret current password");
        }
    }
}
