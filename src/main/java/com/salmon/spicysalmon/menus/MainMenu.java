package com.salmon.spicysalmon.menus;

import com.salmon.spicysalmon.models.Menu;

public class MainMenu {

    private final String MAIN_HEADING = "Main Menu: Please choose an option below.";
    private final String[] MAIN_OPTIONS = {
            "Close System",
            "Login",
            "Apply for account",
            "Authorized Personnel"
    };

    public void show(){
        int userInput = 0;
        Menu mainMenu = new Menu(MAIN_HEADING, MAIN_OPTIONS);
        do{
            System.out.print(mainMenu);
            userInput = mainMenu.getValidOption();
            switch(userInput){
                case 1:
                   // AuthenticationController.customerLogin();
                    break;
                case 2:
                    System.out.println("Application handling");
                    break;
                case 3:
                   // AuthenticationController.employeeLogin();
                    break;
                case 0:
                default:
                    break;
            }
        } while(userInput != 0);
    }

}
