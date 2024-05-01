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

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ManageReservations {
    String[] rowData;
    @FXML
    private TextField SearchField;
    @FXML
    private ComboBox<String> sortComboBox;
    @FXML
    private Button Enqueue;
    @FXML
    private Button Remove;
    @FXML
    private TableView<String[]> tableView;
    private FilteredList<String[]> filteredData;

    public void initialize() {
        Enqueue.setDisable(true);
        Remove.setDisable(true);
        TableColumn<String[], String> column1 = new TableColumn<>("User");
        TableColumn<String[], String> column2 = new TableColumn<>("Make");
        TableColumn<String[], String> column3 = new TableColumn<>("Model");
        TableColumn<String[], String> column4 = new TableColumn<>("Year");
        TableColumn<String[], String> column5 = new TableColumn<>("Rent Per Day");
        TableColumn<String[], String> column6 = new TableColumn<>("Days Rented");
        TableColumn<String[], String> column7 = new TableColumn<>("Total Rent");

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
        column5.setCellValueFactory(data -> {
            String[] rowData = data.getValue();
            return rowData != null && rowData.length > 4 ? new SimpleStringProperty(rowData[4]) : new SimpleStringProperty("");
        });
        column6.setCellValueFactory(data -> {
            String[] rowData = data.getValue();
            return rowData != null && rowData.length > 5 ? new SimpleStringProperty(rowData[5]) : new SimpleStringProperty("");
        });
        column7.setCellValueFactory(data -> {
            String[] rowData = data.getValue();
            return rowData != null && rowData.length > 6 ? new SimpleStringProperty(rowData[6]) : new SimpleStringProperty("");
        });

        tableView.getColumns().addAll(column1, column2, column3, column4,column5,column6,column7);

        loadDataFromFile();


        tableView.setRowFactory(tv -> {
            TableRow<String[]> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    rowData = row.getItem();
                    Enqueue.setDisable(false);
                    Remove.setDisable(false);
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
                File f=new File(Code.reservations);
                Code.NameSortAscending(f);
            }
            else if(selectedSortOption.equalsIgnoreCase("Name in descending Order")){
                File f=new File(Code.reservations);
                Code.NameSortDescending(f);
            }
            else if(selectedSortOption.equalsIgnoreCase("rent in ascending Order")){
                File f=new File(Code.reservations);
                Code.RentSortAscending(f);
            }
            else if(selectedSortOption.equalsIgnoreCase("rent in descending Order")){
                File f=new File(Code.reservations);
                Code.RentSortDescending(f);
            }
            Stage stage = (Stage) Enqueue.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageReservations.fxml"));
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
        String filePath = "C:\\Users\\Dell\\Desktop\\Reservations.txt";
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
    void handleRemovebutton(ActionEvent event) throws IOException {
        Code.DeleteCar(rowData);
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Car has been removed successfully!");
        alert.showAndWait();
        Stage stage = (Stage) Remove.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageReservations.fxml"));
        Parent root = loader.load();
        stage.getScene().setRoot(root);
    }

    @FXML
    void handleSearchField(ActionEvent event) {

    }
    @FXML
    void handleEnqueuebutton(ActionEvent event) {
        try{
            File reservation=new File(Code.reservations);
            File tem=new File(Code.temp);
            String user="C:\\Users\\Dell\\Desktop\\%s RentedCars.txt".formatted(rowData[0]);
            File userFile=new File(user);
            BufferedWriter userWriter=new BufferedWriter(new FileWriter(userFile,true));
            String buffer="%s %s %s %s %s %s".formatted(rowData[1],rowData[2],rowData[3],rowData[4],rowData[5],rowData[6]);
            String carLine="%s %s %s %s %s %s %s".formatted(rowData[0],rowData[1],rowData[2],rowData[3],rowData[4],rowData[5],rowData[6]);
            userWriter.write(buffer);
            userWriter.newLine();
            userWriter.close();
            BufferedReader reserveReader=new BufferedReader(new FileReader(reservation));
            BufferedWriter tempWriter=new BufferedWriter(new FileWriter(tem));
            while((buffer=reserveReader.readLine())!=null){
                if(buffer.equalsIgnoreCase(carLine)){
                }
                else {
                    tempWriter.write(buffer);
                    tempWriter.newLine();
                }
            }
            reserveReader.close();
            tempWriter.close();
            BufferedReader tempReader=new BufferedReader(new FileReader(tem));
            BufferedWriter reserveWriter=new BufferedWriter(new FileWriter(reservation));
            while((buffer=tempReader.readLine())!=null){
                    reserveWriter.write(buffer);
                    reserveWriter.newLine();
            }
            tempReader.close();
            reserveWriter.close();
            tem.delete();
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Car has been dequeued successfully!");
            alert.showAndWait();
            Stage stage = (Stage) Enqueue.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageReservations.fxml"));
            Parent root = loader.load();
            stage.getScene().setRoot(root);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
