package com.salmon.spicysalmon;

public abstract class User {

    private String password;
    private String firstName;
    private String lastName;
    final private String personnumer;

    public User(String password, String firstName, String lastName, String personnumer) {

        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.personnumer = personnumer;
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

    public String getPersonnumer() {
        return personnumer;
    }

    public boolean verifyPassword(String password,String personnumer){
        if(Password==personnumer.getPassword){
            return true;
        }else {
            return false;
        }
    }

    public void changePassword(String oldPassword, String newPassword, String personnumer){
        if (OldPassword == NewPassword){
            System.out.println("Error : You wrote the same password (old password)");
        }
        personnumer.setPassword = NewPassword;
    }
}