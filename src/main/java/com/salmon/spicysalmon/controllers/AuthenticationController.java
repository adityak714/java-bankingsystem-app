package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.Util;
import com.salmon.spicysalmon.controllers.CustomerController;
import com.salmon.spicysalmon.controllers.EmployeeController;
import com.salmon.spicysalmon.menus.CustomerMenu;
import com.salmon.spicysalmon.models.Customer;
import com.salmon.spicysalmon.models.Employee;
import com.salmon.spicysalmon.models.Menu;

public class AuthenticationController {

    private String[] getLoginInfo(){
        System.out.println(Util.EOL + "Login: Please fill in your details!");
        String SSN = Util.readLine("Social Security Number: ");
        String password = Util.readLine("Password: ");
        return new String[]{SSN, password};
    }

    public void customerLogin(){
        CustomerController customerController = new CustomerController();
        String[] loginInfo = getLoginInfo();
        String SSN = loginInfo[0];
        String password = loginInfo[1];
        Customer loggedInCustomer = customerController.findCustomer(SSN);
        if (loggedInCustomer != null && loggedInCustomer.verifyPassword(password)) {
            CustomerMenu customerMenu = new CustomerMenu();
            customerMenu.show();
        } else {
            System.out.println("Username or password incorrect.");
        }
    }

    public void employeeLogin(){
        String[] loginInfo = getLoginInfo();
        String SSN = loginInfo[0];
        String password = loginInfo[1];
        EmployeeController employeeController = new EmployeeController();
//        Employee loggedInEmployee = employeeController.getEmployee(SSN);
//        if (loggedInEmployee != null && loggedInEmployee.verifyPassword(password)){
//            showEmployeeMenu();
//        }else {
//            System.out.println("Username or password incorrect");
//        }
    }
}
