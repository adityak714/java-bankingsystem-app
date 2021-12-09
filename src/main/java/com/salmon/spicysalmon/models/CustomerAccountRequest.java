package com.salmon.spicysalmon.models;

public class CustomerAccountRequest extends AccountRequest{

    private String firstName;
    private String lastName;
    private String password;
    private final String SOCIALSECURITYNUMBER;
    private double salary;
    private String residentialArea;
    private String occupation;

    public CustomerAccountRequest(String socialSecurity, String password, String firstName, String lastName, double salary, String residentialArea, String occupation){
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.SOCIALSECURITYNUMBER = socialSecurity;
        this.salary = salary;
        this.residentialArea = residentialArea;
        this.occupation = occupation;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getSOCIALSECURITYNUMBER() {
        return SOCIALSECURITYNUMBER;
    }

    public double getSalary() {
        return salary;
    }

    public String getResidentialArea() {
        return residentialArea;
    }

    public String getOccupation() {
        return occupation;
    }

}
