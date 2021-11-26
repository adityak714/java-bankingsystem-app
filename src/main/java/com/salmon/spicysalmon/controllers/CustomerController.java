package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.models.Customer;

import java.util.HashMap;

public class CustomerController {
    private final HashMap<String, Customer> allCustomers;
    private final TransactionController transactionsController;

    public CustomerController(){
        allCustomers =  new HashMap<>();
        transactionsController =  new TransactionController();
    }

public void createCustomer(String socialSecurityNumber, String password, String firstName, String lastName, double salary, String residentialArea, String occupation) throws Exception{
        if(transactionsController.isSSNUnique(socialSecurityNumber)){
            Customer newCustomer = new Customer(socialSecurityNumber, password, firstName, lastName, salary, residentialArea, occupation);
            allCustomers.put(socialSecurityNumber, newCustomer);
        } else{
            throw new Exception("A customer with that social security number already exists!");
        }
    }
}

