package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.models.Customer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class loginController implements Initializable {

    @FXML
    public Text heading;
    @FXML
    public TextField username;
    @FXML
    public PasswordField password;

    private CustomerController customerController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerController = new CustomerController();
    }

    public void login(){
        String SSN = username.getText();
        String key = password.getText();
        Customer customer = customerController.getCustomer(SSN);
        if(customer != null){
            if(customer.verifyPassword(key)){
                heading.setText("Welcome "+customer.getFirstName()+"!");
            } else{
                heading.setText("Incorrect Password");
            }
        } else{
            heading.setText("No user with that username exists!");
        }
    }
}
