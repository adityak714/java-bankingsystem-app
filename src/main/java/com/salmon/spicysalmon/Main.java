package com.salmon.spicysalmon;
import com.salmon.spicysalmon.controllers.CustomerController;
import com.salmon.spicysalmon.menus.MainMenu;
import com.salmon.spicysalmon.models.BankAccount;
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
            JSONArray accounts = (JSONArray) customer.get("accounts");
            for(int j=0; j<accounts.size(); j++){
                JSONObject account = (JSONObject) accounts.get(j);
                double balance = (double) ((long)account.get("balance"));
                String accountName = (String) account.get("accountName");
                // get account ID formatting correct, in accordance with Customer.java createBankAccount method
                String accID = j < 10 ? "0"+(j+1) : (j+1)+"";
                cc.createBankAccount(SSN, accountName);
                cc.depositMoney(SSN, j+"", balance);
                BankAccount newAccount = cc.findBankAccount(SSN, accID);
            }
            Customer newCustomer = cc.getCustomer(SSN);
        }
    }
}
