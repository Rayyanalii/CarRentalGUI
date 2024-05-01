package org.example.carrentalgui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DisplayUser {
    @FXML
    private TextField SearchField;
    @FXML
    private ComboBox<String> sortComboBox;
    @FXML
    private TableView<String[]> tableView;
    private FilteredList<String[]> filteredData;

    public void initialize() {
        TableColumn<String[], String> column1 = new TableColumn<>("Username");
        TableColumn<String[], String> column2 = new TableColumn<>("Password");

        column1.setCellValueFactory(data -> {
            String[] rowData = data.getValue();
            return rowData != null && rowData.length > 0 ? new SimpleStringProperty(rowData[0]) : new SimpleStringProperty("");
        });

        column2.setCellValueFactory(data -> {
            String[] rowData = data.getValue();
            return rowData != null && rowData.length > 1 ? new SimpleStringProperty(rowData[1]) : new SimpleStringProperty("");
        });

        tableView.getColumns().addAll(column1, column2);

        loadDataFromFile();


        filteredData = new FilteredList<>(tableView.getItems(), p -> true);

        SearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(data -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                for (String cellData : data) {
                    if (cellData.toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                }
                return false;
            });
        });

        tableView.setItems(filteredData);

        sortComboBox.setOnAction(event -> {
            String selectedSortOption = sortComboBox.getValue();
            sortComboBox.setPromptText(selectedSortOption);
            if(selectedSortOption.equalsIgnoreCase("Name in Ascending Order")){
                File f=new File(Code.users);
                Code.NameSortAscending(f);
            }
            else if(selectedSortOption.equalsIgnoreCase("Name in descending Order")){
                File f=new File(Code.users);
               Code.NameSortDescending(f);
            }
            Stage stage = (Stage) sortComboBox.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DisplayUser.fxml"));
            Parent root;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.getScene().setRoot(root);
        });
    }


    private void loadDataFromFile() {
        String filePath = Code.users;
        String line;
        try {
            File f = new File(filePath);
            if (!f.exists()) {
                tableView.setAccessibleText("No users available");
            } else {
                BufferedReader br = new BufferedReader(new FileReader(f));
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(" ");
                    tableView.getItems().add(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
        ((Stage)(((Node)event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    void handleSearchField(ActionEvent event) {

    }
}
