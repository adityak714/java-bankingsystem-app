package com.salmon.spicysalmon.models;

import com.salmon.spicysalmon.Util;

public class BankAccountRequest extends AccountRequest{
    private final Customer REQUESTEE;
    private String accountName;


    public BankAccountRequest(Customer customer, String accountName){
        super();
        this.REQUESTEE = customer;
        this.accountName = accountName;
    }


    public Customer getREQUESTEE(){
        return this.REQUESTEE;
    }
    public String getAccountName(){
        return this.accountName;
    }

    /*public String getApprovalStatus(){
        if (super.getIsApproved() == null)
            return "Application for bank account: " + this.accountName + " is pending.";
        if (super.getIsApproved())
            return "";
        else
            return "Application for bank account: " + this.accountName + " has been denied.";
    }

     */

        public String toString(){
            String line = "-"; //Separator line to segment customer and request information

            if (REQUESTEE.getFirstName().length() + REQUESTEE.getLastName().length() > REQUESTEE.getSocialSecurityNumber().length()) //Adds some -------- between request dates and requestee information
                line = line.repeat(5 + REQUESTEE.getFirstName().length() + REQUESTEE.getLastName().length());                            // compares length of SSN and first + last name to decide how many dashes to use
                                                                                                                                    // could just use more than necessary I guess.
            else line = line.repeat(5 + REQUESTEE.getSocialSecurityNumber().length());
            return
                            line + Util.EOL
                            + "Bank Account Request" + Util.EOL
                            + getApprovalStatus() + Util.EOL
                            + line
                            + "CUSTOMER INFORMATION"
                            + line
                            + "Name: " +REQUESTEE.getFirstName() + " " + REQUESTEE.getLastName() + Util.EOL
                            + "SSN: " +REQUESTEE.getSocialSecurityNumber() + Util.EOL
                            + "Adress: " + REQUESTEE.getResidentialArea() + Util.EOL
                            + "Occupation " + REQUESTEE.getOccupation() + Util.EOL
                            + "Salary: " + REQUESTEE.getSalary() + Util.EOL
                            + line + Util.EOL;
        }
    }
