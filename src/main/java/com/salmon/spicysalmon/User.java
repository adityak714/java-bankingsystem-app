package com.salmon.spicysalmon;

public abstract class User {






    public boolean verifyPassword(String Password,int UserId){
        if(Password==UserId.getPassword){
            return true;
        }else {
            return false;
        }
    }
    public void changePassword(String OldPassword, String NewPassword, int UserId){
        if (OldPassword == NewPassword){
            System.out.println("Error : You wrote the same password (old password)");
        }
        UserId.setPassword = NewPassword;
    }
}

