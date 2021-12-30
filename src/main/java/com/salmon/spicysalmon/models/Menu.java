package com.salmon.spicysalmon.models;

import com.salmon.spicysalmon.Util;

public class Menu {
    private final String MENU_HEADING;
    private final String[] MENU_OPTIONS;

    public Menu(String heading, String[] options){
        this.MENU_HEADING = heading;
        this.MENU_OPTIONS = options;
    }

    public String toString(){
        String result = Util.EOL + MENU_HEADING + Util.EOL + Util.EOL;
        for (int i = 0; i < MENU_OPTIONS.length; i++) {
            result += i + ". " + MENU_OPTIONS[i] + Util.EOL;
        }
        return result;
    }

    public int getValidOption(){
        int userInput;
        do{
            System.out.println();
            userInput = Util.readInt("Type an option number: ");
            if(userInput < 0 || userInput >= MENU_OPTIONS.length){
                System.out.println("Invalid menu option. Please type another option.");
            }
        } while(userInput < 0 || userInput >= MENU_OPTIONS.length);

        return userInput;
    }
}
