package com.salmon.spicysalmon.models;

import com.salmon.spicysalmon.Util;

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

    public String getCREATIONDATE() {
        return CREATIONDATE;
    }

    public String getRESOLVEDDATE() {
        return RESOLVEDDATE;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public String getMessage() {
        return message;
    }
    public String getApprovalStatus(){
        if (this.isApproved == null)
            return "Status: Pending" + Util.EOL
                    + getCREATIONDATE(); //If the request is pending we show creation date.
        if (this.isApproved)
            return "Status: Approved" + Util.EOL
                    + "Request was created: " + getCREATIONDATE() + Util.EOL //If the request was approved/denied we also show when it was resolved.
                    + "Request was approved: " + getRESOLVEDDATE();
        else
            return "Status: Denied" + Util.EOL
                    + "Request was created: " + getCREATIONDATE() + Util.EOL
                    + "Request was denied: " + getRESOLVEDDATE();
    }
    public Boolean getIsApproved(){
     return this.isApproved;
    }
}