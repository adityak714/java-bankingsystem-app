package com.salmon.spicysalmon.menus;

import com.salmon.spicysalmon.models.Menu;

public class CustomerMenu {
    String CUSTOMER_HEADING = "Customer Menu: Please choose a valid option.";
    String[] CUSTOMER_OPTIONS = {
            "Log out",
            "1. View Bank Accounts",
            "2. My Applications",
            "3. Apply for new Bank Account",
            "4. ",
            // Continue to add more options here and to the switch case as you see fit, it's ok to create submenus if anyone want to do that,
            // just make a switch case inside the switch case (or make a seperate method that is called inside the switch case)
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
