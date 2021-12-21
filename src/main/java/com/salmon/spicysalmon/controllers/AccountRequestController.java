package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.Util;
import com.salmon.spicysalmon.models.*;
import com.salmon.spicysalmon.models.Customer;
import com.sun.tools.jconsole.JConsoleContext;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


import javax.swing.*;
import javax.tools.JavaCompiler;
import java.awt.*;
import java.io.Console;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.security.AllPermission;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* Maybe Customers should have an ArrayList with their requests instead or both?
   Not sure if we can have the Customers see the status of their requests,
   but this is maybe solved once we connect the GUI?
 */
public class AccountRequestController {
    public final ArrayList<BankAccountRequest> allBankAccountRequests;
    public final ArrayList<CustomerAccountRequest> allCustomerAccountRequests;
    
    public AccountRequestController() {
        allBankAccountRequests = new ArrayList<>();
        allCustomerAccountRequests = new ArrayList<>();
    }
    //Creates a new request and puts in the ArrayList of requests
    public void createBankAccountRequest(Customer customer, String accountName) {
        allBankAccountRequests.add(new BankAccountRequest(customer, accountName));
    }
    //Creates a new customer account request
    public void createCustomerAccountRequest(String socialSecurity, String password, String firstName, String lastName, double salary, String residentialArea, String occupation){
        allCustomerAccountRequests.add(new CustomerAccountRequest(socialSecurity, password, firstName, lastName, salary, residentialArea, occupation));
    }
    //Adds a bank account request to the allBankAccountRequests list in case it has been misplaced
    public void addBankAccountRequest(BankAccountRequest request) {
        allBankAccountRequests.add(request);
    }
    //Adds a customer account request to the  allCustomerAccountRequests list in case it has been misplaced
    public void addCustomerAccountRequest(CustomerAccountRequest request){
        allCustomerAccountRequests.add(request);
    }
    //Deletes the specified BankAccountRequest
    public void deleteBankAccountRequest(BankAccountRequest request) {
        allBankAccountRequests.remove(request);
    }
    //Deletes the specified CustomerAccountRequest
    public void deleteCustomerAccountRequest(CustomerAccountRequest request){
        allCustomerAccountRequests.remove(request);
    }
    //Approve a specific BankAccountRequest and creates the bank account for the Customer
    public void approveBankAccountRequest(BankAccountRequest request, String message) {    //Sets the Boolean isApproved of the Customer object that is referenced in the specified request to true.
        request.approveRequest(message); //And creates a new BankAccount for the Customer and puts it in the HashMap of BankAccounts.

        request.getREQUESTEE().createBankAccount(request.getREQUESTEE().getSocialSecurityNumber(), request.getREQUESTEE().getFirstName(), request.getREQUESTEE().getLastName());
    }
    //Approve a specific CustomerAccountRequest and create a Customer with the info from the Request
    public void approveCustomerAccountRequest(CustomerAccountRequest request, String message) throws Exception{
        CustomerController customerController = new CustomerController();
        request.approveRequest(message);
        customerController.createCustomer(request.getSocialSecurityNumber(), request.getPassword(),
                request.getFirstName(), request.getLastName(), request.getSalary(),
                request.getResidentialArea(), request.getOccupation());
    }
    //Deny an Account Request (Maybe need to do casting here? This method is for both BankAccountRequest and CustomerAccountRequest)
    public void denyAccountRequest(AccountRequest request, String denyMessage){
        request.denyRequest(denyMessage); //Passes message to the customer why the request was denied

    }
    //Method used if employee wants to search for requests with a specific name
    public String searchAllBankAccountRequests(String name) throws Exception{
        String output = "";
        String separatorLine = "|" + "-".repeat(30);
        int requestNumber = 1;
        for (BankAccountRequest request : allBankAccountRequests){
          if (request.getREQUESTEE().getFirstName().equals(name) || request.getREQUESTEE().getLastName().equals(name)){
              output += "|<" + requestNumber + ">" + Util.EOL + separatorLine + Util.EOL + "|Name: " + request.getREQUESTEE().getFirstName()
                      + " " + request.getREQUESTEE().getLastName() + Util.EOL + separatorLine + request.getStatusToString()
                      + Util.EOL + "|Created: " + request.getCREATIONDATE() + Util.EOL + "|SSN: " + request.getREQUESTEE().getSocialSecurityNumber()
                      + Util.EOL;
              requestNumber += 1;
          }
      }
        if (output.isEmpty()){throw new Exception("No bank account requests matched your search");}
        else output = " All Bank Account Request matching name: \"" + name + "\"" + output;
        return output;
    }

    //Returns a string of all bank account requests for all users.
    public String printAllBankAccountRequests() { //Maybe sort this
        String output = "";
        String separatorLine = "|" + "-".repeat(30);
        ArrayList<BankAccountRequest> sortedList = new ArrayList<>();
        int requestNumber = 1;
        Collections.copy(sortedList, allBankAccountRequests);
        Collections.reverse(sortedList); //Want to have a default sorting (Either status or creation date)

        for (BankAccountRequest request : sortedList) {
            if (request.getIsApproved() == null)
            output += "|<" + requestNumber + ">" + Util.EOL + separatorLine + Util.EOL + "|Name: " + request.getREQUESTEE().getFirstName()
                    + " " + request.getREQUESTEE().getLastName() + Util.EOL + separatorLine + request.getStatusToString()
                    + Util.EOL + "|Created: " + request.getCREATIONDATE() + Util.EOL + "|SSN: " + request.getREQUESTEE().getSocialSecurityNumber()
                    + Util.EOL;
            requestNumber += 1;

        }
        output = " All Bank Account Requests" + Util.EOL + separatorLine + output;
        return output;
    }
    //Returns the specific BankAccountRequest associated with the ID (UUID 36 char auto-generated) So that we can manipulate it via the menu
    public BankAccountRequest getSpecificBankAccountRequest(String ID){
        for ( BankAccountRequest request : allBankAccountRequests){
            if (request.getID().equals(ID)){
                return request;
            }
        }
        return null;
    }
    //Returns a string of all Customer Account Requests
    public String printAllCustomerAccountRequests() {
        String output = "";
        String separatorLine = "|" + "-".repeat(30);
        int requestNumber = 1;
        for (CustomerAccountRequest request : allCustomerAccountRequests) {
            output += "|<" + requestNumber + ">" + Util.EOL + separatorLine + Util.EOL + "|Name: " + request.getFirstName()
                    + " " + request.getLastName() + Util.EOL + separatorLine + request.getStatusToString()
                    + Util.EOL + "|Created: " + request.getCREATIONDATE() + Util.EOL + "|SSN: " + request.getSocialSecurityNumber()
                    + Util.EOL;
            requestNumber += 1;

        }
        output = " All Customer Account Requests" + Util.EOL + separatorLine + output;
        return output;
    }
    public CustomerAccountRequest getSpecificCustomerAccountRequest(String ID){
        for ( CustomerAccountRequest request : allCustomerAccountRequests){
            if (request.getID().equals(ID)){
                return request;
            }
        }
        return null;
    }
    //Print all pending CustomerAccountRequests
    public String printAllPendingCustomerAccountRequests() throws Exception{
        String output = "";
        String separatorLine = "|" + "-".repeat(30);
        int requestNumber = 1;
        for (CustomerAccountRequest request : allCustomerAccountRequests) {
            if(request.getIsApproved() == null) {
                output += "|<" + requestNumber + ">" + Util.EOL + separatorLine + Util.EOL + "|Name: " + request.getFirstName()
                        + " " + request.getLastName() + Util.EOL + separatorLine + request.getStatusToString()
                        + Util.EOL + "|Created: " + request.getCREATIONDATE() + Util.EOL + "|SSN: " + request.getSocialSecurityNumber()
                        + Util.EOL;
                requestNumber += 1;
            }
        } if (output.isEmpty()){throw new Exception("There are no pending customer account requests.");}
        else
            output = " All Pending Customer Account Requests" + Util.EOL + separatorLine + output;
            return output;
    }
    //Returns a String with the all bank account requests for the specified customer
    public String printSpecificCustomerBankAccountRequests(String SSN) throws Exception { //Returns list of all requests for the input SSN.
        String output = "";
        String separatorLine = "|" + "-".repeat(30);
        String name = ""; //Used to store Customer name for use later
        int requestNumber = 1;  //Used to number the different requests if more than one
        for (BankAccountRequest request : allBankAccountRequests) {
            if (request.getREQUESTEE().getSocialSecurityNumber().equals(SSN)) { //Checks the allBankAccountRequest list to see if SSN matches any request
                name = request.getREQUESTEE().getFirstName() + request.getREQUESTEE().getLastName(); //Stores the Customer name to put in return string later
                output += "|<" + requestNumber + ">" + Util.EOL + separatorLine + Util.EOL + "|Name: " + request.getREQUESTEE().getFirstName()
                        + " " + request.getREQUESTEE().getLastName() + Util.EOL + separatorLine + Util.EOL + "|" + request.getStatusToString()
                        + Util.EOL + "|Created: " + request.getCREATIONDATE() + Util.EOL + "|SSN: " + request.getREQUESTEE().getSocialSecurityNumber()
                        + Util.EOL + "|" + request.getAccountName() + Util.EOL;
                requestNumber += 1;
            }
        }if (output.isEmpty()){throw new Exception("This customer has no bank account requests.");}
        output = " Bank Account Requests for " + name + Util.EOL + separatorLine + output;
        return output;
    }
    /* //Don't think we need this?
    public String getBankAccountRequestStatus(String SSN) { //Returns status of all BankAccountRequests for a specific Customer.
        String status = "";                          //Maybe not needed depending on if we want to delete requests from the list when they are denied or approved.
        for (BankAccountRequest request : allBankAccountRequests) {
            if (request.getREQUESTEE().getSocialSecurityNumber().equals(SSN)) {
                if (request.getIsApproved() == null)
                    status =  "Status: Pending" + Util.EOL
                            + request.getCREATIONDATE(); //If the request is pending we show creation date.
                if (request.getIsApproved())
                    status = "Status: Approved" + Util.EOL
                            + "Request was created: " + request.getCREATIONDATE() + Util.EOL //If the request was approved/denied we also show when it was resolved.
                            + "Request was approved: " + request.getRESOLVEDDATE();
                else
                    status = "Status: Denied" + Util.EOL
                            + "Request was created: " + request.getCREATIONDATE() + Util.EOL
                            + "Request was denied: " + request.getRESOLVEDDATE();
                status += request.getIsApproved() + Util.EOL;
            }
        }
        return status;
    }

     */
    //Return an ArrayList of BankAccountRequest objects (A customer can have more than one request) for the specified SSN
    public ArrayList<BankAccountRequest> getSpecificCustomerBankAccountRequests(String SSN) throws Exception{
        ArrayList<BankAccountRequest> bankAccountRequestsList = new ArrayList<>();
        for (BankAccountRequest request : allBankAccountRequests){
           if (request.getREQUESTEE().getSocialSecurityNumber() == SSN){
             bankAccountRequestsList.add(request);
           }
        } if(bankAccountRequestsList.isEmpty()){
            throw new Exception("There are no bank account requests for this user.");
        } else return bankAccountRequestsList;
    }

    //Return the CustomerAccountRequest object for the specified SSN so that we can manipulate it (Approve / Deny)
    public CustomerAccountRequest getCustomerAccountRequest(String SSN) throws Exception{
        for (CustomerAccountRequest request :allCustomerAccountRequests){
            if (request.getSocialSecurityNumber() == SSN){
                return request;
            }
        }
        throw new Exception("There are no account requests for this SSN.");
    }

    //Return String of all Pending bank account requests for all customers, probably standard view for employees since you don't really care for requests that have already been resolved.
    public String printAllPendingBankAccountRequests() throws Exception {
       String output = "";
       String separatorLine = "|" + "-".repeat(30);
       int requestNumber = 1;
        for (BankAccountRequest request : allBankAccountRequests) {
            if (request.getIsApproved() == null) {
                output += "|<" + requestNumber + ">" + Util.EOL + separatorLine + Util.EOL + "|Name: " + request.getREQUESTEE().getFirstName()
                        + " " + request.getREQUESTEE().getLastName() + Util.EOL + separatorLine + Util.EOL + "|" + request.getStatusToString()
                        + Util.EOL + "|Created: " + request.getCREATIONDATE() + Util.EOL + "|SSN: " + request.getREQUESTEE().getSocialSecurityNumber()
                        + Util.EOL + "|" + request.getAccountName() + Util.EOL
                        + "|";
            requestNumber += 1;
            }
        }
        if (output.isEmpty()){throw new Exception("There are no pending bank account requests.");}
        else
            output = " All Pending Bank Account Requests" + Util.EOL + separatorLine + output;
        return output;
    }

    //Gets a list of all requests that has been neither approved nor denied.
    public ArrayList<BankAccountRequest> getAllPendingBankAccountRequests() {
        ArrayList<BankAccountRequest> returnList = new ArrayList<>();
        for (BankAccountRequest request : allBankAccountRequests) {
            if (request.getIsApproved() == null) {
                returnList.add(request);
            }
        }
        return returnList;
    }

    //Gets a stringified specific request from the "getAllPendingBankAccountRequests" method.This is done to show the request information
    public String getSpecificBankAccountRequestFromList(int input) throws Exception{
        if (input < 1 || input-1 > getAllPendingBankAccountRequests().size()){
            throw new Exception("Invalid input.");
        } else {
            return getAllPendingBankAccountRequests().get(input-1).toString();
        }
    }

}

