package com.salmon.spicysalmon.menus;

import com.salmon.spicysalmon.controllers.AuthenticationController;
import com.salmon.spicysalmon.controllers.CustomerController;
import com.salmon.spicysalmon.models.Menu;

public class MainMenu {

    private final String MAIN_HEADING = "Main Menu: Please choose an option below.";
    private final String[] MAIN_OPTIONS = {
            "Close System",
            "Login",
            "Sign up for account",
            "Authorized Personnel"
    };

    public void show(){
        int userInput = 0;
        Menu mainMenu = new Menu(MAIN_HEADING, MAIN_OPTIONS);
        AuthenticationController authenticationController = new AuthenticationController();
        do{
            System.out.print(mainMenu);
            userInput = mainMenu.getValidOption();
            switch (userInput) {
                case 1 -> authenticationController.customerLogin();
                case 2 -> System.out.println("Application handling");
                case 3 -> authenticationController.employeeLogin();
                case 0, default -> {}
            }
        } while(userInput != 0);
    }

}
