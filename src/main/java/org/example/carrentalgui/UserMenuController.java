package org.example.carrentalgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class UserMenuController {

    @FXML
    private Button BackButton;

    @FXML
    private Button LoginButton;

    @FXML
    private Button RegisterButton;

    @FXML
    void handleBackButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuPage.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root,720,480));
        stage.setResizable(false);
        stage.setTitle("Main Menu");
        stage.show();
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    void handleLoginButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserLoginPage.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root,720,480));
        stage.setResizable(false);
        stage.setTitle("User Login");
        stage.show();
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    void handleRegisterButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterPage.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root,720,480));
        stage.setResizable(false);
        stage.setTitle("User Register");
        stage.show();
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

}

