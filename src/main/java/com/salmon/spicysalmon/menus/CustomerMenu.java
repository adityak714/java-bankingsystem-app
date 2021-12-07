package com.salmon.spicysalmon.menus;

import com.salmon.spicysalmon.models.Menu;

public class CustomerMenu {
    String CUSTOMER_HEADING = "Customer Menu: Please choose a valid option.";
    String[] CUSTOMER_OPTIONS = {
            "Log out",
            "View Bank Accounts",
            "My Applications",
            "Apply for new Bank Account"
    };

    public void show(){
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

}
