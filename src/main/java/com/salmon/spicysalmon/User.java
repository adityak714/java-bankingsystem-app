package com.salmon.spicysalmon;

public abstract class User {

    private String password;
    private String firstName;
    private String lastName;
    final private String socialSecurityNumber;

    public User(String password, String firstName, String lastName, String socialSecurityNumber) {

        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.personnumer = this.socialSecurityNumber;
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
        return personnumer;
    }

    public boolean verifyPassword(String password,String socialSecurityNumber){
        if(Password==socialSecurityNumber.getPassword){
            return true;
        }else {
            return false;
        }
    }

    public void changePassword(String oldPassword, String newPassword, String socialSecurityNumber){
        if (OldPassword == NewPassword){
            System.out.println("Error : You wrote the same password (old password)");
        }
        socialSecurityNumber.setPassword = NewPassword;
    }
}