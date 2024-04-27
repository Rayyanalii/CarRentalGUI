package org.example.carrentalgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddCar {

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
    void handleAddButton(ActionEvent event) throws IOException {
        if(((MakeField.getText()).equals("")||(ModelField.getText()).equals(""))||((RentField.getText()).equals("")||(YearField.getText()).equals(""))){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fields are empty. Try Again");
            alert.showAndWait();
        }
        else if (!(RentField.getText()).matches("\\d+")) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Rent Field has characters. Try Again");
            alert.showAndWait();
        }
        else if(!(YearField.getText()).matches("\\d+")){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Year Field has characters. Try Again");
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
        stage.setTitle("Menu Page");
        stage.show();
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    void handleMakeField(ActionEvent event) {

    }

    @FXML
    void handleModelField(ActionEvent event) {

    }

    @FXML
    void handleRentField(ActionEvent event) {

    }

    @FXML
    void handleYearField(ActionEvent event) {

    }

}
