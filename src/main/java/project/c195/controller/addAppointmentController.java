package project.c195.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import project.c195.helpers.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class addAppointmentController implements Initializable {

    @FXML
    private ComboBox<Integer> contactDropDown;

    @FXML
    private TextField customerIDBox;

    @FXML
    private TextField descriptionBox;

    @FXML
    private ComboBox<Integer> divisionDropDown;

    @FXML
    private ComboBox<String> endHourDropDown;

    @FXML
    private ComboBox<String> endMinDropDown;

    @FXML
    private ComboBox<String> hourDropDown;

    @FXML
    private ComboBox<String> locationDropDown;

    @FXML
    private ComboBox<String> minDropDown;

    @FXML
    private DatePicker dateBox;

    @FXML
    private TextField titleBox;

    @FXML
    private ComboBox<String> typeDropDown;

    @FXML
    private TextField userIDBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            contactDropDown.setItems(contactsDataSQL.getContactsName());
            locationDropDown.setItems(countriesDataSQL.getCountryName());
            typeDropDown.getItems().addAll("Medical", "Walk-in", "Misc");
            divisionDropDown.setItems(divisionsDataSQL.getDivisionName());
            hourDropDown.getItems().addAll("08", "09", "10", "11", "12", "1", "2", "3", "4");
            minDropDown.getItems().addAll("00", "15", "30", "45");
            endHourDropDown.getItems().addAll("08", "09", "10", "11", "12", "1", "2", "3", "4");
            endMinDropDown.getItems().addAll("00", "15", "30", "45");
            userIDBox.setText(String.valueOf(usersDataSQL.getCurrentUsers().getUserID()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDate() {
        LocalDate datePicked = dateBox.getValue();
        return datePicked.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public void openOverviewMenu(ActionEvent event) throws IOException {
        sceneController.switchScreen(event, "overviewMenu.fxml");
    }

    public void addAppointment(ActionEvent event) throws IOException {
        int customerID = Integer.parseInt(customerIDBox.getText());
        int appointmentID = appointmentDataSQL.getAppointmentID();
        int userID = Integer.parseInt(userIDBox.getText());
        int contactID = contactsDataSQL.getContactsIDByName(String.valueOf(contactDropDown.getSelectionModel().getSelectedItem()));
        String title  = titleBox.getText();
        String description = descriptionBox.getText();
        String location = String.valueOf(locationDropDown.getSelectionModel().getSelectedItem());
        String type = String.valueOf(typeDropDown.getSelectionModel().getSelectedItem());
        String start = getDate() + " " + hourDropDown.getSelectionModel().getSelectedItem() + ":" + minDropDown.getSelectionModel().getSelectedItem() + ":00";
        String end = getDate() + " " + endHourDropDown.getSelectionModel().getSelectedItem() + ":" + endMinDropDown.getSelectionModel().getSelectedItem() + ":00";

        appointmentDataSQL.appointmentInsertSQL(customerID, appointmentID, userID, contactID,title, description, location, type, start, end);
        sceneController.switchScreen(event, "overviewMenu.fxml");
    }
}
