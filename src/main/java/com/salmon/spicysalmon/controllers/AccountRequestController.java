package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.Util;
import com.salmon.spicysalmon.models.*;
import com.salmon.spicysalmon.models.Customer;
import java.util.ArrayList;
import java.util.Collections;


public class AccountRequestController {
    private static final ArrayList<BankAccountRequest> allBankAccountRequests = new ArrayList<>();
    private static final ArrayList<CustomerAccountRequest> allCustomerAccountRequests = new ArrayList<>();

    //Creates a new request and puts in the ArrayList of requests
    public void createBankAccountRequest(Customer customer, String accountName) {
        BankAccountRequest BAR = new BankAccountRequest(customer, accountName);
        allBankAccountRequests.add(BAR);
    }

    //Creates a new customer account request
    public void createCustomerAccountRequest(String SSN, String password, String firstName, String lastName, double salary, String residentialArea, String occupation){
        CustomerAccountRequest CAR =  new CustomerAccountRequest(SSN, password, firstName, lastName, salary, residentialArea, occupation);
        allCustomerAccountRequests.add(CAR);
    }

    //Approve a specific BankAccountRequest and creates the bank account for the Customer
    public void approveBankAccountRequest(BankAccountRequest request, String message) { //Sets the Boolean isApproved of the Customer object that is referenced in the specified request to true.
        request.approveRequest(message); //And creates a new BankAccount for the Customer and puts it in the HashMap of BankAccounts.
        request.getREQUESTEE().createBankAccount(request.getAccountName());
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
    public void denyAccountRequest(AccountRequest request, String denyMessage) {
        request.denyRequest(denyMessage); //Passes message to the customer why the request was denied
    }

    //Used by all the bankAccountRequest print methods that has more than one request to print
    public String stringBuilderBankAccountRequest(ArrayList<BankAccountRequest> list){
        int requestNumber = 1;
        StringBuilder sb = new StringBuilder();
        String accountName = "";
        String returnString = "";
        String customerName= "";
        for(BankAccountRequest request : list) {
           if (request.getAccountName().length() > 23) {accountName = request.getAccountName().substring(0,24);} //Shortens bank account name to 20 chars if its longer than that.
           else {accountName = request.getAccountName();}
           if (request.getREQUESTEE().getFirstName().length() > 10){customerName = request.getREQUESTEE().getFirstName().substring(0,11);} //Shortens first name if longer than 10 chars
           else {customerName = request.getREQUESTEE().getFirstName();}
            sb.append(requestNumber + " ".repeat(5-(String.valueOf(requestNumber)).length()) + request.getStatusToString() + " ".repeat(11-request.getStatusToString().length()) + customerName
                   + " " + request.getREQUESTEE().getLastName().charAt(0) +"." + " ".repeat(14-customerName.length()-3)
                   + request.getREQUESTEE().getSocialSecurityNumber() + " ".repeat(13-request.getREQUESTEE().getSocialSecurityNumber().length())
                   + accountName + " ".repeat(25-accountName.length()) + request.getCREATIONDATE().substring(0,10));
            requestNumber += 1;
            if (requestNumber != list.size() + 1) {
                sb.append(Util.EOL);
            }
        }
        returnString = Util.EOL
                + "--------------------------------------------------------------------------------" + Util.EOL
                + "#    " + "Status     " + "Customer      " + "SSN          " + "Account Name             " + "Created   " + Util.EOL
                + "--------------------------------------------------------------------------------" +  Util.EOL
                + sb.toString() + Util.EOL
                + "--------------------------------------------------------------------------------" + Util.EOL;
        return returnString;
    }

    //Returns ArrayList with all Bank Account Requests, copied and reversed
    //because then we get new requests first
    public ArrayList<BankAccountRequest> getAllBankAccountRequests()throws Exception{
        ArrayList<BankAccountRequest> returnList = new ArrayList<>(allBankAccountRequests);
        Collections.reverse(returnList); //Will put most recent ones first
        returnList.sort(AccountRequest::compareTo); //Will then compare Status, puts Pending requests first since those are the most interesting ones for an employee to look at
        if(returnList.isEmpty()){throw new Exception("There are no bank account requests.");}
        else return returnList;
    }

    //Returns a concatenated string of all bank account requests for all users.
    public String printAllBankAccountRequests() throws Exception{ //Maybe sort this
        String output = "All Bank Account Requests" + stringBuilderBankAccountRequest(getAllBankAccountRequests());
        return output;
    }

    //Return an ArrayList of BankAccountRequest objects (A customer can have more than one request) for the specified SSN
    public ArrayList<BankAccountRequest> getBankAccountRequestsForSpecificCustomer(String SSN) throws Exception {
        ArrayList<BankAccountRequest> bankAccountRequestsList = new ArrayList<>();
        for (BankAccountRequest request : allBankAccountRequests) {
            if (request.getREQUESTEE().getSocialSecurityNumber().equals(SSN)) {
                bankAccountRequestsList.add(request);
            }
        }
        if (bankAccountRequestsList.isEmpty()) {
            throw new Exception("There are no bank account requests for this customer.");
        } else return bankAccountRequestsList;
    }

    //Returns a String with the all bank account requests for the specified customer, used by employees
    public String printBankAccountRequestsForSpecificCustomer(String SSN) throws Exception { //Returns list of all requests for the input SSN.
        String output = "All Bank Account Requests for SSN: " + SSN + stringBuilderBankAccountRequest(getBankAccountRequestsForSpecificCustomer(SSN));
        return output;
    }

    //Gets a toStringed specific request from the "getAllPendingBankAccountRequests" method. This is done to show the request information to the employee
    //Takes in a list, so we give it a list when calling it, like this: getSpecificBankAccountRequestFromList(input, getAllBankAccountRequests())
    // So we get the users input e.g. 1 then the right list depending on where in the menu we call this method.
    public BankAccountRequest getSpecificBankAccountRequestFromList(int input) throws Exception {
        ArrayList<BankAccountRequest> list = getAllBankAccountRequests();
        if (input < 0 || input > list.size()) {
            throw new Exception("Invalid input, please choose between 1-" + list.size());
        } else {
            return list.get(input); //gets the index of the users input -1, because we start at 1 not 0
        }                               //We use 0 to go back in the menus
    }

    //Used by all customerAccountRequest methods that has more than one request to print
    public String stringBuilderCustomerAccountRequest(ArrayList<CustomerAccountRequest> list) throws Exception {
        int requestNumber = 1;
        StringBuilder sb = new StringBuilder();
        String name = "";
        String returnString = "";
        for (CustomerAccountRequest request : list) {
            if(request.getFirstName().length()+request.getLastName().length() > 25){
                name = request.getFirstName() + " " + request.getLastName().substring(0, 26-request.getFirstName().length()) + "..";
            }
            else {name = request.getFirstName() + " " + request.getLastName();}
            sb.append(requestNumber)
                    .append(" ".repeat(5-(String.valueOf(requestNumber)).length()))
                    .append(request.getStatusToString())
                    .append(" ".repeat(12-request.getStatusToString().length()))
                    .append(name)
                    .append(" ".repeat(27-name.length()))
                    .append(request.getSocialSecurityNumber()).append(" ".repeat(16-request.getSocialSecurityNumber().length()))
                    .append(request.getCREATIONDATE().substring(0,10));

            requestNumber += 1;
            if (requestNumber != list.size()+1) {
                sb.append(Util.EOL);
            }
        }
        returnString = Util.EOL
                     + "-".repeat(70) + Util.EOL
                     + "#    " + "Status      " + "Name                       " + "SSN             " + "Created   " + Util.EOL
                     + "-".repeat(70) +  Util.EOL + sb.toString() + Util.EOL + "-".repeat(70) + Util.EOL;
        return returnString;
    }

    //Returns ArrayList with all Bank Account Requests, copied and reversed
    //because then we get new requests first
    public ArrayList<CustomerAccountRequest> getAllCustomerAccountRequests() throws Exception{
        ArrayList<CustomerAccountRequest> returnList = new ArrayList<>(allCustomerAccountRequests);
        Collections.reverse(returnList);//This should sort it by creation date (new first) because in allCustomerAccountRequests the oldest will be first in the list.
        returnList.sort(AccountRequest::compareTo);
        if (returnList.isEmpty()){throw new Exception("There are no customer account requests.");}
        else return returnList;
    }

    //Returns a string of all Customer Account Requests
    public String printAllCustomerAccountRequests() throws Exception{ //Test method 31/12
        String output = "All Customer Account Requests" + stringBuilderCustomerAccountRequest(getAllCustomerAccountRequests());
        return output;
    }

    //Used by Employees, Searches for match in both first & ast name of  Customer Account Requests and returns an ArrayList of CustomerAccountRequest
    //Return the CustomerAccountRequest object for the specified SSN so that we can manipulate it (Approve / Deny)
    public CustomerAccountRequest getCustomerAccountRequestBySSN(String SSN) throws Exception {
        for (CustomerAccountRequest request : allCustomerAccountRequests) {
            if (request.getID().equals(SSN)) {
                return request;
            }
        }
        throw new Exception("There are no account requests for this SSN.");
    }

    //Gets a toStringed specific request from the "getAllPendingCustomerAccountRequests" method. This is done to show the request information to the employee
    //Takes in a list, so we give it a list when calling it, like this: getSpecificCustomerAccountRequestFromList(input, getAllCustomerAccountRequests())
    //So we get the users input e.g. 1 then the right list depending on where in the menu we call this method.
    public CustomerAccountRequest getSpecificCustomerAccountRequestFromList(int input) throws Exception {
        ArrayList<CustomerAccountRequest> list = getAllCustomerAccountRequests();
        if (input < 0 || input > list.size()) {
            throw new Exception("Invalid input, please choose between 1- " + list.size());
        } else {
            return list.get(input);
        }
    }
}