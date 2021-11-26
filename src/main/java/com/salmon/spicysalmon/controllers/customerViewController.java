package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.models.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.text.TableView;
import java.io.IOException;

public class customerViewController {

    private Customer loggedInCustomer;

    @FXML
    private Text heading;

    public void initialData(Customer customer){
        loggedInCustomer = customer;
        heading.setText("Welcome "+loggedInCustomer.getFirstName()+" "+loggedInCustomer.getLastName()+"!");
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
