package com.salmon.spicysalmon.models;

public class Manager extends Employee {

    public Manager(String socialSecurityNumber, String password, String firstName, String lastName, String startDate) {
        super(socialSecurityNumber, password, firstName, lastName, startDate);
    }

    /*public String createEmployee(String pwd, String firstName, String lastName, String socialSecurityNumber, String title, String startDate)throws Exception{
        try{
            String uniqueID;
            do{
                int temp = (int) (Math.random()*999999);
                uniqueID = "3"+ temp;
            } while(transactionsCollection.get(uniqueID) != null);
            String ID = uniqueID;
            User newEmployee = new Employee(ID, pwd, firstName, lastName, socialSecurityNumber, title, startDate);
            usersCollection.put(uniqueID, newEmployee);
        } catch(Exception e){
            throw new Exception("Please enter valid customer information");
        }
        return "Employee created successfully";
    }*/
}

