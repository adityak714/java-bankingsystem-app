package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.UserIO;
import com.salmon.spicysalmon.models.BankAccount;
import com.salmon.spicysalmon.models.Customer;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerController {
    private static final HashMap<String, Customer> allCustomers = new HashMap<>(); // "customerList" is a better name?
    private final TransactionController transactionsController;
    private final ArrayList<Customer> customerList = new ArrayList<>();

    public CustomerController(){
        transactionsController = new TransactionController();
    }

    public void createCustomer(String socialSecurityNumber, String password, String firstName, String lastName, double salary, String residentialArea, String occupation) throws Exception{
            if(transactionsController.checkIfSSNUnique(socialSecurityNumber)){
                Customer newCustomer = new Customer(socialSecurityNumber, password, firstName, lastName, salary, residentialArea, occupation);
                allCustomers.put(socialSecurityNumber, newCustomer);
                customerList.add(newCustomer);
            } else {
                throw new Exception("A customer with that social security number already exists!");
            }
    }

    public Customer getCustomer(String SSN){
        return allCustomers.get(SSN);
    }

    public Customer findCustomer(String SSN) {
        for (Customer customer : customerList) {
            if (customer.getSocialSecurityNumber().equals(SSN)) {
                return customer;
            }
        }
        return null;
    }

    public String removeCustomer(String SSN) {
        Customer customer = findCustomer(SSN);

        if(customer == null) {
            return ("Customer " + SSN + " could not be removed.");
        }
        customerList.remove(customer);
        return ("Customer " + SSN + " was successfully removed.");
    }

    public String printAllCustomers(){
        String EOL = System.lineSeparator();
        if(customerList.isEmpty()){
            return "No customers registered yet.";
        }
        String message = "All registered customers:" + EOL + "-------------------------" + EOL;
        for (Customer customer: customerList) {
            message += printCustomer(customer.getSocialSecurityNumber()) + EOL;
        }
        return message;
    }

    public String printCustomer(String SSN) {
        String EOL = System.lineSeparator();
        Customer customer = findCustomer(SSN);
        if (customer == null) {
            return "Customer " + SSN + " was not registered yet.";
        } else {
            String firstName = customer.getFirstName();
            String lastName = customer.getLastName();
            return ("Name: " + firstName + " " + lastName + EOL + "Occupation: " + customer.getOccupation() + EOL + "Residential Area: " + customer.getResidentialArea() + EOL);
        }
    }

    public String printSpecificCustomer(String SSN) {
        Customer customer = findCustomer(SSN);
        System.out.println("kladdkaka45");
        if(customer == null) {
            return ("Customer " + SSN + " could not be found.");
        }
        return printCustomer(customer.getSocialSecurityNumber());
    }

    public String createBankAccount(String SSN, String accountNumber, String customerFirstName, String customerLastName){
        Customer customer = findCustomer(SSN);
        if(customer == null){
            return "Customer does not exist.";
        }else{
            BankAccount newAccount = new BankAccount(accountNumber, customerFirstName, customerLastName);
            customer.getCustomerAccounts().add(newAccount);
            return "Account " + accountNumber + " created successfully.";
        }
    }

    public String depositMoney(String SSN, String accountNumber, double depositAmount) {
        BankAccount account = findBankAccount(SSN, accountNumber);
        if (account == null) {
            return "Account does not exist";
        } else {
            account.setBalance(depositAmount + account.getBalance());
            System.out.println(account.getBalance());
            return depositAmount + "has been added to account: " + accountNumber;
        }
    }

    public BankAccount findBankAccount(String SSN, String accountNumber) {
        Customer customer = findCustomer(SSN);
        for (BankAccount account : customer.getCustomerAccounts()) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }
}


