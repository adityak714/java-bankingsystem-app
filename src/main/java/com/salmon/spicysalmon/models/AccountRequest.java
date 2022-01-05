package com.salmon.spicysalmon.models;

import com.salmon.spicysalmon.Util;
import com.salmon.spicysalmon.controllers.AccountRequestController;

import java.util.UUID;

public abstract class AccountRequest implements Comparable<AccountRequest>{

    private final String CREATIONDATE;
    private String RESOLVEDDATE;
    private final String ID;
    private int isApproved;
    private String message;

    public AccountRequest() {
        this.CREATIONDATE = Util.getDateAndTime();
        this.RESOLVEDDATE = null;
        this.isApproved = 0;
        this.ID = UUID.randomUUID().toString().replace("-", "");
    }
    public void approveRequest(String approveMessage){
        this.isApproved = 1;
        this.message = approveMessage;
        this.RESOLVEDDATE = Util.getDateAndTime();
    }
    public void denyRequest(String denyMessage){
        this.isApproved = -1;
        this.message = denyMessage;
        this.RESOLVEDDATE = Util.getDateAndTime();
    }

    public String getCREATIONDATE() {
        return CREATIONDATE;
    }
    public String getRESOLVEDDATE() {
        if (RESOLVEDDATE == null){return "Request is pending";}
        else return RESOLVEDDATE;
    }
    public int getIsApproved(){
        return this.isApproved;
    }
    public String getStatusToString(){
        if (this.isApproved == 0){return "Pending";}
        if (this.isApproved == 1){return "Approved";}
        else return "Denied";
    }
    public String getMessage() {
        return message;
    }
    public String getID() {
        return ID;
    }

    @Override
    public int compareTo(AccountRequest otherRequest) {
        if (this.getIsApproved() == 0)
            return -1;
        if (this.getIsApproved() > otherRequest.getIsApproved())
            return 1;
        else
            return 0;
    }
}