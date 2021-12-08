package com.salmon.spicysalmon;
import com.salmon.spicysalmon.controllers.CustomerController;
import com.salmon.spicysalmon.menus.MainMenu;
import com.salmon.spicysalmon.models.Customer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        try{
            readCustomers();
        } catch(Exception e){
            e.printStackTrace();
        }
        MainMenu mainMenu = new MainMenu();
        mainMenu.show();
    }

    private static void readCustomers() throws Exception{
        CustomerController cc = new CustomerController();
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(new FileReader("src/main/java/com/salmon/spicysalmon/data/Customers.json"));
        JSONArray customers = (JSONArray) obj;
        for(int i=0; i<customers.size();i++){
            JSONObject customer = (JSONObject) customers.get(i);
            String SSN = (String) customer.get("SSN");
            String password = (String) customer.get("password");
            String firstName = (String) customer.get("firstName");
            String lastName = (String) customer.get("lastName");
            double salary  = (double)((long) customer.get("salary"));
            String residentialArea = (String) customer.get("residentialArea");
            String occupation = (String) customer.get("occupation");
            cc.createCustomer(SSN, password, firstName, lastName, salary, residentialArea, occupation);
            Customer newCustomer = cc.getCustomer(SSN);
            System.out.println(newCustomer);
        }
    }
}
