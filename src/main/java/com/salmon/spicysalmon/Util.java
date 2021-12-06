package com.salmon.spicysalmon;

import java.io.Console;
import java.util.Scanner;

public class Util {
    public static String EOL = System.lineSeparator();
    private static final Scanner input = new Scanner(System.in);

    public static int readInt(String message){
        System.out.print(message);
        int userInt = input.nextInt();
        input.nextLine();
        return userInt;
    }

    public static String readLine(String message){
        System.out.print(message);
        return input.nextLine();
    }

    public static String readPassword(String message){
        System.out.print(message);
        Console cnsl = System.console();
        char[] key = cnsl.readPassword();
        String password = "";
        for(char character : key){
            password += character;
        }
        return password;
    }

}
