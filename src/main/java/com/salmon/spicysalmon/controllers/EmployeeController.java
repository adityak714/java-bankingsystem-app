package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.models.Customer;
import com.salmon.spicysalmon.models.Employee;
import com.salmon.spicysalmon.models.User;

import javax.swing.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ThreadPoolExecutor;

public class EmployeeController {
    private final LinkedHashMap<String, Employee> employeeAccounts;
    private final TransactionController transactionsController;
    private final ApplicationController applicationController;

    public EmployeeController(){
        employeeAccounts = new LinkedHashMap<>();
        transactionsController =  new TransactionController();
        applicationController = new ApplicationController();
    }

    public Employee getEmployee(String SSN){
    return employeeAccounts.get(SSN);
    }
    public void createEmployee(String socialSecurityNumber, String password, String firstName, String lastName, String title, String startDate)throws Exception {
            Employee employee = new Employee(socialSecurityNumber, password, firstName, lastName, title, startDate);
            employeeAccounts.put(socialSecurityNumber, employee);
        }
        public LinkedHashMap<String, Employee> getAllEmployees(){
            return employeeAccounts;
        }
    public String toString(String SSN) throws Exception{
        if (employeeAccounts.containsKey(SSN)) {
            return employeeAccounts.get(SSN).toString();
        }
        else throw new Exception("Employee does not exist.");
    }
    public void setEmployeeTitle(String SSN, String newTitle){
        employeeAccounts.get(SSN).setTitle(newTitle);
    }
    public void approveApplication(){

    }
}
