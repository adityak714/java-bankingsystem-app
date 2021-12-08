package com.salmon.spicysalmon.models;

public class CustomerAccountApplication extends Application{

    private String firstName;
    private String lastName;
    private String password;
    private final String SOCIALSECURITYNUMBER;

    public CustomerAccountApplication(String SOCIALSECURITYNUMBER, String firstName, String lastName, String password){
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.SOCIALSECURITYNUMBER = SOCIALSECURITYNUMBER;
    }
}
