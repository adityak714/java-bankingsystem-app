package com.salmon.spicysalmon.models;

import com.salmon.spicysalmon.Util;
import com.salmon.spicysalmon.controllers.AccountRequestController;

import java.util.UUID;

public abstract class AccountRequest {

    private final String CREATIONDATE;
    private String RESOLVEDDATE;
    private final String ID;
    private Boolean isApproved;
    private String message;

    public AccountRequest() {
        this.CREATIONDATE = Util.getDateAndTime();
        this.RESOLVEDDATE = null;
        this.isApproved = null;
        this.ID = UUID.randomUUID().toString().replace("-", "");
    }
    public void approveRequest(String approveMessage){
        this.isApproved = true;
        this.message = approveMessage;
        this.RESOLVEDDATE = Util.getDateAndTime();
    }
    public void denyRequest(String denyMessage){
        this.isApproved = false;
        this.message = denyMessage;
        this.RESOLVEDDATE = Util.getDateAndTime();
    }

    public String getCREATIONDATE() {
        return CREATIONDATE;
    }
    public String getRESOLVEDDATE() {
        return RESOLVEDDATE;
    }
    public Boolean getIsApproved(){
        return this.isApproved;
    }
    public String getStatusToString(){
        if (this.isApproved == null){return "Status: Pending ";}
        if (this.isApproved){return "Status: Approved ";}
        else return "Status: Denied)";
    }
    public String getMessage() {
        return message;
    }
    public String getID() {
        return ID;
    }
}