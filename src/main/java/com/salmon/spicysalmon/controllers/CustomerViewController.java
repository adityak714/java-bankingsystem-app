package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.models.BankAccount;
import com.salmon.spicysalmon.models.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedHashMap;

public class CustomerViewController {

    private Customer loggedInCustomer;

    @FXML
    private Text heading;
    @FXML
    private VBox accountsContainer;

    public void initialData(Customer customer){
        loggedInCustomer = customer;
        heading.setText("Welcome "+loggedInCustomer.getFirstName()+" "+loggedInCustomer.getLastName()+"!");
        this.showCustomerAccounts();
    }

    public void showCustomerAccounts(){
        LinkedHashMap<String, BankAccount> accounts = loggedInCustomer.getAccounts();
        if(!accounts.isEmpty()){
            for(String accountID : accounts.keySet()){
                BankAccount account = accounts.get(accountID);
                String accountString = loggedInCustomer.getSocialSecurityNumber()+"-"+account.getAccountNumber()+", "+account.getBalance()+" SEK";
                Button accountBtn = new Button(accountString);
                accountBtn.setPrefWidth(205);
                accountBtn.setOnMouseClicked((mouseEvent -> {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/views/bankAccountView.fxml"));
                    try {
                        Scene accountView = new Scene(loader.load());
                        Stage mainStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                        BankAccountViewController bavc = loader.getController();
                        bavc.initData(loggedInCustomer, account);
                        mainStage.setTitle(accountString);
                        mainStage.setScene(accountView);
                        mainStage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }));

                accountsContainer.getChildren().add(accountBtn);
                Separator horizontalSep = new Separator();
                accountsContainer.getChildren().add(horizontalSep);
            }
        } else{
            accountsContainer.getChildren().add(new Text("You have no registered accounts"));
        }
    }

    public void logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/loginView.fxml"));
        Scene loginScene = new Scene(loader.load());
        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainStage.setScene(loginScene);
        mainStage.setTitle("Please login!");
        mainStage.show();
    }

}
