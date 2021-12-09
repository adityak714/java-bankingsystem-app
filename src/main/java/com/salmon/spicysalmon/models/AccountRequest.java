package com.salmon.spicysalmon.models;

public abstract class AccountRequest {

    private final String CREATIONDATE;
    private String RESOLVEDDATE;
    private Boolean isApproved;
    private String message;

    public AccountRequest() {
        this.CREATIONDATE = null;
        this.RESOLVEDDATE = null;
        this.isApproved = null;
    }
    public void approveRequest(String approveMessage){
        this.isApproved = true;
        this.message = approveMessage;
    }
    public void denyRequest(String denyMessage){
        this.isApproved = false;
        this.message = denyMessage;
    }
    public Boolean getIsApproved(){
     return this.isApproved;
    }
}