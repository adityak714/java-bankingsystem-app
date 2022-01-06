package com.salmon.spicysalmon;
import com.salmon.spicysalmon.controllers.JSONController;
import com.salmon.spicysalmon.views.MainMenu;

public class Main {
    public static void main(String[] args) {
        try {
            JSONController jsonController = new JSONController();
            jsonController.readData();
        } catch (Exception e) {
            System.out.println("Could not read data from files.");
            System.out.println(e.getMessage());
        }
        MainMenu mainMenu = new MainMenu();
        mainMenu.show();
    }
}