package com.salmon.spicysalmon.models;
import  com.salmon.spicysalmon.Util;

import com.salmon.spicysalmon.Util;

public class Employee extends User{
    private final String startDate;

   public Employee(String socialSecurityNumber, String password, String firstName, String lastName, String date){
        super(socialSecurityNumber, password, firstName, lastName);
        this.startDate = date;
   }

   public String getStartDate(){
       return startDate;
   }

    public String toString(){
        return  Util.EOL +  super.toString() + Util.EOL
                + "Start Date: " + this.startDate + Util.EOL;
    }
    /*public String deleteAccount(String ID) throws Exception{

        if (ID.length() == 10 && ID.startsWith("5")){
            String userID = ID.substring(2, 8); //Check if this should start at 1
            String accountID = ID.substring(9, 10);
            if (getUsersCollection().containsKey(userID) && getUsersCollection().get(userID).getAccounts().containsKey(accountID)) {
                getUsersCollection().get(userID).getAccounts().remove(accountID);

                return "Account removed successfully.";
            }
            else return "Account number not found.";
        }
        return "Invalid account number";
    }*/
}
