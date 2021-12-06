package com.salmon.spicysalmon;

import com.salmon.spicysalmon.controllers.CustomerController;
import com.salmon.spicysalmon.controllers.EmployeeController;

public class LoginMenu {

    private String[] getLoginInfo(){
        System.out.println("Login.");
        String SSN = Util.readLine("Social Security Number: ");
        String password = Util.readLine("Password: ");
        return new String[]{SSN, password};
    }

    public void loginCustomer(){
        String[] loginInfo = getLoginInfo();
        System.out.println(loginInfo[0]);
        System.out.println(loginInfo[1]);
    }

    public void loginEmployee(){
        String[] loginInfo = getLoginInfo();
    }
}
