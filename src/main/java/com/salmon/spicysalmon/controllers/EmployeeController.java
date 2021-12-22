package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.models.Employee;

import java.util.LinkedHashMap;

public class EmployeeController {
    private static final LinkedHashMap<String, Employee> employeeAccounts = new LinkedHashMap<>();

    public Employee getEmployee(String SSN){
        return employeeAccounts.get(SSN);
    }

    public void createEmployee(String socialSecurityNumber, String password, String firstName, String lastName, String startDate){
            Employee employee = new Employee(socialSecurityNumber, password, firstName, lastName, startDate);
            employeeAccounts.put(socialSecurityNumber, employee);
    }

    public LinkedHashMap<String, Employee> getAllEmployees(){
        return employeeAccounts;
    }

    public String removeEmployee(String SSN){
        //Checks if the employee exists
        if (getEmployee(SSN) == null){
            //If it doesn't exist
            return ("Employee " + SSN + " could not be removed.");
        }else{
            //If it does exist
            employeeAccounts.remove(SSN);
            return ("Employee " + SSN + " was successfully removed.");
        }
    }

    public String toString(String SSN){
        if (employeeAccounts.containsKey(SSN)) {
            return employeeAccounts.get(SSN).toString();
        }
        return "";
    }

}
