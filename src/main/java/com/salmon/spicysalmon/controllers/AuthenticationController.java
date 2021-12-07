package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.Util;
import com.salmon.spicysalmon.controllers.CustomerController;
import com.salmon.spicysalmon.controllers.EmployeeController;
import com.salmon.spicysalmon.models.Customer;
import com.salmon.spicysalmon.models.Employee;
import com.salmon.spicysalmon.models.Menu;

public class AuthenticationController {
    String CUSTOMER_HEADING = "Customer Menu: Please choose a valid option.";
    String[] CUSTOMER_OPTIONS = {
            "Log out",
            "View Bank Accounts",
            "Apply for new Bank Account"
    };
    String EMPLOYEE_HEADING = "Employee Menu: Please choose a valid option.";
    String[] EMPLOYEE_OPTIONS = {
            "Log out",
            "Delete account",
            "Approve new customer accounts",
            "Approve new bank accounts",
    };


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
        Customer loggedInCustomer = customerController.getCustomer(SSN);
        if (loggedInCustomer != null && loggedInCustomer.verifyPassword(password)) {
            showCustomerMenu();
        } else {
            System.out.println("Username or password incorrect.");
        }
    }

    private void showCustomerMenu(){
        Menu customerMenu = new Menu(CUSTOMER_HEADING, CUSTOMER_OPTIONS);
        int userInput = 0;
        do{
            System.out.print(customerMenu);
            userInput = customerMenu.getValidOption();
            switch (userInput) {
                case 1 -> System.out.println("View Bank Accounts");
                case 2 -> System.out.println("apply new bank account");
                case 0, default -> {}
            }
        }while(userInput != 0);
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
    private void showEmployeeMenu(){
        Menu employeeMenu = new Menu(EMPLOYEE_HEADING, EMPLOYEE_OPTIONS);
        int userInput = 0;
        do {
            System.out.println(employeeMenu);
            userInput = employeeMenu.getValidOption();
            switch (userInput){
                case 1:
                    System.out.println("Delete account"); // ADD LOGIC HERE
                    break;
                case 2:
                    System.out.println("Approve new customer accounts"); // ADD LOGIC HERE
                    break;
                case 3:
                    System.out.println("Approve new bank accounts"); // ADD LOGIC HERE
                    break;
                default:
                    System.out.println("goodbye");
                    break;
            }
        }while (userInput != 0);
    }
}
