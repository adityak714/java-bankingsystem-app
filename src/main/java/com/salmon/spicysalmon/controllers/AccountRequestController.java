package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.Util;
import com.salmon.spicysalmon.models.*;
import com.salmon.spicysalmon.models.Customer;


import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.security.AllPermission;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

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
        request.setRESOLVEDDATE(); //Sets resolvedate
        request.getREQUESTEE().createBankAccount(request.getREQUESTEE().getSocialSecurityNumber(), request.getREQUESTEE().getFirstName(), request.getREQUESTEE().getLastName());
    }
    //Approve a specific CustomerAccountRequest and create a Customer with the info from the Request
    public void approveCustomerAccountRequest(CustomerAccountRequest request, String message) throws Exception{
        CustomerController customerController = new CustomerController();
        request.approveRequest(message);
        request.setRESOLVEDDATE(); //Sets resolved date to current date/time
        customerController.createCustomer(request.getSOCIALSECURITYNUMBER(), request.getPassword(),
                request.getFirstName(), request.getLastName(), request.getSalary(),
                request.getResidentialArea(), request.getOccupation());
    }
    //Deny an Account Request (Maybe need to do casting here? This method is for both BankAccountRequest and CustomerAccountRequest)
    public void denyAccountRequest(AccountRequest request, String denyMessage){
        request.denyRequest(denyMessage); //Passes message to the customer why the request was denied
        request.setRESOLVEDDATE(); //Sets resolved date to current date/time
    }

    //Returns an ArrayList of Strings for all BankAccountRequests
    public ArrayList<String> getAllBankAccountRequests() {
        ArrayList<String> requestList = new ArrayList();
        for (BankAccountRequest request : allBankAccountRequests) {
                requestList.add(request.getID() + " " + request.getREQUESTEE().getSocialSecurityNumber() + " " + request.getREQUESTEE().getFirstName()
                                + " " + request.getREQUESTEE().getLastName() + " " + request.getAccountName() + " " + "(" + request.getCREATIONDATE() + ")" + Util.EOL);
        }
        return requestList;
    }
    //Returns the specific BankAccountRequest associated with the ID (UUID 36 char auto-generated) So that you can manipulate it via the menu
    public BankAccountRequest getSpecificBankAccountRequest(String ID){
        for ( BankAccountRequest request : allBankAccountRequests){
            if (request.getID().equals(ID)){
                return request;
            }
        }
        return null;
    }
    //Returns an ArrayList of Strings that we can print
    public ArrayList<String> getAllCustomerAccountRequests(){
        ArrayList<String> requestList = new ArrayList();
        for (CustomerAccountRequest request : allCustomerAccountRequests) {
            requestList.add(request.getSOCIALSECURITYNUMBER() + " " + request.getFirstName() + " " + request.getLastName() + "(" + request.getCREATIONDATE() + ")" + Util.EOL);
        }
        return requestList;
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
        for (CustomerAccountRequest request : allCustomerAccountRequests) {
            if(request.getIsApproved() == null) {
                output += request.getStatusToString() + request.getID() + " " + request.getSOCIALSECURITYNUMBER() + " "
                        + request.getFirstName() + " " + request.getLastName() + "(" + request.getCREATIONDATE() + ")" + Util.EOL;
            }
        } if (output.isEmpty()){throw new Exception("There are no pending customer account requests.");}
        else return output;
    }
    //Returns a String that we can print with a su
    public String printSpecificCustomerBankAccountRequests(String SSN) throws Exception { //Returns list of all requests for the input SSN.
        String output = "";
        String dash = "-";
        for (BankAccountRequest request : allBankAccountRequests) {
            if (request.getREQUESTEE().getSocialSecurityNumber().equals(SSN)) {
                output += request.getID() + Util.EOL + request.getREQUESTEE().getSocialSecurityNumber() + " " + request.getREQUESTEE().getFirstName()
                        + " " + request.getREQUESTEE().getLastName() + " " + request.getAccountName() + Util.EOL;
            }
        }if (output.isEmpty()){throw new Exception("This customer has no bank account requests.");}
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
            if (request.getSOCIALSECURITYNUMBER() == SSN){
                return request;
            }
        }
        throw new Exception("There are no account requests for this SSN.");
    }
    //Return String of all Pending bank account requests for all customers, probably standard view for employees since you don't really care for requests that have already been resolved.
    public String printAllPendingBankAccountRequests() throws Exception {
       String output = "";
        for (BankAccountRequest request : allBankAccountRequests) {
            if (request.getIsApproved() == null) {
                output += request.getID() + " " + request.getREQUESTEE().getSocialSecurityNumber() + " " + request.getREQUESTEE().getFirstName() //Not sure how we want to do with ID (36 digit UUID)
                        + " " + request.getREQUESTEE().getLastName() + " " + request.getAccountName() + Util.EOL;                               //Probably don't want to show it, but we want to use it to find the specific request
            }
        }
        if (output.isEmpty()){throw new Exception("There are no pending bank account requests.");}
        else
        return output;
    /*  int input = Util.readInt("Select a request");
      methodName(input);


    getSpecificBankAccountRequest(printPendingBankAccountRequests().get(input).substring(0,35)); //Gets UUID and gives it to the getSpecificBankAccountRequest method
*/
    }
}

