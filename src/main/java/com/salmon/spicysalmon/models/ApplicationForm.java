package com.salmon.spicysalmon.models;

public class ApplicationForm { // Armin: ApplicationForm a better name
    private final Customer requestee;
    private String accountName;
    private boolean isApproved;

    public ApplicationForm(Customer customer, String accountName){
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
