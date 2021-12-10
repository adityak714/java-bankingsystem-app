package com.salmon.spicysalmon.menus;

import com.salmon.spicysalmon.UserIO;
import com.salmon.spicysalmon.controllers.CustomerController;
import com.salmon.spicysalmon.models.Menu;

public class EmployeeMenu {
    String EMPLOYEE_HEADING = "Employee Menu: Please choose a valid option.";
    String[] EMPLOYEE_OPTIONS = {
            "Log out",
            "Menu for Customer handling",
            "Menu for Application handling",
    };
    String EMPLOYEE_HEADING2 = "Customer handling menu: Please choose a valid option.";
    String[] EMPLOYEE_OPTIONS2 = {
            "0. Log out",
            "1. create customer",
            "2. delete customer",
            "3. Print all registered customers.",
            // is these 2 methods agreed upon functions of the employee?
            "4. deposit money into bank account ", // this function is used when a customer meets with an employee in person and has cash that they want to deposit
            "5. withdraw money from bank account", // this function is used when a customer meets with an employee in person and has cash that they want to withdraw
            "6."
            // Continue to add more options here and to the switch case as you see fit, it's ok to create submenus if anyone want to do that,
            // just make a switch case inside the switch case (or make a seperate method that is called inside the switch case)
    };
    String EMPLOYEE_HEADING3 = "Application handling menu: Please choose a valid option.";
    String[] EMPLOYEE_OPTIONS3 = {
            "Log out",
            "approve customer application",
            "approve bank application",
    };

    public void show(String SSN){
        CustomerController customerController = new CustomerController();
        Menu employeeMenu = new Menu(EMPLOYEE_HEADING, EMPLOYEE_OPTIONS);
        Menu employeeCustomerMenu = new Menu(EMPLOYEE_HEADING2, EMPLOYEE_OPTIONS2);
        Menu employeeApplicationMenu = new Menu(EMPLOYEE_HEADING3,EMPLOYEE_OPTIONS3);
        int userInput = 0;
        do {
            System.out.println(employeeMenu);
            userInput = employeeMenu.getValidOption();
            switch (userInput){
                case 1:
                    do {
                        System.out.println("Customer Menu"); // ADD LOGIC HERE
                        System.out.println(employeeCustomerMenu);
                        switch (userInput){
                            case 1:
                                System.out.println("You have chosen: Create a customer.");
                                System.out.print("What is your SSN?: ");
                                String socialSecurityNumber = UserIO.readStr();
                                System.out.print("Create a new password: ");
                                String password = UserIO.readStr();
                                System.out.print("What is your first name?: ");
                                String firstName = UserIO.readStr();
                                System.out.print("What is your last name?: ");
                                String lastName = UserIO.readStr();
                                System.out.print("What is your salary?: ");
                                int salary = UserIO.readInt();
                                System.out.print("Where do you live?: ");
                                String residentalArea = UserIO.readStr();
                                System.out.print("What is your occupation?: ");
                                String occupation = UserIO.readStr();
                                customerController.createCustomer(socialSecurityNumber,password, firstName,lastName, salary, residentalArea, occupation);
                                // we should probably remove the exceptionhandling
                                break;
                            case 2:
                                System.out.println("You have chosen: Remove a customer.");
                                System.out.print("What customer do you wish to remove? Enter SSN: ");
                                String remove = UserIO.readStr();
                                System.out.println(customerController.removeCustomer(remove));
                                break;
                            case 3:
                                System.out.println("You have chosen: Print all registered customers.");
                                System.out.println(customerController.printAllCustomers());
                                break;
                            case 4:

                                break;
                            case 5:

                                break;
                        }
                        break;
                    }while (userInput != 0);
                case 2:
                    System.out.println("Application Menu"); // ADD LOGIC HERE
                    System.out.println(employeeApplicationMenu);
                    userInput = employeeMenu.getValidOption();
                    switch (userInput){
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4:
                            break;
                    }
                    break;
                default:
                    System.out.println("goodbye");
                    break;
            }
        }while (userInput != 0);
    }

}

