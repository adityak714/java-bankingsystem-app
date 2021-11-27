package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.models.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class loginViewController implements Initializable {

    @FXML
    private Text heading;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    private CustomerController customerController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerController = new CustomerController();
    }

    public void login(ActionEvent event) throws IOException {
        String SSN = username.getText();
        String key = password.getText();
        Customer customer = customerController.getCustomer(SSN);
        if(customer != null){
            if(customer.verifyPassword(key)){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/customerView.fxml"));
                Scene customerView = new Scene(loader.load());

                CustomerViewController cvc = loader.getController();
                cvc.initialData(customer);

                Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                mainStage.setScene(customerView);
                mainStage.setTitle("Logged in as: "+customer.getFirstName()+" "+customer.getLastName()+ "("+customer.getSocialSecurityNumber()+")");
                mainStage.show();
            } else{
                heading.setText("Incorrect Password");
            }
        } else{
            heading.setText("No user with that username exists!");
        }
    }
}
