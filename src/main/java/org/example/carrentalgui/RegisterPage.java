package org.example.carrentalgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class RegisterPage {
    @FXML
    private void initialize(){
        req2.setTextFill(Paint.valueOf("red"));
        req1.setTextFill(Paint.valueOf("red"));
        req.setTextFill(Paint.valueOf("red"));
        req.setVisible(false);
        req1.setVisible(false);
        req2.setVisible(false);
    }

    @FXML
    private Button GoBackButton;

    @FXML
    private Button LoginButton;

    @FXML
    private Button RegisterButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField passwordField1;

    @FXML
    private Label req;

    @FXML
    private Label req1;

    @FXML
    private Label req2;

    @FXML
    private TextField usernameField;

    @FXML
    void handleBackButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserMenu.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root,720,480));
        stage.setResizable(false);
        stage.setTitle("Login Page");
        stage.show();
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
    @FXML
    void handleUsernameFieldKeyReleased(KeyEvent event) {
        String username=usernameField.getText();
        int flag=0;
        try {
            File f=new File(Code.users);
            if(!f.exists()||username.equalsIgnoreCase("")){
                req2.setTextFill(Paint.valueOf("red"));
            }
            else {
                BufferedReader br=new BufferedReader(new FileReader(f));
                String buffer;
                while((buffer=br.readLine())!=null){
                    String data[]=buffer.split(" ");
                    if(username.equals(data[0])){
                        flag=1;
                        req2.setTextFill(Paint.valueOf("red"));
                    }
                }
                if(flag==0){
                    req2.setTextFill(Paint.valueOf("green"));
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void handlePasswordFieldKeyReleased(KeyEvent event) {
        String password=passwordField.getText();

    }

    @FXML
    void handleLoginButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserLoginPage.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root,720,480));
        stage.setResizable(false);
        stage.setTitle("Login Page");
        stage.show();
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    void handlePasswordField(ActionEvent event) throws IOException {
        if(usernameField.getText().equals("")||passwordField.getText().equals("")||passwordField1.getText().equals("")){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fields are empty. Try Again");
            alert.showAndWait();
        }
        else{
            handleRegisterButton(event);
        }
    }

    @FXML
    void handlePasswordField1(ActionEvent event) throws IOException {
        if(usernameField.getText().equals("")||passwordField.getText().equals("")||passwordField1.getText().equals("")){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fields are empty. Try Again");
            alert.showAndWait();
        }
        else{
            handleRegisterButton(event);
        }
    }

    @FXML
    void handlePasswordField1Click(MouseEvent event) {
        req.setVisible(false);
        req1.setVisible(true);
        req2.setVisible(false);
    }

    @FXML
    void handlePasswordFieldClick(MouseEvent event) {
        req.setVisible(true);
        req1.setVisible(false);
        req2.setVisible(false);
    }

    @FXML
    void handleRegisterButton(ActionEvent event) throws IOException {
        if (usernameField.getText().equals("") || passwordField.getText().equals("") || passwordField1.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fields are empty. Try Again");
            alert.showAndWait();
        } else {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String password1 = passwordField1.getText();
            if (Code.UserRegistration(username, password, password1).equals("exist")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username already exists!");
                alert.showAndWait();
            } else if (Code.UserRegistration(username, password, password1).equals("incorrect")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Passwords do not match!");
                alert.showAndWait();
            } else if (Code.UserRegistration(username, password, password1).equals("both")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Password does not contain 3 numbers and a special character!");
                alert.showAndWait();
            } else if (Code.UserRegistration(username, password, password1).equals("num")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Password does not contain 3 numbers");
                alert.showAndWait();
            } else if (Code.UserRegistration(username, password, password1).equals("char")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Passwords do not contain special character!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Registration Successful!");
                alert.showAndWait();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("UserMenu.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root, 720, 480));
                stage.setResizable(false);
                stage.setTitle("Login Page");
                stage.show();
                ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
            }
        }
    }

    @FXML
    void handleUsernameField(ActionEvent event) throws IOException {
        if(usernameField.getText().equals("")||passwordField.getText().equals("")||passwordField1.getText().equals("")){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fields are empty. Try Again");
            alert.showAndWait();
        }
        else{
            handleRegisterButton(event);
        }
    }

    @FXML
    void handleUsernameFieldClick(MouseEvent event) {
        req.setVisible(false);
        req1.setVisible(false);
        req2.setVisible(true);
    }

}
