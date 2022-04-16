package project.c195.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import project.c195.helpers.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.time.LocalDate;

public class addAppointmentController implements Initializable {

    @FXML
    private ComboBox contactDropDown;

    @FXML
    private TextField customerIDBox;

    @FXML
    private TextField descriptionBox;

    @FXML
    private ComboBox divisionDropDown;

    @FXML
    private ComboBox endHourDropDown;

    @FXML
    private ComboBox endMinDropDown;

    @FXML
    private ComboBox hourDropDown;

    @FXML
    private ComboBox locationDropDown;

    @FXML
    private ComboBox minDropDown;

    @FXML
    private DatePicker dateBox;

    @FXML
    private TextField titleBox;

    @FXML
    private ComboBox typeDropDown;

    @FXML
    private TextField userIDBox;

    public String getDate() {
        LocalDate datePicked = dateBox.getValue();
        String dateFormatting = datePicked.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return dateFormatting;
    }

    public void setText(ActionEvent event) throws IOException {
        locationDropDown.setPromptText(String.valueOf(locationDropDown.getContextMenu()));
    }

    public void openOverviewMenu(ActionEvent event) throws IOException {
        sceneController.switchScreen(event, "overviewMenu.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            contactDropDown.setItems(contactsDataSQL.getContactsName());
            locationDropDown.setItems(countriesDataSQL.getCountryName());
            typeDropDown.getItems().addAll("Medical", "Walk-in", "Misc");
            divisionDropDown.setItems(divisionsDataSQL.getDivisionName());
            hourDropDown.getItems().addAll("08", "09", "10", "45");
            minDropDown.getItems().addAll("00", "15", "30", "45");
            endHourDropDown.getItems().addAll("08", "09", "10", "45");
            endMinDropDown.getItems().addAll("00", "15", "30", "45");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addAppointment(ActionEvent event) throws IOException, SQLException {
        int customerID = Integer.parseInt(customerIDBox.getText());
        int appointmentID = appointmentDataSQL.getAppointmentID();
        int userID = usersDataSQL.getCurrentUsers().getUserID();
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
