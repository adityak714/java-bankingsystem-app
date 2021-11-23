package models;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Customer extends User {
    private final LinkedHashMap<String, BankAccount> accounts;
    private double salary;
    private String residentialArea;
    private String occupation;

    public Customer(String userID, String password, String firstName, String lastName, String socialSecurityNumber, double salary, String residentialArea, String occupation) {
        super(userID, password, firstName, lastName, socialSecurityNumber);
        this.accounts = new LinkedHashMap<>();
        this.salary = salary;
        this.residentialArea = residentialArea;
        this.occupation = occupation;
    }

    // super functions, adding social security numbers and attributes inherited from super class User

    public LinkedHashMap<String, BankAccount> getAccounts() {
        return accounts;
    }

    public double getSalary() {
        return salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getResidentialArea() {
        return residentialArea;
    }
    public void setResidentialArea(String residentialArea) {
        this.residentialArea = residentialArea;
    }

    public String getOccupation() {
        return occupation;
    }
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public double getTotalBalance(String accName){
        return accounts.get(accName).getBalance();
    }
}