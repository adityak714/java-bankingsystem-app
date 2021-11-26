package com.salmon.spicysalmon;

import com.salmon.spicysalmon.controllers.CustomerController;
import com.salmon.spicysalmon.models.Customer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/loginView.fxml"));
            Scene loginScene = new Scene(loader.load());
            stage.setTitle("Login Salmon");
            stage.setScene(loginScene);
            stage.show();
            CustomerController cc = new CustomerController();
            cc.createCustomer("123", "salmon", "Shariq", "Shahbaz", 988888888, "gatan", "chutiya");
            cc.createCustomer("321", "salmon", "Armin", "Balesic", 988888888, "gatan", "chutiya");
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}
