package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.models.BankAccount;
import com.salmon.spicysalmon.models.Customer;
import com.salmon.spicysalmon.models.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class BankAccountViewController {

    @FXML
    private Label mainHeading;
    @FXML
    private TableView<Transaction> transactionsContainer;
    @FXML
    private TableColumn<Transaction, Double> AMOUNT;
    @FXML
    private TableColumn<Transaction, String> ID;
    @FXML
    private TableColumn<Transaction, String> TO;


    private Customer loggedInCustomer;
    private BankAccount currentBankAccount;

    public void initData(Customer customer, BankAccount bankAccount){
        this.loggedInCustomer = customer;
        this.currentBankAccount = bankAccount;
        mainHeading.setText(loggedInCustomer.getSocialSecurityNumber()+"-"+currentBankAccount.getAccountNumber()+"\n"+currentBankAccount.getBalance()+" SEK");
        this.showTransactions();
    }

    private void showTransactions(){

        Transaction trans1 = new Transaction("Armin", "Shariq", 5000);
        Transaction trans2 = new Transaction("Victor", "Shariq", 10000);
        Transaction trans3 = new Transaction("Umar", "Shariq", 50000);
        Transaction trans4 = new Transaction("Aditya", "Shariq", 2);

        ArrayList<Transaction> allTransactions = new ArrayList<>();
        allTransactions.add(trans1);
        allTransactions.add(trans2);
        allTransactions.add(trans3);
        allTransactions.add(trans4);


        ID.setCellValueFactory(new PropertyValueFactory<Transaction, String>("ID"));
        TO.setCellValueFactory(new PropertyValueFactory<Transaction, String>("TO"));
        AMOUNT.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("AMOUNT"));
        transactionsContainer.setItems(FXCollections.observableList(allTransactions));
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
