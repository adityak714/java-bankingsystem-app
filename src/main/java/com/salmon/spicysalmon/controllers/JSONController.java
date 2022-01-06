package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.models.BankAccount;
import com.salmon.spicysalmon.models.Customer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JSONController {

    public void readData() throws Exception{
        this.readCustomers();
        this.readEmployees();
        this.readTransactions();
        this.readCustomerAccountRequests();
        this.readBankAccountRequests();
    }


    // reads each key value pair from the Customers.json file and calls the createCustomer method from the customerController
    // also reads the array of accounts in each customer JSON object.
    private void readCustomers() throws Exception {
        CustomerController customerController = new CustomerController();
        JSONParser jsonParser = new JSONParser();
        Object obj;
        try{
            obj = jsonParser.parse(new FileReader("src/main/java/com/salmon/spicysalmon/data/Customers.json"));
        } catch(FileNotFoundException fileNotFoundException){
            // necessary for the json demo, other wise would need to create the complex directory structure
            obj = jsonParser.parse(new FileReader("data/Customers.json"));
        }
        JSONArray customers = (JSONArray) obj;
        for (int i = 0; i < customers.size(); i++) {
            JSONObject customer = (JSONObject) customers.get(i);
            String SSN = (String) customer.get("SSN");
            String password = (String) customer.get("password");
            String firstName = (String) customer.get("firstName");
            String lastName = (String) customer.get("lastName");
            double salary = (double) ((long) customer.get("salary"));
            String residentialArea = (String) customer.get("residentialArea");
            String occupation = (String) customer.get("occupation");
            customerController.createCustomer(SSN, password, firstName, lastName, salary, residentialArea, occupation);
            JSONArray accounts = (JSONArray) customer.get("accounts");
            for (int j = 0; j < accounts.size(); j++) {
                JSONObject account = (JSONObject) accounts.get(j);
                double balance = (double) ((long) account.get("balance"));
                String accountName = (String) account.get("accountName");
                // get account ID formatting correct, in accordance with Customer.java createBankAccount method
                String accID = j < 10 ? "0" + (j + 1) : (j + 1) + "";
                customerController.createBankAccount(SSN, accountName);
                customerController.depositMoney(SSN, accID, balance);
            }
        }
    }

    // reads each key value pair in the Employees.json file, and calls the createEmployee method
    private void readEmployees() throws IOException, ParseException {
        EmployeeController employeeController = new EmployeeController();
        JSONParser jsonParser = new JSONParser();
        Object obj;
        try{
            obj = jsonParser.parse(new FileReader("src/main/java/com/salmon/spicysalmon/data/Employees.json"));
        } catch(FileNotFoundException fileNotFoundException){
            // necessary for the json demo, other wise would need to create the complex directory structure
            obj = jsonParser.parse(new FileReader("data/Employees.json"));
        }
        JSONArray employees = (JSONArray) obj;
        for(int i=0; i<employees.size();i++){
            JSONObject employee = (JSONObject) employees.get(i);
            String SSN = (String) employee.get("SSN");
            String password = (String) employee.get("password");
            String firstName = (String) employee.get("firstName");
            String lastName = (String) employee.get("lastName");
            String startDate = (String) employee.get("startDate");
            Boolean isManager = (Boolean) employee.get("isManager");
            if(isManager != null && isManager){
                employeeController.createManager(SSN, password, firstName, lastName, startDate);
            } else{
                employeeController.createEmployee(SSN, password, firstName, lastName, startDate);
            }
        }
    }


    // reads each key value pair from the Transactions.json file and calls the create transaction method, which creates a pair of transactions
    private void readTransactions() throws IOException, ParseException {
        TransactionController transactionController = new TransactionController();
        JSONParser jsonParser = new JSONParser();
        Object obj;
        try{
            obj = jsonParser.parse(new FileReader("src/main/java/com/salmon/spicysalmon/data/Transactions.json"));
        } catch(FileNotFoundException fileNotFoundException){
            // necessary for the json demo, other wise would need to create the complex directory structure
            obj = jsonParser.parse(new FileReader("data/Transactions.json"));
        }
        JSONArray transactions = (JSONArray) obj;
        for(int i=0; i<transactions.size(); i++){
            JSONObject transaction = (JSONObject) transactions.get(i);
            String acc1 = (String) transaction.get("acc1");
            String acc2 = (String) transaction.get("acc2");
            double amount = (double)((long)transaction.get("amount"));
            String date = (String) transaction.get("date");
            transactionController.createTransaction(acc1, acc2, amount, date);
        }
    }

    // reads each key value pair in the CustomerAccountRequests.json file and calls the createCustomerAccountRequest method
    private void readCustomerAccountRequests() throws IOException, ParseException {
        AccountRequestController accountRequestController = new AccountRequestController();
        JSONParser jsonParser = new JSONParser();
        Object obj;
        try{
            obj = jsonParser.parse(new FileReader("src/main/java/com/salmon/spicysalmon/data/CustomerAccountRequests.json"));
        } catch(FileNotFoundException fileNotFoundException){
            // necessary for the json demo, other wise would need to create the complex directory structure
            obj = jsonParser.parse(new FileReader("data/CustomerAccountRequests.json"));
        }
        JSONArray applications = (JSONArray) obj;
        for(int i=0; i<applications.size(); i++){
            JSONObject application = (JSONObject) applications.get(i);
            String SSN = (String) application.get("SSN");
            String password = (String) application.get("password");
            String firstName = (String) application.get("firstName");
            String lastName = (String) application.get("lastName");
            double salary = (double) ((long) application.get("salary"));
            String residentialArea = (String) application.get("residentialArea");
            String occupation = (String) application.get("occupation");
            accountRequestController.createCustomerAccountRequest(SSN, password, firstName, lastName, salary, residentialArea, occupation);
        }
    }

    // reads each key value pair in the BankAccountRequests.json file and calls the createBankAccountRequest method.
    private void readBankAccountRequests() throws IOException, ParseException {
        CustomerController customerController = new CustomerController();
        AccountRequestController accountRequestController = new AccountRequestController();
        JSONParser jsonParser = new JSONParser();
        Object obj;
        try{
            obj = jsonParser.parse(new FileReader("src/main/java/com/salmon/spicysalmon/data/BankAccountRequests.json"));
        } catch(FileNotFoundException fileNotFoundException){
            // necessary for the json demo, other wise would need to create the complex directory structure
            obj = jsonParser.parse(new FileReader("data/BankAccountRequests.json"));
        }
        JSONArray applications = (JSONArray) obj;
        for(int i=0; i<applications.size(); i++){
            JSONObject application = (JSONObject) applications.get(i);
            String SSN = (String) application.get("SSN");
            String accountName = (String) application.get("accountName");
            Customer applicant = customerController.findCustomer(SSN);
            accountRequestController.createBankAccountRequest(applicant,accountName);
        }
    }
}
