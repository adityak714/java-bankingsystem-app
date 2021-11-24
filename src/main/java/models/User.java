package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public abstract class User extends Bank {
    private final String ID;
    private String password;
    private String firstName;
    private String lastName;
    private final String socialSecurityNumber;


    public User(String ID, String password, String firstName, String lastName, String socialSecurityNumber) {
        this.ID = ID;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.socialSecurityNumber = socialSecurityNumber;
        this.accounts = new LinkedHashMap<>();
    }

    public String getID() {
        return ID;
    }

    public String getPassword() {
        return password;
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

    public LinkedHashMap<String, BankAccount> getAccounts() {
        return accounts;
    }

}
