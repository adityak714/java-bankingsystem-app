package com.salmon.spicysalmon.models;

import com.salmon.spicysalmon.Util;
import com.salmon.spicysalmon.controllers.AccountRequestController;

import java.util.UUID;

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
            AccountRequestController accountRequestController = new AccountRequestController();
            String line = "-"; //Separator line to segment customer and request information

            if (REQUESTEE.getFirstName().length() + REQUESTEE.getLastName().length() > REQUESTEE.getSocialSecurityNumber().length()) //Adds some -------- between request dates and requestee information
                line = line.repeat(5 + REQUESTEE.getFirstName().length() + REQUESTEE.getLastName().length());                            // compares length of SSN and first + last name to decide how many dashes to use
                                                                                                                                    // could just use more than necessary I guess.
            else line = line.repeat(5 + REQUESTEE.getSocialSecurityNumber().length());
            String status = "";
            if (this.getIsApproved() == null)
                status =  "Status: Pending" + Util.EOL
                        + this.getCREATIONDATE(); //If the request is pending we show creation date.
            if (this.getIsApproved())
                status = "|Status: Approved" + Util.EOL
                        + "|Request was created: " + this.getCREATIONDATE() + Util.EOL //If the request was approved/denied we also show when it was resolved.
                        + "|Request was approved: " + this.getRESOLVEDDATE();
            else
                 status = "|Status: Denied" + Util.EOL
                        + "|Request was created: " + this.getCREATIONDATE() + Util.EOL
                        + "|Request was denied: " + this.getRESOLVEDDATE();

            return
                            "|" + "-".repeat(40) + Util.EOL
                            + "Bank Account Request" + Util.EOL
                            + status + Util.EOL
                            + "|" + "-".repeat(40)
                            + "|CUSTOMER INFORMATION"
                            + "|" + "-".repeat(40)
                            + "|Name: " +REQUESTEE.getFirstName() + " " + REQUESTEE.getLastName() + Util.EOL
                            + "|SSN: " +REQUESTEE.getSocialSecurityNumber() + Util.EOL
                            + "|Address: " + REQUESTEE.getResidentialArea() + Util.EOL
                            + "|Occupation " + REQUESTEE.getOccupation() + Util.EOL
                            + "|Salary: " + REQUESTEE.getSalary() + Util.EOL
                            + "|" + "-".repeat(40) + Util.EOL;
        }


    /*public int compareTo(Customer otherCustomer) { //Compare last name letter by letter
            int nameLength = Math.max(this.getREQUESTEE().getLastName().length(), otherCustomer.getLastName().length()); //Checks which last name is longer
            for (int i = 0; i < nameLength; i++) {
                if (this.getREQUESTEE().getLastName().toLowerCase().charAt(i) < otherCustomer.getLastName().toLowerCase().charAt(i)){
                    return -1;
                }
                if (this.getREQUESTEE().getLastName().toLowerCase().charAt(i) > otherCustomer.getLastName().toLowerCase().charAt(i)){
                    return 1;
                }
            }
            return 0;
        }*/
}
