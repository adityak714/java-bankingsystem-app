package com.salmon.spicysalmon;
import com.salmon.spicysalmon.controllers.JSONController;
import com.salmon.spicysalmon.menus.MainMenu;

public class Main {
    public static void main(String[] args) {
        try {
            JSONController jsonController = new JSONController();
            jsonController.readDate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MainMenu mainMenu = new MainMenu();
        mainMenu.show();
    }
}