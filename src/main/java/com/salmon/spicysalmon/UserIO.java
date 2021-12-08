package com.salmon.spicysalmon;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;

public class UserIO {

    public static String decimalFormat(double price){
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.DOWN);
        return df.format(price);
    }

    public static double truncateFormat(double mean) {
        return (int)(mean*10)/10.0;
    }

    public static double truncateFormat2(double price){
        return (int)(price*100)/100.0;
    }


    public static final Scanner SCANNER = new Scanner(System.in);

    public static int readInt(){
        int input = SCANNER.nextInt();
        SCANNER.nextLine();
        return input;
    }

    public static String readStr(){
        String str = SCANNER.nextLine();
        return str;
    }

    public static double readDouble(){
        return SCANNER.nextDouble();
    }
}
