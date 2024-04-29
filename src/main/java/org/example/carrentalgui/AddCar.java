package org.example.carrentalgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;

public class AddCar {
    public void initialize(){
        makeText.setVisible(false);
        rentText.setVisible(false);
        yearText.setVisible(false);
        modelText.setVisible(false);
    }

    @FXML
    private Button AddCarButton;

    @FXML
    private Button GoBackButton;

    @FXML
    private TextField MakeField;

    @FXML
    private TextField ModelField;

    @FXML
    private TextField RentField;

    @FXML
    private TextField YearField;
    @FXML
    private Label makeText;

    @FXML
    private Label modelText;

    @FXML
    private Label rentText;

    @FXML
    private Label yearText;

    @FXML
    void handleAddButton(ActionEvent event) throws IOException {
        if(((MakeField.getText()).equals("")||(ModelField.getText()).equals(""))||((RentField.getText()).equals("")||(YearField.getText()).equals(""))){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fields are empty. Try Again");
            alert.showAndWait();
        }
        else if(MakeField.getText().contains(" ")){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Make Field has space. Try Again");
            alert.showAndWait();
        }
        else if(ModelField.getText().contains(" ")){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Model Field has space. Try Again");
            alert.showAndWait();
        }
        else if(YearField.getText().contains(" ")){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Year Field has space. Try Again");
            alert.showAndWait();
        }
        else if(RentField.getText().contains(" ")){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Rent Field has space. Try Again");
            alert.showAndWait();
        }
        else if(!(YearField.getText()).matches("\\d+")){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Year Field has characters. Try Again");
            alert.showAndWait();
        }
        else if (!(RentField.getText()).matches("\\d+")) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Rent Field has characters. Try Again");
            alert.showAndWait();
        }
        else{
            Code.AddCar(MakeField.getText(),ModelField.getText(),YearField.getText(),RentField.getText());
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Car has been added successfully!");
            alert.showAndWait();
            Stage stage = (Stage) AddCarButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddCar.fxml"));
            Parent root = loader.load();
            stage.getScene().setRoot(root);
        }
    }

    @FXML
    void handleBackButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPanel.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root,720,480));
        stage.setResizable(false);
        stage.setTitle("Admin Panel");
        stage.show();
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
    @FXML
    void handleYearRelease(KeyEvent event) {
        yearText.setVisible(false);
        makeText.setVisible(false);
        modelText.setVisible(false);
        rentText.setVisible(false);
        String buffer=YearField.getText();
        if(buffer.equalsIgnoreCase("")){
            yearText.setVisible(false);
            makeText.setVisible(false);
            modelText.setVisible(false);
            rentText.setVisible(false);
            return;
        }
        if(buffer.contains(" ")){
            yearText.setTextFill(Paint.valueOf("red"));
            yearText.setText("Spaces are not allowed!");
            yearText.setVisible(true);
        }
        else if(!(YearField.getText()).matches("\\d+")) {
            yearText.setTextFill(Paint.valueOf("red"));
            yearText.setText("Characters are not allowed!");
            yearText.setVisible(true);
        }
    }
    @FXML
    void handleRentRelease(KeyEvent event) {
        yearText.setVisible(false);
        makeText.setVisible(false);
        modelText.setVisible(false);
        rentText.setVisible(false);
        String buffer=RentField.getText();
        if(buffer.equalsIgnoreCase("")){
            yearText.setVisible(false);
            makeText.setVisible(false);
            modelText.setVisible(false);
            rentText.setVisible(false);
            return;
        }
        if(buffer.contains(" ")){
            rentText.setTextFill(Paint.valueOf("red"));
            rentText.setText("Spaces are not allowed!");
            rentText.setVisible(true);
        }
        else if(!(RentField.getText()).matches("\\d+")) {
            rentText.setTextFill(Paint.valueOf("red"));
            rentText.setText("Characters are not allowed!");
            rentText.setVisible(true);
        }
    }
    @FXML
    void handleModelRelease(KeyEvent event) {
        yearText.setVisible(false);
        makeText.setVisible(false);
        modelText.setVisible(false);
        rentText.setVisible(false);
        String buffer=ModelField.getText();
        if(buffer.equalsIgnoreCase("")){
            yearText.setVisible(false);
            makeText.setVisible(false);
            modelText.setVisible(false);
            rentText.setVisible(false);
            return;
        }
        if(buffer.contains(" ")){
            modelText.setTextFill(Paint.valueOf("red"));
            modelText.setText("Spaces are not allowed!");
            modelText.setVisible(true);
        }
    }
    @FXML
    void handleMakeRelease(KeyEvent event) {
        yearText.setVisible(false);
        makeText.setVisible(false);
        modelText.setVisible(false);
        rentText.setVisible(false);
        String buffer=MakeField.getText();
        if(buffer.equalsIgnoreCase("")){
            yearText.setVisible(false);
            makeText.setVisible(false);
            modelText.setVisible(false);
            rentText.setVisible(false);
            return;
        }
        if(buffer.contains(" ")){
            makeText.setTextFill(Paint.valueOf("red"));
            makeText.setText("Spaces are not allowed!");
            makeText.setVisible(true);
        }
    }

    @FXML
    void handleMakeField(ActionEvent event) throws IOException {
        handleAddButton(event);
    }

    @FXML
    void handleModelField(ActionEvent event) throws IOException {
        handleAddButton(event);
    }

    @FXML
    void handleRentField(ActionEvent event) throws IOException {
        handleAddButton(event);
    }

    @FXML
    void handleYearField(ActionEvent event) throws IOException {
        handleAddButton(event);
    }

}
