package org.example.carrentalgui;

import com.almasb.fxgl.ui.DialogBox;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class UserLoginPageController {

    @FXML
    private Button GoBackButton;

    @FXML
    private Button RegisterButton;
    @FXML
    private Button LoginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;
    Stage stage;
    static int Locked=0;

    @FXML
    void handleBackButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserMenu.fxml"));
        Parent root = loader.load();
        stage = new Stage();
        stage.setScene(new Scene(root,720,480));
        stage.setResizable(false);
        stage.setTitle("User Menu");
        stage.show();
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
    @FXML
    void handlePasswordField(ActionEvent event) throws Code.LockedOutException, IOException {
        if(passwordField.getText().equals("")&&usernameField.getText().equals("")){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fields are empty. Try Again");
            alert.showAndWait();
            Locked=0;
        }
        else{
            handleLoginButton(event);
        }
    }
    @FXML
    void handleUsernameField(ActionEvent event) throws Code.LockedOutException, IOException {
        if(passwordField.getText().equals("")||usernameField.getText().equals("")){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fields are empty. Try again");
            alert.showAndWait();
            Locked=0;
        }
        else{
            handleLoginButton(event);
        }
    }

    @FXML
    void handleLoginButton(ActionEvent event) throws Code.LockedOutException, IOException {
        passwordField.setTooltip(new Tooltip("Hello"));
        String username=usernameField.getText();
        String password=passwordField.getText();
        if(passwordField.getText().equals("")&&usernameField.getText().equals("")){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Username and Password Fields are empty");
            alert.showAndWait();
            Locked=0;
        }
        else if(passwordField.getText().equals("")){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Password field is empty");
            alert.showAndWait();
            Locked=0;
        }
        else if(usernameField.getText().equals("")){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Username Field is empty");
            alert.showAndWait();
            Locked=0;
        }
        else{
            if(Code.UserLogin(username,password).equals("NoUsers")){
                Alert alert=new Alert(Alert.AlertType.NONE);
                alert.setContentText("No Registered Users");
                alert.showAndWait();
            }
            else if(Code.UserLogin(username,password).equals("Invalid")){
                Locked++;
                if(Locked==3){
                    Alert alert=new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Locked Out");
                    alert.showAndWait();
                    System.exit(1);
                }
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid Username or Password");
                alert.showAndWait();
            }
            else{
                Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Login Successful");
                alert.showAndWait();
                UserPanel.setUsername(username);
                loadUserPanel(event);
            }
        }
    }
    @FXML
    void loadUserPanel(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserPanel.fxml"));
        Parent root = loader.load();
        stage = new Stage();
        stage.setScene(new Scene(root,720,480));
        stage.setResizable(false);
        stage.setTitle("User Panel");
        stage.show();
        ((Stage)(((Node)event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    void handleRegisterButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterPage.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root,720,480));
        stage.setResizable(false);
        stage.setTitle("User Register Panel");
        stage.show();
        ((Stage)(((Node)event.getSource()).getScene().getWindow())).close();
    }

}

