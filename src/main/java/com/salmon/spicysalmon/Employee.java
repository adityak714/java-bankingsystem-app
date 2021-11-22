package com.salmon.spicysalmon;

public class Employee extends Users{

    private String title;
    private int vacationDays;
    private String startDate;

    public Employee(int ID, String pwd, String name, String title, String startDate){
        super(ID, pwd, name);

        this.title = title;
        this.startDate = startDate;

    }
    public String getTitle(){
        return this.title;
    }
    public String getStartDate(){
        return this.startDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setVacationDays(int vacationDays) {
        this.vacationDays = vacationDays;
    }

    public String deleteAccount(int accountID){

    }
    public String reviewApplication(){

    }
    public String certifyLoan(){

    }
}
