package org.example.carrentalgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminLogin {

    @FXML
    private Button GoBackButton;

    @FXML
    private Button LoginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    void handleBackButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuPage.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root,720,480));
        stage.setResizable(false);
        stage.setTitle("Main Menu");
        stage.show();
        ((Stage)(((Node)event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    void handleLoginButton(ActionEvent event) throws IOException {
        if(usernameField.getText().equals("")||passwordField.getText().equals("")){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fields are empty. Try Again");
            alert.showAndWait();
        }
        else{
            String username=usernameField.getText();
            String password=passwordField.getText();
            if(Code.AdminLogin(username,password)){
                Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Login Successful");
                alert.showAndWait();
                loadAdminPanel(event);
            }
            else{
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid Username or Password");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void handlePasswordField(ActionEvent event) throws IOException {
        if(usernameField.getText().equals("")||passwordField.getText().equals("")){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fields are empty. Try Again");
            alert.showAndWait();
        }
        else{
            handleLoginButton(event);
        }
    }

    @FXML
    void handleUsernameField(ActionEvent event) throws IOException {
        if(usernameField.getText().equals("")||passwordField.getText().equals("")){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fields are empty. Try Again");
            alert.showAndWait();
        }
        else{
            handleLoginButton(event);
        }
    }
    @FXML
    void loadAdminPanel(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPanel.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root,720,480));
        stage.setResizable(false);
        stage.setTitle("Admin Panel");
        stage.show();
        ((Stage)(((Node)event.getSource()).getScene().getWindow())).close();
    }
}

