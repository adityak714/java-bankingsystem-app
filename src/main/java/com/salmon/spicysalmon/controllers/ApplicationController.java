package com.salmon.spicysalmon.controllers;
import com.salmon.spicysalmon.models.BankAccountApplication;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
/* Maybe Customers should have an ArrayList with their applications instead or both?
   Not sure if we can have the Customers see the status of their applications,
   but this is maybe solved once we connect the GUI?
 */
public class ApplicationController {
    private final ArrayList<BankAccountApplication> allApplications;

    public ApplicationController(){
        allApplications = new ArrayList<>();
    }

    public ArrayList<BankAccountApplication> getAllApplications(){
        return allApplications;
    }
    public String printAllApplications(){
        return allApplications.toString();
    }

    public ArrayList<String> getSpecificApplication(String SSN){
        ArrayList<String> specificUserAccountList = new ArrayList<>();
        for (BankAccountApplication application : allApplications){
            if (application.getRequestee().equals(SSN)){
                specificUserAccountList.add(application.toString() + "\n");
            }
        }
        return specificUserAccountList;
    }

    public String getApplicationStatus(String SSN){
        String output = "";
        for (BankAccountApplication application : allApplications){
            if (application.getRequestee().getSocialSecurityNumber().equals(SSN)){
                output += application.getApprovalStatus() + "\n";
            }
        }
        return output;
    }
    public void addApplication(BankAccountApplication application){
        allApplications.add(application);
    }
}
