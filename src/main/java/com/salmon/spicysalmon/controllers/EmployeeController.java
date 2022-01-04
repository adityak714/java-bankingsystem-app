package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.Util;
import com.salmon.spicysalmon.models.Employee;
import com.salmon.spicysalmon.models.Manager;

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

    public void createManager(String socialSecurityNumber, String password, String firstName, String lastName, String startDate){
        Employee employee = new Manager(socialSecurityNumber, password, firstName, lastName, startDate);
        employeeAccounts.put(socialSecurityNumber, employee);
    }

    public String printAllEmployees(){
        String result = "All Registered Employees" + Util.EOL;
        result += "----------------------------" + Util.EOL;
        if(!employeeAccounts.isEmpty()){
            for(String SSN : employeeAccounts.keySet()){
                Employee currentEmployee = employeeAccounts.get(SSN);
                result += currentEmployee + Util.EOL;
                result += "----------------------------" + Util.EOL;
            }
        } else{
            result += "No employees registered yet.";
        }
        return result;
    }

    public void removeEmployee(String SSN) throws Exception {
        //Checks if the employee exists
        if (getEmployee(SSN) != null){
            //If it exists
            employeeAccounts.remove(SSN);
        }else{
            //If it does not exist
            throw new Exception("Employee with "+SSN+" was not found.");
        }
    }
    //Method to change customer's password
    public void changePassword(String testPassword, String newPassword, String SSN) throws Exception {
        Employee employee = getEmployee(SSN);
        employee.changePassword(testPassword, newPassword);
    }
    public String printEmployee(String SSN) {
        try {
            Employee employee = getEmployee(SSN);
            return employee.toString();
        } catch(Exception ex) {
            return ex.getMessage();
        }
    }

    public String toString(String SSN){
        if (employeeAccounts.containsKey(SSN)) {
            return employeeAccounts.get(SSN).toString();
        }
        return "";
    }

}
