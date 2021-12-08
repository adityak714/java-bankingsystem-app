package com.salmon.spicysalmon.models;
import  com.salmon.spicysalmon.Util;

public class Employee extends User{
    // delete vacation part
    private String title;
    private int vacationDays;
    private final String STARTDATE;

   public Employee(String socialSecurityNumber, String password, String firstName, String lastName, String title, String STARTDATE){
        super(password, firstName, lastName, socialSecurityNumber);
        this.title = title;
        this.STARTDATE = STARTDATE;
   }

    public String getTitle(){
        return this.title;
    }

    public String getStartDate(){
        return this.STARTDATE;
    }

    public int getVacationDays() {
        return this.vacationDays;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVacationDays(int vacationDays) {
        this.vacationDays = vacationDays;
    }

    public String toString(){
        return getFirstName() + " " + getLastName() + Util.EOL
                + getSocialSecurityNumber() + Util.EOL
                + getTitle() + Util.EOL
                + getStartDate() + Util.EOL
                + getVacationDays();
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
    public String reviewApplication(){

        return "";
    }
    public String certifyLoan(){
        return "";
    }
}
