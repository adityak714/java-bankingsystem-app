package com.salmon.spicysalmon;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public static String getDateAndTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime currentDateAndTime = LocalDateTime.now();
        return formatter.format(currentDateAndTime);
    }
}
