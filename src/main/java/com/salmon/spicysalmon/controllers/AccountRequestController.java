package com.salmon.spicysalmon.controllers;
import com.salmon.spicysalmon.Util;

import com.salmon.spicysalmon.models.*;
import com.salmon.spicysalmon.models.Customer;


import java.util.ArrayList;

/* Maybe Customers should have an ArrayList with their requests instead or both?
   Not sure if we can have the Customers see the status of their requests,
   but this is maybe solved once we connect the GUI?
 */
public class AccountRequestController {
    public final ArrayList<BankAccountRequest> allBankAccountRequests;
    public final ArrayList<CustomerAccountRequest> allCustomerAccountRequests;
    public static CustomerController customerController;
    public AccountRequestController() {
        allBankAccountRequests = new ArrayList<>();
        allCustomerAccountRequests = new ArrayList<>();
        customerController = new CustomerController();
    }

    public ArrayList<BankAccountRequest> getallBankAccountRequests() {
        return allBankAccountRequests;
    }

    public ArrayList<CustomerAccountRequest> getAllCustomerAccountRequests() {
        return allCustomerAccountRequests;
    }

    public String printallBankAccountRequests() {
        return allBankAccountRequests.toString();
    }

    public ArrayList<String> getBankAccountRequests(String SSN) { //Returns list of all requests for the input SSN.
        ArrayList<String> specificUserAccountList = new ArrayList<>();
        for (BankAccountRequest request : allBankAccountRequests) {
            if (request.getRequestee().getSocialSecurityNumber().equals(SSN)) {
                specificUserAccountList.add(request.toString() + "\n");
            }
        }
        return specificUserAccountList;
    }

    public ArrayList<String> getBankAccountRequests(Customer customer) { //Returns list of all requests for the requested Customer object.
        ArrayList<String> specificUserAccountList = new ArrayList<>();
        for (BankAccountRequest request : allBankAccountRequests) {
            if (request.getRequestee().getSocialSecurityNumber().equals(customer.getSocialSecurityNumber())) {
                specificUserAccountList.add(request.toString() + "\n");
            }
        }
        return specificUserAccountList;
    }

    public String getBankAccountRequestStatus(String SSN) { //Returns status of all BankAccountRequests for a specific Customer.
        String output = "";                          //Maybe not needed depending on if we want to delete requests from the list when they are denied or approved.
        for (BankAccountRequest request : allBankAccountRequests) {
            if (request.getRequestee().getSocialSecurityNumber().equals(SSN)) {
                output += request.getApprovalStatus() + "\n";
            }
        }
        return output;
    }

    public void createBankAccountRequest(Customer customer, String accountName) { //Creates a new request and puts in the ArrayList of requests
        allBankAccountRequests.add(new BankAccountRequest(customer, accountName));
    }

    public void addBankAccountRequest(BankAccountRequest request) { //Adds
        allBankAccountRequests.add(request);
    }

    public void deleteBankAccountRequest(BankAccountRequest request) { //Deletes the specified request
        allBankAccountRequests.remove(request);
    }

    public void approveBankAccountRequest(BankAccountRequest request, String message) {    //Sets the Boolean isApproved of the Customer object that is referenced in the specified request to true.
        request.approveRequest(message); //And creates a new BankAccount for the Customer and puts it in the HashMap of BankAccounts.
        request.getRequestee().createBankAccount(request.getRequestee().getSocialSecurityNumber(), request.getRequestee().getFirstName(), request.getRequestee().getLastName());
    }

    public void approveCustomerAccountRequest(CustomerAccountRequest request, String message) throws Exception{
        request.approveRequest(message);
        customerController.createCustomer(request.getSOCIALSECURITYNUMBER(), request.getPassword(),
                                            request.getFirstName(), request.getLastName(), request.getSalary(),
                                            request.getResidentialArea(), request.getOccupation());

    }

    public void denyAccountRequest(AccountRequest request, String denyMessage){
        request.denyRequest(denyMessage); //Passes message to the customer why the request was denied
    }
}

