package org.example.carrentalgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class UserPanel {
    @FXML
    void initialize(){
        welcomeLabel.setText("Welcome: "+username);
    }
    private static String username;

    @FXML
    private Button LogoutButton;

    @FXML
    private Button RentButton;

    @FXML
    private Button ReserveButton;

    @FXML
    private Button ReturnButton;

    @FXML
    private Label welcomeLabel;

    static void setUsername(String name){
        username=name;
    }

    @FXML
    void handleLogoutButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuPage.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root,720,480));
        stage.setResizable(false);
        stage.setTitle("Login Page");
        stage.show();
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    void handleRentButton(ActionEvent event) throws IOException {
        RentACar.setUsername(username);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RentACar.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root,720,480));
        stage.setResizable(false);
        stage.setTitle("Login Page");
        stage.show();
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    void handleReserveButton(ActionEvent event) throws IOException {
        ReserveACar.setUsername(username);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReserveACar.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root,720,480));
        stage.setResizable(false);
        stage.setTitle("Login Page");
        stage.show();
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    void handleReturnButton(ActionEvent event) throws IOException {
        ReturnACar.setUsername(username);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReturnACar.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root,720,480));
        stage.setResizable(false);
        stage.setTitle("Login Page");
        stage.show();
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

}

