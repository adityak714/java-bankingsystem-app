package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.models.BankAccount;
import com.salmon.spicysalmon.models.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class BankAccountViewController {

    @FXML
    private Label mainHeading;

    private Customer loggedInCustomer;
    private BankAccount currentBankAccount;

    public void initData(Customer customer, BankAccount bankAccount){
        this.loggedInCustomer = customer;
        this.currentBankAccount = bankAccount;
        mainHeading.setText(loggedInCustomer.getSocialSecurityNumber()+"-"+currentBankAccount.getAccountNumber()+"\n"+currentBankAccount.getBalance()+" SEK");
    }

    public void goBack(ActionEvent event){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/customerView.fxml"));
        try {
            Scene customerView = new Scene(loader.load());
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            CustomerViewController cvc = loader.getController();
            cvc.initialData(loggedInCustomer);
            mainStage.setTitle(loggedInCustomer.getFirstName());
            mainStage.setScene(customerView);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
