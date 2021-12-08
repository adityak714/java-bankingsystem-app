package com.salmon.spicysalmon.menus;

import com.salmon.spicysalmon.models.Menu;

public class EmployeeMenu {
    String EMPLOYEE_HEADING = "Employee Menu: Please choose a valid option.";
    String[] EMPLOYEE_OPTIONS = {
            "Log out",
            "Delete account",
            "Approve new customer accounts",
            "Approve new bank accounts",
    };

    public void show(){
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
