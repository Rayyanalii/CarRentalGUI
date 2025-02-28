package org.example.carrentalgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
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
        req.setText("Must have atleast 8 characters \nMust have 3 digits and 1 special character");
        usernameField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode()==KeyCode.TAB) {
                req2.setVisible(false);
                req.setVisible(true);
                usernameField.getParent().getChildrenUnmodifiable().stream()
                        .filter(node -> node.isFocusTraversable() && node != usernameField)
                        .findFirst()
                        .ifPresent(node -> node.requestFocus());
            }
        });
        passwordField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode()==KeyCode.TAB) {
                req.setVisible(false);
                req1.setVisible(true);
                passwordField.getParent().getChildrenUnmodifiable().stream()
                        .filter(node -> node.isFocusTraversable() && node != passwordField)
                        .findFirst()
                        .ifPresent(node -> node.requestFocus());
            }
        });
        passwordField1.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode()==KeyCode.TAB) {
                req1.setVisible(false);
                passwordField1.getParent().getChildrenUnmodifiable().stream()
                        .filter(node -> node.isFocusTraversable() && node != passwordField1)
                        .findFirst()
                        .ifPresent(node -> node.requestFocus());
            }
        });
        GoBackButton.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode()==KeyCode.TAB) {
                req2.setVisible(true);
                GoBackButton.getParent().getChildrenUnmodifiable().stream()
                        .filter(node -> node.isFocusTraversable() && node != GoBackButton)
                        .findFirst()
                        .ifPresent(node -> node.requestFocus());
            }
        });
        req2.setTextFill(Paint.valueOf("red"));
        req1.setTextFill(Paint.valueOf("red"));
        req.setTextFill(Paint.valueOf("red"));
        req.setVisible(false);
        req1.setVisible(false);
        req2.setVisible(true);
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
    boolean special;

    @FXML
    void handleBackButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserMenu.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root,720,480));
        stage.setResizable(false);
        stage.setTitle("User Menu");
        stage.show();
        ((Stage)(((Node)event.getSource()).getScene().getWindow())).close();
    }
    @FXML
    void handleUsernameFieldKeyReleased(KeyEvent event) {
        special=false;
        String username=usernameField.getText();
        for (int i = 0; i < username.length(); i++) {
            if(Character.isLetterOrDigit(username.charAt(i))){
            }
            else {
                special=true;
            }
        }
        if(usernameField.getText().contains(" ")){
            req2.setText("Space is not allowed!");
            req2.setTextFill(Paint.valueOf("red"));
            req2.setVisible(true);
            return;
        }
        else if(special) {
            req2.setText("Must not contain special characters!");
            req2.setTextFill(Paint.valueOf("red"));
            req2.setVisible(true);
            return;
        }
        else{
            req2.setText("Username must be unique");
            req2.setTextFill(Paint.valueOf("red"));
            req2.setVisible(true);
        }
        int flag=0;
        try {
            File f=new File(Code.users);
            if(!f.exists()){
                req2.setTextFill(Paint.valueOf("green"));
            }
            if(username.equalsIgnoreCase("")){
                req2.setTextFill(Paint.valueOf("red"));
            }
            else {
                f.createNewFile();
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
        req.setLayoutY(249.0);
        req.setText("Must have atleast 8 characters \nMust have 3 digits and 1 special character");
        if(passwordField.getText().contains(" ")){
            req.setLayoutY(258.0);
            req.setText("Space is not allowed!");
            req.setTextFill(Paint.valueOf("red"));
            req.setVisible(true);
            return;
        }
        String password=passwordField.getText();
        int num=0;
        int special=0;
        if(password.length()>3){
            for (int i = 0; i < password.length(); i++) {
                char c= password.charAt(i);
                if(Character.isDigit(c)){
                    num++;
                }
                else if(!Character.isDigit(c) && !Character.isLetter(c) && !Character.isWhitespace(c)){
                    special++;
                }
            }
            if(num>=3&&special>=1&&password.length()>=8){
                req.setTextFill(Paint.valueOf("green"));
            }
            else {
                req.setTextFill(Paint.valueOf("red"));
            }
        }
        else {
            req.setTextFill(Paint.valueOf("red"));
        }
    }
    @FXML
    void handleReenterReleased(KeyEvent event) {
        String password1=passwordField.getText();
        String password2=passwordField1.getText();
        if(passwordField1.getText().contains(" ")){
            req1.setText("Space is not allowed!");
            req1.setTextFill(Paint.valueOf("red"));
            req1.setVisible(true);
            return;
        }
        else {
            req1.setText("Password must match!");
            req1.setTextFill(Paint.valueOf("red"));
            req1.setVisible(true);
        }
        if(password2.equalsIgnoreCase("")){
            req1.setTextFill(Paint.valueOf("red"));
            return;
        }
        if(password1.equals(password2)){
            req1.setTextFill(Paint.valueOf("green"));
        }
        else {
            req1.setTextFill(Paint.valueOf("red"));
        }
    }

    @FXML
    void handleLoginButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserLoginPage.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root,720,480));
        stage.setResizable(false);
        stage.setTitle("User Login Panel");
        stage.show();
        ((Stage)(((Node)event.getSource()).getScene().getWindow())).close();
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
        String password1=passwordField.getText();
        String password2=passwordField1.getText();
        if(password2.equalsIgnoreCase("")){
            req1.setTextFill(Paint.valueOf("red"));
            return;
        }
        if(password1.equals(password2)){
            req1.setTextFill(Paint.valueOf("green"));
        }
        else {
            req1.setTextFill(Paint.valueOf("red"));
        }
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
            if(usernameField.getText().contains(" ")){
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username Field has space. Try Again");
                alert.showAndWait();
            }
            else if(passwordField.getText().contains(" ")){
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Password Field has space. Try Again");
                alert.showAndWait();
            }
            else if(passwordField1.getText().contains(" ")){
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Re-Enter Password Field has space. Try Again");
                alert.showAndWait();
            }
            else if (special){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username contains Special Characters");
                alert.showAndWait();
            }
            else if (Code.UserRegistration(username, password, password1).equals("exist")) {
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
                stage.setTitle("User Menu");
                stage.show();
                ((Stage) (((Node) event.getSource()).getScene().getWindow())).close();
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
