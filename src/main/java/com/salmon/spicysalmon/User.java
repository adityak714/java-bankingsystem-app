package com.salmon.spicysalmon;

public abstract class User {
    final private int userID;
    private String password;
    private String firstName;
    private String lastName;
    final private long personnumer;

    public User(int userID, String password, String firstName, String lastName, long personnumer) {
        this.userID = userID;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.personnumer = personnumer;
    }

    public int getUserID() {
        return userID;
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

    public long getPersonnumer() {
        return personnumer;
    }
}
