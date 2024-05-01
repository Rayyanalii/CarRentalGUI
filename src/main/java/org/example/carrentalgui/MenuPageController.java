package org.example.carrentalgui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuPageController {

    @FXML
    private Button AdminPanelButton;

    @FXML
    private Button UserPanelButton;

    @FXML
    void handleAdminPanel(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminLogin.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root,720,480));
        stage.setResizable(false);
        stage.setTitle("Admin Login Menu");
        stage.show();
        ((Stage)(((Node)event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    void handleUserPanel(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserMenu.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root,720,480));
        stage.setResizable(false);
        stage.setTitle("User Menu");
        stage.show();
        ((Stage)(((Node)event.getSource()).getScene().getWindow())).close();
    }

}
