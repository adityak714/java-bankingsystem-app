package com.salmon.spicysalmon.models;

public class BankAccountApplication {
    private final Customer requestee;
    private String accountName;
    private boolean isApproved;

    public BankAccountApplication(Customer customer,String accountName){
        this.requestee = customer;
        this.accountName = accountName;
    }

    public boolean approvalStatus(){
        return this.isApproved;
    }

    public Customer getRequestee(){
        return this.requestee;
    }

}
