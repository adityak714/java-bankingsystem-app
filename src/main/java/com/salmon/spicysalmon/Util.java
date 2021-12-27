package com.salmon.spicysalmon;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Util {
    public static String EOL = System.lineSeparator();
    private static final Scanner input = new Scanner(System.in);

    public static int readInt(String message){
        int input = -1;
        do{
            try{
                String potentialInteger = readLine(message);
                input = Integer.parseInt(potentialInteger);
            } catch(Exception e){
                System.out.println("Please enter a valid integer.");
            }
        }while(input == -1);
        return input;
    }
    public static String readLine(String message){
        System.out.print(message);
        return input.nextLine();
    }

    public static String getDateAndTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }

    public static boolean checkSSNFormat(String SSN){
        return SSN.matches("^\\d{10}$");
    }
}
