package com.salmon.spicysalmon.models;

public class Card {
    private final String CARDTYPE;
    private final int CARDNUMBER;
    private final String CARDOWNER;
    private int pincode;
    private boolean cardActive;
    private final int STARTDATE;
    private final int ENDINGDATE;

    public Card(String CARDTYPE, int CARDNUMBER, String CARDOWNER, int pincode, boolean cardActive, int STARTDATE, int ENDINGDATE) {
        this.CARDTYPE = CARDTYPE;
        this.CARDNUMBER = CARDNUMBER;
        this.CARDOWNER = CARDOWNER;
        this.pincode = pincode;
        this.cardActive = cardActive;
        this.STARTDATE = STARTDATE;
        this.ENDINGDATE = ENDINGDATE;
    }

    public String getCARDTYPE() {
        return CARDTYPE;
    }

    public int getCARDNUMBER() {
        return CARDNUMBER;
    }

    public String getCARDOWNER() {
        return CARDOWNER;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public boolean isCardActive() {
        return cardActive;
    }

    public void setCardActive(boolean cardActive) {
        this.cardActive = cardActive;
    }

    public int getSTARTDATE() {
        return STARTDATE;
    }

    public int getENDINGDATE() {
        return ENDINGDATE;
    }
}
