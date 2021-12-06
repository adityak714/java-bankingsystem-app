package com.salmon.spicysalmon;

import com.salmon.spicysalmon.controllers.CustomerController;
import com.salmon.spicysalmon.controllers.EmployeeController;
import com.salmon.spicysalmon.models.Customer;

public class LoginMenu {
    String CUSTOMER_HEADING = "Customer Menu: Please choose a valid option.";
    String[] CUSTOMER_OPTIONS = {
            "View Bank Accounts",
            "Apply for new Bank Account",
            "Log out"
    };


    private String[] getLoginInfo(){
        System.out.println("Login.");
        String SSN = Util.readLine("Social Security Number: ");
        String password = Util.readLine("Password: ");
        return new String[]{SSN, password};
    }

    public void loginCustomer(){
        String[] loginInfo = getLoginInfo();
        String SSN = loginInfo[0];
        String password = loginInfo[1];
        CustomerController customerController = new CustomerController();
        Customer loggedInCustomer = customerController.getCustomer(SSN);
        if(loggedInCustomer != null && loggedInCustomer.verifyPassword(password)){
            Menu customerMenu = new Menu(CUSTOMER_HEADING, CUSTOMER_OPTIONS);
        } else{
            System.out.println("Username or password incorrect.");
        }
    }

    public void loginEmployee(){
        String[] loginInfo = getLoginInfo();
    }
}
