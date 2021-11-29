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
                System.out.println("kladdkaka009");
                return customer;
            }
        }
        System.out.println("Kladdkaka2323");
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
        String message = "All registered customers:" + EOL;
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
            return (firstName + EOL + lastName + EOL + customer.getOccupation() + EOL + customer.getResidentialArea());
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
}


