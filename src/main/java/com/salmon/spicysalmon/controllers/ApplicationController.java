package com.salmon.spicysalmon.controllers;
import com.salmon.spicysalmon.models.BankAccount;
import com.salmon.spicysalmon.models.BankAccountApplication;
import com.salmon.spicysalmon.models.Customer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
    public String getApplicationStatus(String SSN) { //Returns status of all applications for a specific Customer.
        String output = "";                          //Maybe not needed depending on if we want to delete applications from the list when they are denied or approved.
        for (BankAccountApplication application : allApplications) {
            if (application.getRequestee().getSocialSecurityNumber().equals(SSN)) {
                output += application.getApprovalStatus() + "\n";
            }
        }
        return output;
    }

    public void deleteApplication(BankAccountApplication application) { //Deletes the specified application
        allApplications.remove(application);
    }

    public void createApplication(Customer customer, String accountName) { //Creates a new application and puts in the ArrayList of applications
        allApplications.add(new BankAccountApplication(customer, accountName));
    }

    public void addApplication(BankAccountApplication application) { //Adds
        allApplications.add(application);
    }

    public void approveApplication(BankAccountApplication application) {    //Sets the Boolean isApproved of the Customer object that is referenced in the specified application to true.
        application.approveApplication();                                   //And creates a new BankAccount for the Customer and puts it in the HashMap of BankAccounts.
        if (application.getRequestee().getNumberOfAccounts() < 10) {        // Key depends on the size of the hashmap, or at least I hope it does :).
            //application.getRequestee().getAccounts().put("0" + application.getRequestee().getNumberOfAccounts() + 1, new BankAccount("0" + application.getRequestee().getNumberOfAccounts() + 1, application.getRequestee().getFirstName(), application.getRequestee().getLastName()));
            deleteApplication(application);
        } else
            //application.getRequestee().getAccounts().put("" + application.getRequestee().getNumberOfAccounts() + 1, new BankAccount("" + application.getRequestee().getNumberOfAccounts() + 1, application.getRequestee().getFirstName(), application.getRequestee().getLastName()));
        deleteApplication(application); //Deletes the application, maybe we don't want to do this?
    }
    public void denyApplication(BankAccountApplication application){
        application.denyApplication();
        //Customer probably want to know why application was denied
    }
}

