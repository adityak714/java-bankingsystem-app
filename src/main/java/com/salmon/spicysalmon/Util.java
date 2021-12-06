package com.salmon.spicysalmon;

import java.util.Scanner;

public class Util {
    public static final String EOL = System.lineSeparator();
    private static final Scanner input = new Scanner(System.in);
    public static int readInt(String message){
        System.out.print(message);
        int userInput = input.nextInt();
        input.nextLine();
        return userInput;
    }
}
