package com.salmon.spicysalmon.controllers;
import com.salmon.spicysalmon.Util;

import com.salmon.spicysalmon.models.*;
import com.salmon.spicysalmon.models.Customer;


import java.util.ArrayList;

/* Maybe Customers should have an ArrayList with their applications instead or both?
   Not sure if we can have the Customers see the status of their applications,
   but this is maybe solved once we connect the GUI?
 */
public class ApplicationController {
    public final ArrayList<BankAccountApplication> allApplications;

    public ApplicationController() {
        allApplications = new ArrayList<>();
    }
    public ArrayList<BankAccountApplication> getAllApplications() {
        return allApplications;
    }
    public String printAllApplications() {
        return allApplications.toString();
    }
    public ArrayList<String> getApplications(String SSN) { //Returns list of all applications for the input SSN.
        ArrayList<String> specificUserAccountList = new ArrayList<>();
        for (BankAccountApplication application : allApplications) {
            if (application.getRequestee().getSocialSecurityNumber().equals(SSN)) {
                specificUserAccountList.add(application.toString() + "\n");
            }
        }
        return specificUserAccountList;
    }
    public ArrayList<String> getApplications(Customer customer) { //Returns list of all applications for the requested Customer object.
        ArrayList<String> specificUserAccountList = new ArrayList<>();
        for (BankAccountApplication application : allApplications) {
            if (application.getRequestee().getSocialSecurityNumber().equals(customer.getSocialSecurityNumber())) {
                specificUserAccountList.add(application.toString() + "\n");
            }
        }
        return specificUserAccountList;
    }
    public String getApplicationStatus(String SSN) { //Returns status of all BankAccountApplications for a specific Customer.
        String output = "";                          //Maybe not needed depending on if we want to delete applications from the list when they are denied or approved.
        for (BankAccountApplication application : allApplications) {
            if (application.getRequestee().getSocialSecurityNumber().equals(SSN)) {
                output += application.getApprovalStatus() + "\n";
            }
        }
        return output;
    }
    public void createApplication(Customer customer, String accountName) { //Creates a new application and puts in the ArrayList of applications
        allApplications.add(new BankAccountApplication(customer, accountName));
    }
    public void addApplication(BankAccountApplication application) { //Adds
        allApplications.add(application);
    }
    public void deleteApplication(BankAccountApplication application) { //Deletes the specified application
        allApplications.remove(application);
    }
    public void approveApplication(BankAccountApplication application) {    //Sets the Boolean isApproved of the Customer object that is referenced in the specified application to true.
        application.approveApplication(); //And creates a new BankAccount for the Customer and puts it in the HashMap of BankAccounts.
        application.getRequestee().createBankAccount(application.getRequestee().getSocialSecurityNumber(), application.getRequestee().getFirstName(), application.getRequestee().getLastName());
    }
    public void denyApplication(BankAccountApplication application, String denyMessage){
        application.denyApplication(denyMessage); //Passes message to the customer why the application was denied
    }
}

