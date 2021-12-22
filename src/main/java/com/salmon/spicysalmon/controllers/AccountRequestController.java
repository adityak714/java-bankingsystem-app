package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.Util;
import com.salmon.spicysalmon.models.*;
import com.salmon.spicysalmon.models.Customer;


import java.util.ArrayList;
import java.util.Collections;

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
    public void createCustomerAccountRequest(String socialSecurity, String password, String firstName, String lastName, double salary, String residentialArea, String occupation) {
        allCustomerAccountRequests.add(new CustomerAccountRequest(socialSecurity, password, firstName, lastName, salary, residentialArea, occupation));
    }
    //Adds a bank account request to the allBankAccountRequests list in case it has been misplaced
    public void addBankAccountRequest(BankAccountRequest request) {
        allBankAccountRequests.add(request);
    }
    //Adds a customer account request to the  allCustomerAccountRequests list in case it has been misplaced
    public void addCustomerAccountRequest(CustomerAccountRequest request) {
        allCustomerAccountRequests.add(request);
    }
    //Deletes the specified BankAccountRequest
    public void deleteBankAccountRequest(BankAccountRequest request) {
        allBankAccountRequests.remove(request);
    }
    //Deletes the specified CustomerAccountRequest
    public void deleteCustomerAccountRequest(CustomerAccountRequest request) {
        allCustomerAccountRequests.remove(request);
    }
    //Approve a specific BankAccountRequest and creates the bank account for the Customer
    public void approveBankAccountRequest(BankAccountRequest request, String message) {    //Sets the Boolean isApproved of the Customer object that is referenced in the specified request to true.
        request.approveRequest(message); //And creates a new BankAccount for the Customer and puts it in the HashMap of BankAccounts.

        request.getREQUESTEE().createBankAccount(request.getAccountName());
    }
    //Approve a specific CustomerAccountRequest and create a Customer with the info from the Request
    public void approveCustomerAccountRequest(CustomerAccountRequest request, String message) {
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


/*
 +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 |B|A|N|K|A|C|C|O|U|N|T|R|E|Q|U|E|S|T|S|
 +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
           |M|E|T|H|O|D|S|
           +-+-+-+-+-+-+-+
 */
    //Returns ArrayList with all Bank Account Requests, copied and reversed
    //because then we get new requests first
    public ArrayList<BankAccountRequest> getAllBankAccountRequests(){
        ArrayList<BankAccountRequest> returnList = new ArrayList<>(allBankAccountRequests);
        Collections.reverse(returnList); //This should sort it by creation date (new first) because in allBankAccountRequests the oldest will be first in the list.
        return returnList;
    }
    //Returns a concatenated string of all bank account requests for all users.
    public String printAllBankAccountRequests() { //Maybe sort this
        String output = "";
        int requestNumber = 1;
        for (BankAccountRequest request : getAllBankAccountRequests()) {
            output += " ".repeat(1) + requestNumber + " ".repeat(10) + request.getStatusToString() + " ".repeat(14-request.getStatusToString().length()) + request.getREQUESTEE().getFirstName()
                    + " " + request.getREQUESTEE().getLastName().charAt(0) +"." + " ".repeat(14-request.getREQUESTEE().getFirstName().length()-3)
                    + request.getREQUESTEE().getSocialSecurityNumber() + " ".repeat(14-request.getREQUESTEE().getSocialSecurityNumber().length())
                    + request.getAccountName() + " ".repeat(18-request.getAccountName().length()) + request.getCREATIONDATE() + Util.EOL;
            requestNumber += 1;

        }
        output = "All Bank Account Requests" + Util.EOL
                + "-".repeat(80) + Util.EOL
                + "Number      " + "Status        " + "Customer     " + "SSN           " + "Account Name      " + "Created   " + Util.EOL
                + "-".repeat(80) +  Util.EOL + output + Util.EOL + "-".repeat(80) + Util.EOL;
        return output;
    }
    public ArrayList<BankAccountRequest> getAllBankAccountRequestsForSearchedName(String name)throws Exception {
        ArrayList<BankAccountRequest> returnList = new ArrayList<>();
        for (BankAccountRequest request : allBankAccountRequests) {
            if (request.getREQUESTEE().getFirstName().equals(name) || request.getREQUESTEE().getLastName().equals(name)) {
                returnList.add(request);
            }

        }
        if (returnList.isEmpty()){
            throw new Exception("No bank account requests matched your search");
        }
        return returnList;
    }
    //Method used if employee wants to search for requests with a specific name
    public String printAllBankAccountRequestsForSearchedName(String name) throws Exception{
        String output = "";
        int requestNumber = 1;
        for (BankAccountRequest request : getAllBankAccountRequestsForSearchedName(name)) {
                output += " ".repeat(1) + requestNumber + " ".repeat(10) + request.getStatusToString() + " ".repeat(14-request.getStatusToString().length()) + request.getREQUESTEE().getFirstName()
                        + " " + request.getREQUESTEE().getLastName().charAt(0) +"." + " ".repeat(13-request.getREQUESTEE().getFirstName().length()-3)
                        + request.getREQUESTEE().getSocialSecurityNumber() + " ".repeat(14-request.getREQUESTEE().getSocialSecurityNumber().length())
                        + request.getAccountName() + " ".repeat(18-request.getAccountName().length()) + request.getCREATIONDATE() + Util.EOL;
                requestNumber += 1;
        }
        if (output.isEmpty()) {
            throw new Exception("No bank account requests matched your search");
        } else output = "All Bank Account Request matching name: \"" + name + "\"" + Util.EOL
                        + "-".repeat(80) +  Util.EOL
                        + "Number      " + "Status        " + "Customer     " + "SSN           " + "Account Name      " + "Created   " + Util.EOL
                        + "-".repeat(80) +  Util.EOL + output + Util.EOL + "-".repeat(80) + Util.EOL;
        return output;
    }
    public BankAccountRequest getSpecificBankAccountRequest(String ID) throws Exception{
        for (BankAccountRequest request : allBankAccountRequests) {
            if (request.getID().equals(ID)) {
                return request;
            }
        }
        throw new Exception("Customer has no bank account requests.");
    }
    public String getBankAccountRequestStatus(String SSN) throws Exception{ //Returns status of all BankAccountRequests for a specific Customer.
        String output = "";
        String name = ""; //Used to store Customer name for use later
        int requestNumber = 1;  //Used to number the different requests if more than one

        for (BankAccountRequest request : getBankAccountRequestsForSpecificCustomer(SSN)) {
            name = request.getREQUESTEE().getFirstName() + request.getREQUESTEE().getLastName(); //Stores the Customer name to put in return string later
            output += " ".repeat(1) + requestNumber + " ".repeat(13) + request.getStatusToString() + " ".repeat(14-request.getStatusToString().length()) + request.getREQUESTEE().getFirstName()
                    + " " + request.getREQUESTEE().getLastName().charAt(0) +"." + " ".repeat(14-request.getREQUESTEE().getFirstName().length()-3)
                    + request.getREQUESTEE().getSocialSecurityNumber() + " ".repeat(11-request.getREQUESTEE().getSocialSecurityNumber().length())
                    + request.getAccountName() + " ".repeat(18-request.getAccountName().length()) + request.getCREATIONDATE() + Util.EOL;
            requestNumber += 1;
        }
        if (output.isEmpty()) {
            throw new Exception("You have no bank account requests.");
        }
        output = "Your bank account requests " + name + Util.EOL
                + "-".repeat(80) + Util.EOL
                + "Number      " + "Status        " + "Customer     " + "SSN           " + "Account Name      " + "Created   " + Util.EOL
                + "-".repeat(80) +  Util.EOL + output + Util.EOL + "-".repeat(80) + Util.EOL;
        return output;
    }
    //Gets a list of all requests that has been neither approved nor denied. Use this when se
    public ArrayList<BankAccountRequest> getAllPendingBankAccountRequests() {
        ArrayList<BankAccountRequest> returnList = new ArrayList<>();
        for (BankAccountRequest request : allBankAccountRequests) {
            if (request.getIsApproved() == null) {
                returnList.add(request);
            }
        }
        return returnList;
    }
    //Return String of all Pending bank account requests for all customers, probably standard view for employees since you don't really care for requests that have already been resolved.
    public String printAllPendingBankAccountRequests() throws Exception {
        String output = "";
        String separatorLine = "|" + "-".repeat(30);
        int requestNumber = 1;
        for (BankAccountRequest request : allBankAccountRequests) {
            if (request.getIsApproved() == null) {
                output += " ".repeat(1) + requestNumber + " ".repeat(10) + request.getStatusToString() + " ".repeat(14-request.getStatusToString().length()) + request.getREQUESTEE().getFirstName()
                        + " " + request.getREQUESTEE().getLastName().charAt(0) +"." + " ".repeat(13-request.getREQUESTEE().getFirstName().length()-3)
                        + request.getREQUESTEE().getSocialSecurityNumber() + " ".repeat(14-request.getREQUESTEE().getSocialSecurityNumber().length())
                        + request.getAccountName() + " ".repeat(18-request.getAccountName().length()) + request.getCREATIONDATE() + Util.EOL;
                requestNumber += 1;
            }
        }
        if (output.isEmpty()) {
            throw new Exception("There are no pending bank account requests.");
        } else
            output = "All Pending Bank Account Requests" + Util.EOL
                    + "-".repeat(80) + Util.EOL
                    + "Number      " + "Status        " + "Customer     " + "SSN           " + "Account Name      " + "Created   " + Util.EOL
                    + "-".repeat(80) +  Util.EOL + output + Util.EOL + "-".repeat(80) + Util.EOL;
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
            throw new Exception("There are no bank account requests for this user.");
        } else return bankAccountRequestsList;
    }
    //Returns a String with the all bank account requests for the specified customer, for employee
    public String printBankAccountRequestsForSpecificCustomer(String SSN) throws Exception { //Returns list of all requests for the input SSN.
        String output = "";
        String name = ""; //Used to store Customer name for use later
        int requestNumber = 1;  //Used to number the different requests if more than one

        for (BankAccountRequest request : getBankAccountRequestsForSpecificCustomer(SSN)) {
            name = request.getREQUESTEE().getFirstName() + request.getREQUESTEE().getLastName(); //Stores the Customer name to put in return string later
            output += " ".repeat(1) + requestNumber + " ".repeat(13) + request.getStatusToString() + " ".repeat(14-request.getStatusToString().length()) + request.getREQUESTEE().getFirstName()
                    + " " + request.getREQUESTEE().getLastName().charAt(0) +"." + " ".repeat(14-request.getREQUESTEE().getFirstName().length()-3)
                    + request.getREQUESTEE().getSocialSecurityNumber() + " ".repeat(11-request.getREQUESTEE().getSocialSecurityNumber().length())
                    + request.getAccountName() + " ".repeat(18-request.getAccountName().length()) + request.getCREATIONDATE() + Util.EOL;
            requestNumber += 1;
        }
        if (output.isEmpty()) {
            throw new Exception("This customer has no bank account requests.");
        }
        output = "All Bank Account Requests for " + name + Util.EOL
                + "-".repeat(80) + Util.EOL
                + "Number      " + "Status        " + "Customer     " + "SSN           " + "Account Name      " + "Created   " + Util.EOL
                + "-".repeat(80) +  Util.EOL + output + Util.EOL + "-".repeat(80) + Util.EOL;
        return output;
    }
    //Gets a toStringed specific request from the "getAllPendingBankAccountRequests" method. This is done to show the request information to the employee
    //Takes in a list, so we give it a list when calling it, like this: getSpecificBankAccountRequestFromList(input, getAllBankAccountRequests())
    // So we get the users input e.g. 1 then the right list depending on where in the menu we call this method.
    public BankAccountRequest getSpecificBankAccountRequestFromList(int input, ArrayList<BankAccountRequest> list) throws Exception {
        if (input < 1 || input - 1 > list.size()) {
            throw new Exception("Invalid input, you must choose between 1-" + list.size());
        } else {
            return list.get(input - 1);
        }
    }
/*
 +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 |C|U|S|T|O|M|E|R|A|C|C|O|U|N|T|R|E|Q|U|E|S|T|S|
 +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
           |M|E|T|H|O|D|S|
           +-+-+-+-+-+-+-+
*/
    //Returns ArrayList with all Bank Account Requests, copied and reversed
    //because then we get new requests first
    public ArrayList<CustomerAccountRequest> getAllCustomerAccountRequests(){
        ArrayList<CustomerAccountRequest> returnList = new ArrayList<>(allCustomerAccountRequests);
        Collections.reverse(returnList);//This should sort it by creation date (new first) because in allCustomerAccountRequests the oldest will be first in the list.
        return returnList;
    }
    //Returns a string of all Customer Account Requests
    public String printAllCustomerAccountRequests() {
        String output = "";
        int requestNumber = 1;

        for (CustomerAccountRequest request : getAllCustomerAccountRequests()) {
            output += " ".repeat(1) + requestNumber + " ".repeat(10) + request.getStatusToString() + " ".repeat(17-request.getStatusToString().length()) + request.getFirstName()
                    + " " + request.getLastName().charAt(0) +"." + " ".repeat(20-request.getFirstName().length()-3)
                    + request.getSocialSecurityNumber() + " ".repeat(22-request.getSocialSecurityNumber().length()) + Util.EOL;
            requestNumber += 1;

        }
        output = "All Customer Account Requests" + Util.EOL
                + "-".repeat(80) + Util.EOL
                + "Number      " + "Status           " + "Name                " + "SSN   			       " + "Created   " + Util.EOL
                + "-".repeat(80) +  Util.EOL + output + Util.EOL + "-".repeat(80) + Util.EOL;
        return output;
    }
    public ArrayList<CustomerAccountRequest> getAllCustomerAccountRequestsForSearchedName(String name)throws Exception {
        ArrayList<CustomerAccountRequest> returnList = new ArrayList<>();
        for (CustomerAccountRequest request : allCustomerAccountRequests) {
            if (request.getFirstName().equals(name) || request.getLastName().equals(name)) {
                returnList.add(request);
            }
        }
        if (returnList.isEmpty()){
            throw new Exception("No customer account requests matched your search");
        }
        return returnList;
    }
    //Used by Employees, Searches for matches in both First & Last name of Customer Account Requests and returns a concatenated String of all matches
    public String printAllCustomerAccountRequestsForSearchedName(String name) throws Exception {
        String output = "";
        int requestNumber = 1;
        for (CustomerAccountRequest request : getAllCustomerAccountRequestsForSearchedName(name)) {
            if (request.getFirstName().equals(name) || request.getLastName().equals(name)) {
                output += " ".repeat(1) + requestNumber + " ".repeat(10) + request.getStatusToString() + " ".repeat(17-request.getStatusToString().length()) + request.getFirstName()
                        + " " + request.getLastName().charAt(0) +"." + " ".repeat(20-request.getFirstName().length()-3)
                        + request.getSocialSecurityNumber() + " ".repeat(22-request.getSocialSecurityNumber().length()) + Util.EOL;
                requestNumber += 1;
            }
        }
        if (output.isEmpty()) {
            throw new Exception("No bank account requests matched your search");
        } else output = "All Customer Account Request matching name: \"" + name + "\"" + Util.EOL
                + "-".repeat(80) +  Util.EOL
                + "Number      " + "Status           " + "Name                " + "SSN   			       " + "Created   " + Util.EOL
                + "-".repeat(80) +  Util.EOL + output + Util.EOL + "-".repeat(80) + Util.EOL;
        return output;
    }
    //Returns the specific BankAccountRequest associated with the ID (UUID 36 char auto-generated) So that we can manipulate it via the menu
    //Gets a list of all requests that has been neither approved nor denied.
    public ArrayList<CustomerAccountRequest> getAllPendingCustomerAccountRequests() {
        ArrayList<CustomerAccountRequest> returnList = new ArrayList<>();
        for (CustomerAccountRequest request : allCustomerAccountRequests) {
            if (request.getIsApproved() == null) {
                returnList.add(request);
            }
        }
        return returnList;
    }
    //Print all pending CustomerAccountRequests
    public String printAllPendingCustomerAccountRequests() throws Exception {
        String output = "";
        int requestNumber = 1;
        for (CustomerAccountRequest request : getAllPendingCustomerAccountRequests()) {

                output += " ".repeat(1) + requestNumber + " ".repeat(10) + request.getStatusToString() + " ".repeat(17-request.getStatusToString().length()) + request.getFirstName()
                        + " " + request.getLastName().charAt(0) +"." + " ".repeat(20-request.getFirstName().length()-3)
                        + request.getSocialSecurityNumber() + " ".repeat(22-request.getSocialSecurityNumber().length()) + Util.EOL;
                requestNumber += 1;

        }
        if (getAllPendingCustomerAccountRequests() == null) {
            throw new Exception("There are no pending customer account requests.");
        } else
            output = "All Pending Customer Account Requests" + Util.EOL
                    + "-".repeat(80) + Util.EOL
                    + "Number      " + "Status        " + "Customer     " + "SSN           " + "Account Name      " + "Created   " + Util.EOL
                    + "-".repeat(80) +  Util.EOL + output + Util.EOL + "-".repeat(80) + Util.EOL;
        return output;
    }
    public CustomerAccountRequest getSpecificCustomerAccountRequest(String ID) {
        for (CustomerAccountRequest request : allCustomerAccountRequests) {
            if (request.getID().equals(ID)) {
                return request;
            }
        }
        return null;
    }
    public String printSpecificCustomerAccountRequest(String ID){
        return getSpecificCustomerAccountRequest(ID).toString();
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
    public CustomerAccountRequest getSpecificCustomerAccountRequestFromList(int input, ArrayList<CustomerAccountRequest> list) throws Exception {
        if (input < 1 || input - 1 > list.size()) {
            throw new Exception("Invalid number, try again");
        } else {
            return list.get(input - 1);
        }
    }
}