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
    public static ArrayList<BankAccountRequest> allBankAccountRequests = new ArrayList<>();
    public static ArrayList<CustomerAccountRequest> allCustomerAccountRequests = new ArrayList<>();




    //Creates a new request and puts in the ArrayList of requests
    public void createBankAccountRequest(Customer customer, String accountName) {
        BankAccountRequest BAR = new BankAccountRequest(customer, accountName);
        allBankAccountRequests.add(BAR);
    }
    //Creates a new customer account request
    public void createCustomerAccountRequest(String SSN, String password, String firstName, String lastName, double salary, String residentialArea, String occupation){
       /* CustomerController customerController = new CustomerController();
        TransactionController transactionController = new TransactionController();
        if(Util.checkSSNFormat(SSN)){
            if(customerController.findCustomer(SSN) == null && transactionController.checkIfSSNUnique(SSN)) {
                CustomerAccountRequest CAR =  new CustomerAccountRequest(SSN, password, firstName, lastName, salary, residentialArea, occupation);
                allCustomerAccountRequests.add(CAR);
            } else{
                throw new Exception("A customer with that SSN already exists.");
            }
        } else{
            throw new Exception("SSN formatting incorrect, use format YYMMDDXXXX");*/
        CustomerAccountRequest CAR =  new CustomerAccountRequest(SSN, password, firstName, lastName, salary, residentialArea, occupation);
        allCustomerAccountRequests.add(CAR);

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




/*
 +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 |B|A|N|K|A|C|C|O|U|N|T|R|E|Q|U|E|S|T|S|
 +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
           |M|E|T|H|O|D|S|
           +-+-+-+-+-+-+-+
 */
    //Used by all the bankAccountRequest print methods that has more than one request to print
    public String stringBuilderBankAccountRequest(ArrayList<BankAccountRequest> list){
        int requestNumber = 1;
        StringBuilder sb = new StringBuilder();
        String accountName = "";
        String returnString = "";
        for(BankAccountRequest request : list) { //String.format("%-10s",requestNumber); -10 means it would insert 10 empty spaces to the right of requestNumber which would be 1 for the first loop. without the dash
           if (request.getAccountName().length() > 20) {accountName = request.getAccountName().substring(0,21);}
           else {accountName = request.getAccountName();}
            sb.append(requestNumber + " ".repeat(9) + request.getStatusToString() + " ".repeat(12-request.getStatusToString().length()) + request.getREQUESTEE().getFirstName()
                   + " " + request.getREQUESTEE().getLastName().charAt(0) +"." + " ".repeat(13-request.getREQUESTEE().getFirstName().length()-3)
                   + request.getREQUESTEE().getSocialSecurityNumber() + " ".repeat(13-request.getREQUESTEE().getSocialSecurityNumber().length())
                   + accountName + " ".repeat(22-accountName.length()) + request.getCREATIONDATE().substring(0,10));
            requestNumber += 1;
            if (requestNumber != list.size() + 1) {
                sb.append(Util.EOL);
            }
        }
        returnString = Util.EOL
                + "--------------------------------------------------------------------------------" + Util.EOL
                + "Number    " + "Status      " + "Customer     " + "SSN          " + "Account Name          " + "Created   " + Util.EOL
                + "--------------------------------------------------------------------------------" +  Util.EOL
                + sb.toString() + Util.EOL
                + "--------------------------------------------------------------------------------" + Util.EOL;
        return returnString;
    }

    //Returns ArrayList with all Bank Account Requests, copied and reversed
    //because then we get new requests first
    public ArrayList<BankAccountRequest> getAllBankAccountRequests()throws Exception{
        ArrayList<BankAccountRequest> returnList = new ArrayList<>(allBankAccountRequests);
        Collections.reverse(returnList); //This should sort it by creation date (new first) because in allBankAccountRequests the oldest will be first in the list.
        if(returnList.isEmpty()){throw new Exception("There are no bank account requests.");}
        else return returnList;
    }
    //Returns a concatenated string of all bank account requests for all users.
    public String printAllBankAccountRequests() throws Exception{ //Maybe sort this
       String output = "All Bank Account Requests" + stringBuilderBankAccountRequest(getAllBankAccountRequests());
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
        String output = "All Bank Account Request matching name: \"" + name + "\"" + stringBuilderBankAccountRequest(getAllBankAccountRequestsForSearchedName(name));
        return output;
    }
    public BankAccountRequest getSpecificBankAccountRequest(String ID) throws Exception{
        for (BankAccountRequest request : allBankAccountRequests) {
            if (request.getID().equals(ID)) {
                return request;
            }
        }
        throw new Exception("The specified ID does not match any bank account request.");
    }
    //Returns status of all BankAccountRequests for a specific Customer, used by customer menu to show their bank account requests' status.
    public String printBankAccountRequestStatus(String SSN) throws Exception{
        String output = "Your bank account requests" + stringBuilderBankAccountRequest(getBankAccountRequestsForSpecificCustomer(SSN));
        if (getBankAccountRequestsForSpecificCustomer(SSN).isEmpty()) {
            throw new Exception("You have no bank account requests."); //Since this method is used for showing a customers
        }                                                              //requests' status we want to give a different message
        else
        return output;
    }
    //Gets a list of all requests that has been neither approved nor denied. Use this when se
    public ArrayList<BankAccountRequest> getAllPendingBankAccountRequests() throws Exception{
        ArrayList<BankAccountRequest> returnList = new ArrayList<>();
        for (BankAccountRequest request : allBankAccountRequests) {
            if (request.getIsApproved() == null) {
                returnList.add(request);
            }
        }
        if (returnList.isEmpty()){throw new Exception("There are no pending bank account requests.");}
        else return returnList;
    }
    //Return String of all Pending bank account requests for all customers, probably standard view for employees since you don't really care for requests that have already been resolved.
    public String printAllPendingBankAccountRequests() throws Exception{
            String output = "All Pending Bank Account Requests" + stringBuilderBankAccountRequest(getAllPendingBankAccountRequests());
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
        if (input < 1 || input - 1 > list.size()) {
            throw new Exception("Invalid input, please choose between 1-" + list.size());
        } else {
            return list.get(input - 1); //gets the index of the users input -1, because we start at 1 not 0
        }                               //We use 0 to go back in the menus
    }
/*
 +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 |C|U|S|T|O|M|E|R|A|C|C|O|U|N|T|R|E|Q|U|E|S|T|S|
 +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
           |M|E|T|H|O|D|S|
           +-+-+-+-+-+-+-+
*/
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
            sb.append(requestNumber + " ".repeat(9)).append(request.getStatusToString() + " ".repeat(13-request.getStatusToString().length()))
                    .append(name + " ".repeat(29-name.length())).append(request.getSocialSecurityNumber() + " ".repeat(18-request.getSocialSecurityNumber().length())).append(request.getCREATIONDATE().substring(0,10));

            requestNumber += 1;
            if (requestNumber != list.size()+1) {
                sb.append(Util.EOL);
            }
        }
        returnString = Util.EOL
                     + "-".repeat(80) + Util.EOL
                     + "Number    " + "Status       " + "Name                         " + "SSN               " + "Created   " + Util.EOL
                     + "-".repeat(80) +  Util.EOL + sb.toString() + Util.EOL + "-".repeat(80) + Util.EOL;
        return returnString;
    }


    //Returns ArrayList with all Bank Account Requests, copied and reversed
    //because then we get new requests first
    public ArrayList<CustomerAccountRequest> getAllCustomerAccountRequests() throws Exception{
        ArrayList<CustomerAccountRequest> returnList = new ArrayList<>();
        returnList.addAll(allCustomerAccountRequests);
        Collections.reverse(returnList);//This should sort it by creation date (new first) because in allCustomerAccountRequests the oldest will be first in the list.
        if (returnList.isEmpty()){throw new Exception("There are no customer account requests.");}
        else return returnList;
    }
    //Returns a string of all Customer Account Requests
    public String printAllCustomerAccountRequests() throws Exception{ //Test method 31/12
        String output = "All Customer Account Requests" + stringBuilderCustomerAccountRequest(getAllCustomerAccountRequests());
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
        String output = "All Customer Account Requests matching name" + "\"" + name +"\"" + stringBuilderCustomerAccountRequest(getAllCustomerAccountRequestsForSearchedName(name)); //Adds title first then adds the string returned from the stringbuilder method
        return output;
    }

    //Returns the specific BankAccountRequest associated with the ID (UUID 36 char auto-generated) So that we can manipulate it via the menu
    //Gets a list of all requests that has been neither approved nor denied.
    public ArrayList<CustomerAccountRequest> getAllPendingCustomerAccountRequests() throws Exception {
        ArrayList<CustomerAccountRequest> returnList = new ArrayList<>();
        for (CustomerAccountRequest request : allCustomerAccountRequests) {
            if (request.getIsApproved() == null) {
                returnList.add(request);
            }
        }
        if (returnList.isEmpty()){
            throw new Exception("There are no pending customer account requests.");
        } return returnList;
    }
    //Print all pending CustomerAccountRequests
    public String printAllPendingCustomerAccountRequests() throws Exception {
        String output = "All Pending Customer Account Requests" + stringBuilderCustomerAccountRequest(getAllPendingCustomerAccountRequests());
        return output;
    }
    //Gets customeraccountrequest by ID, can probably delete this because we can jsut use the SSN one since it can only be 1 CustomerAccountRequest per SSN, unlike bankaccountrequests
    public CustomerAccountRequest getSpecificCustomerAccountRequest(String ID) throws Exception {
        for (CustomerAccountRequest request : allCustomerAccountRequests) {
            if (request.getID().equals(ID)) {
                return request;
            }
        }
        throw new Exception("The specified ID does not match any customer account request.");
    }
    public String printSpecificCustomerAccountRequest(String ID) throws Exception{
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
    //Print the CAR for the input SSN
    public String printCustomerAccountRequestBySSN(String SSN) throws Exception{
        return getCustomerAccountRequestBySSN(SSN).toString();
    }
    //Gets a toStringed specific request from the "getAllPendingCustomerAccountRequests" method. This is done to show the request information to the employee
    //Takes in a list, so we give it a list when calling it, like this: getSpecificCustomerAccountRequestFromList(input, getAllCustomerAccountRequests())
    //So we get the users input e.g. 1 then the right list depending on where in the menu we call this method.
    public CustomerAccountRequest getSpecificCustomerAccountRequestFromList(int input) throws Exception {
        ArrayList<CustomerAccountRequest> list = getAllCustomerAccountRequests();
        if (input < 1 || input - 1 > list.size()) {
            throw new Exception("Invalid input, please choose between 1- " + list.size());
        } else {
            return list.get(input - 1);
        }
    }
}