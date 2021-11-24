package models;

public class Employee extends User{

    private String title;
    private int vacationDays;
    private String startDate;

    public Employee(String ID, String password, String firstName, String lastName, String socialSecurityNumber, String title, String startDate){
        super(ID, password, firstName, lastName, socialSecurityNumber);
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
        return "";
    }
    public String reviewApplication(){
        return "";
    }
    public String certifyLoan(){
        return "";
    }
}
