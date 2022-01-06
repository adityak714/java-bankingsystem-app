package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.Util;
import com.salmon.spicysalmon.views.CustomerMenu;
import com.salmon.spicysalmon.views.EmployeeMenu;
import com.salmon.spicysalmon.models.Customer;
import com.salmon.spicysalmon.models.Employee;

public class AuthenticationController {
    //Array to get login info i.e password and social security number
    private String[] getLoginInfo() {
        System.out.println(Util.EOL + "Login: Please fill in your details!");
        String SSN = Util.readLine("Social Security Number: ");
        String password = Util.readPassword("Password: ");
        return new String[]{SSN, password};
    }
  /// Verifies customer login
    public void customerLogin() {
        CustomerController customerController = new CustomerController();
        String[] loginInfo = getLoginInfo();
        String SSN = loginInfo[0];
        String password = loginInfo[1];
        Customer loggedInCustomer = customerController.findCustomer(SSN);
        if (loggedInCustomer != null && loggedInCustomer.verifyPassword(password)) {
            CustomerMenu customerMenu = new CustomerMenu();
            customerMenu.show(SSN); // Added the SSN in the brackets
        } else {
            System.out.println("Username or password incorrect.");
        }
    }
    //Verifies employee login
    public void employeeLogin() {
        String[] loginInfo = getLoginInfo();
        String SSN = loginInfo[0];
        String password = loginInfo[1];
        EmployeeController employeeController = new EmployeeController();
        Employee loggedInEmployee = employeeController.getEmployee(SSN);
        if (loggedInEmployee != null && loggedInEmployee.verifyPassword(password)) {
            EmployeeMenu employeeMenu = new EmployeeMenu();
            employeeMenu.show(SSN);
        } else {
            System.out.println("Username or password incorrect");
        }
    }
}
