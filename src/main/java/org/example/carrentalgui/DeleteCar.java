
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

public class DeleteCar {
    String[] rowData;
    @FXML
    private TextField SearchField;
    @FXML
    private ComboBox<String> sortComboBox;
    @FXML
    private Button DeleteCar;
    @FXML
    private TableView<String[]> tableView;
    private FilteredList<String[]> filteredData;

    public void initialize() {
        DeleteCar.setDisable(true);
        TableColumn<String[], String> column1 = new TableColumn<>("Make");
        TableColumn<String[], String> column2 = new TableColumn<>("Model");
        TableColumn<String[], String> column3 = new TableColumn<>("Year");
        TableColumn<String[], String> column4 = new TableColumn<>("Rent Per Day");

        column1.setCellValueFactory(data -> {
            String[] rowData = data.getValue();
            return rowData != null && rowData.length > 0 ? new SimpleStringProperty(rowData[0]) : new SimpleStringProperty("");
        });

        column2.setCellValueFactory(data -> {
            String[] rowData = data.getValue();
            return rowData != null && rowData.length > 1 ? new SimpleStringProperty(rowData[1]) : new SimpleStringProperty("");
        });

        column3.setCellValueFactory(data -> {
            String[] rowData = data.getValue();
            return rowData != null && rowData.length > 2 ? new SimpleStringProperty(rowData[2]) : new SimpleStringProperty("");
        });
        column4.setCellValueFactory(data -> {
            String[] rowData = data.getValue();
            return rowData != null && rowData.length > 3 ? new SimpleStringProperty(rowData[3]) : new SimpleStringProperty("");
        });

        tableView.getColumns().addAll(column1, column2, column3, column4);

        loadDataFromFile();


        tableView.setRowFactory(tv -> {
            TableRow<String[]> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    rowData = row.getItem();
                    DeleteCar.setDisable(false);
                }
            });
            return row;
        });

        filteredData = new FilteredList<>(tableView.getItems(), p -> true);

        SearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(data -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                for (String cellData : data) {
                    if (cellData.toLowerCase().startsWith(lowerCaseFilter)) {
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
                File f=new File(Code.available);
                Code.NameSortAscending(f);
            }
            else if(selectedSortOption.equalsIgnoreCase("Name in descending Order")){
                File f=new File(Code.available);
                Code.NameSortDescending(f);
            }
            else if(selectedSortOption.equalsIgnoreCase("rent in ascending Order")){
                File f=new File(Code.available);
                Code.RentSortAscending(f);
            }
            else if(selectedSortOption.equalsIgnoreCase("rent in descending Order")){
                File f=new File(Code.available);
                Code.RentSortDescending(f);
            }
            Stage stage = (Stage) DeleteCar.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DeleteCar.fxml"));
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
        String filePath = "C:\\Users\\rayya\\Desktop\\AvailCars.txt";
        String line;

        try {
            File f = new File(filePath);
            if (!f.exists()) {
                tableView.setAccessibleText("No cars available");
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
    void handleDeletebutton(ActionEvent event) throws IOException {
        File file=new File(Code.available);
        Code.DeleteCar(rowData,file);
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Car has been deleted successfully!");
        alert.showAndWait();
        Stage stage = (Stage) DeleteCar.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DeleteCar.fxml"));
        Parent root = loader.load();
        stage.getScene().setRoot(root);
    }

    @FXML
    void handleSearchField(ActionEvent event) {

    }
}
