package com.salmon.spicysalmon.models;

import com.salmon.spicysalmon.Util;

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

    public String getSocialSecurityNumber() {
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
   /* public String getApprovalStatus(){
        if (super.getIsApproved() == null)
            return "Account request is pending." + Util.EOL
                    + "Request was created " + getCREATIONDATE(); //If the request is pending we show creation date.
        if (super.getIsApproved())
            return "Account request was approved." + Util.EOL
                    + "Request was created: " + getCREATIONDATE() + Util.EOL //If the request was approved/denied we also show when it was resolved.
                    + "Request was approved: " + getRESOLVEDDATE();
        else
            return "Account request was denied." + getRESOLVEDDATE()
                    + "Request was created: " + getCREATIONDATE() + Util.EOL
                    + "Request was denied: " + getRESOLVEDDATE();
    }
    */

    public String toString(){
        String line = "-"; //Separator line to segment customer and request information

                 if (this.getFirstName().length() + this.getLastName().length() > this.getSocialSecurityNumber().length()) //Adds some -------- between request dates and requestee information
                 line = line.repeat(5 + this.getFirstName().length() + this.getLastName().length());                            // compares length of SSN and first + last name to decide how many dashes to use
                                                                                                                            // could just use more than necessary I guess.
                 else line = line.repeat(5 + this.getSocialSecurityNumber().length());
        String status = "";
        if (this.getIsApproved() == null)
            status =  "Status: Pending" + Util.EOL
                    + this.getCREATIONDATE(); //If the request is pending we show creation date.
        if (this.getIsApproved())
            status = "Status: Approved" + Util.EOL
                    + "Request was created: " + this.getCREATIONDATE() + Util.EOL //If the request was approved/denied we also show when it was resolved.
                    + "Request was approved: " + this.getRESOLVEDDATE();
        else
            status = "Status: Denied" + Util.EOL
                    + "Request was created: " + this.getCREATIONDATE() + Util.EOL
                    + "Request was denied: " + this.getRESOLVEDDATE();
        return
                "|" + "-".repeat(40) + Util.EOL
                        + "Bank Account Request" + Util.EOL
                        + status + Util.EOL
                        + "|" + "-".repeat(40)
                        + "|CUSTOMER INFORMATION"
                        + "|" + "-".repeat(40)
                        + "|Name: " +this.getFirstName() + " " + this.getLastName() + Util.EOL
                        + "|SSN: " +this.getSocialSecurityNumber() + Util.EOL
                        + "|Address: " + this.getResidentialArea() + Util.EOL
                        + "|Occupation " + this.getOccupation() + Util.EOL
                        + "|Salary: " + this.getSalary() + Util.EOL
                        + "|" + "-".repeat(40) + Util.EOL;
    }
   /* public int compareTo(CustomerAccountRequest otherCustomerAccountRequest) { //Compare last name letter by letter
        int nameLength = Math.max(this.getLastName().length(), otherCustomerAccountRequest.getLastName().length()); //Checks which last name is longer
        for (int i = 0; i < nameLength; i++) {
            if (this.getLastName().toLowerCase().charAt(i) < otherCustomerAccountRequest.getLastName().toLowerCase().charAt(i)){
                return -1;
            }
            if (this.getLastName().toLowerCase().charAt(i) > otherCustomerAccountRequest.getLastName().toLowerCase().charAt(i)){
                return 1;
            }
        }
        return 0;
    }*/
}
