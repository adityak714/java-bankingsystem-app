package com.salmon.spicysalmon.models;

public abstract class Application {

    private final String CREATIONDATE;
    private String RESOLVEDDATE;
    private Boolean isApproved;

    public Application(){
        this.CREATIONDATE = null;
        this.RESOLVEDDATE = null;
        this.isApproved = null;
    }
    public void approveApplication() {
        this.isApproved = true;
    }
    public void denyApplication(){
        this.isApproved = false;
    }
    public Boolean getIsApproved(){
     return this.isApproved;
    }
}