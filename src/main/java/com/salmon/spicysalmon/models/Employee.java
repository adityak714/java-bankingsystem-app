package com.salmon.spicysalmon.models;

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
        return getFirstName() + " " + getLastName() + Util.EOL
                + getSocialSecurityNumber() + Util.EOL
                + getStartDate() + Util.EOL;
    }
}
