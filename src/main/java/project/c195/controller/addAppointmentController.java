package project.c195.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import project.c195.helpers.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class addAppointmentController implements Initializable {

    @FXML
    private ComboBox<Integer> contactDropDown;

    @FXML
    private TextField customerIDBox;

    @FXML
    private TextField descriptionBox;

    @FXML
    private ComboBox<String> divisionDropDown;

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

    Alert alert;
    String start, end, contactName, title, description, location, type, createdBy, lastUpdateBy;
    int customerID, appointmentID, userID, contactID;
    boolean validOverLap, validBusinessHours;
    LocalDateTime startDateTime, endDateTime;
    LocalDate dateSelected;
    ZonedDateTime createDate, lastUpdated;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            contactDropDown.setItems(contactsDataSQL.getContactsName());
            locationDropDown.setItems(countriesDataSQL.getCountryName());
            typeDropDown.getItems().addAll("Medical", "Walk-in", "Misc");
            divisionDropDown.setItems(divisionsDataSQL.getDivisionName());
            hourDropDown.getItems().addAll("08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21");
            minDropDown.getItems().addAll("00", "15", "30", "45");
            endHourDropDown.getItems().addAll("08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21");
            endMinDropDown.getItems().addAll("00", "15", "30", "45");
            userIDBox.setText(String.valueOf(usersDataSQL.getCurrentUsers().getUserID()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openOverviewMenu(ActionEvent event) throws IOException {
        sceneController.switchScreen(event, "overviewMenu.fxml");
    }

    public Boolean validateBusinessHours(LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDate appointmentDate) {
        // (8am to 10pm EST, Not including weekends)
        // Turn into zonedDateTimeObject, so we can evaluate whatever time was entered in user time zone against EST

        ZonedDateTime startZonedDateTime = ZonedDateTime.of(startDateTime, usersDataSQL.getUserTimeZone());
        ZonedDateTime endZonedDateTime = ZonedDateTime.of(endDateTime, usersDataSQL.getUserTimeZone());

        ZonedDateTime startBusinessHours = ZonedDateTime.of(appointmentDate, LocalTime.of(8,0),
                ZoneId.of("America/New_York"));
        ZonedDateTime endBusinessHours = ZonedDateTime.of(appointmentDate, LocalTime.of(22, 0),
                ZoneId.of("America/New_York"));

        // If startTime is before or after business hours
        // If end time is before or after business hours
        // if startTime is after endTime - these should cover all possible times entered and validate input.
        if (startZonedDateTime.isBefore(startBusinessHours) | startZonedDateTime.isAfter(endBusinessHours) |
                endZonedDateTime.isBefore(startBusinessHours) | endZonedDateTime.isAfter(endBusinessHours) |
                startZonedDateTime.isAfter(endZonedDateTime)) {
            return false;

        }
        else {
            return true;
        }

    }

    public void addAppointment(ActionEvent event) throws IOException {
        try {
            customerID = Integer.parseInt(customerIDBox.getText());
            appointmentID = appointmentDataSQL.getAppointmentID();
            userID = parseInt(userIDBox.getText());
            contactID = contactsDataSQL.getContactsIDByName(String.valueOf(contactDropDown.getSelectionModel().getSelectedItem()));
            contactName = String.valueOf(contactDropDown.getSelectionModel().getSelectedItem());
            title  = titleBox.getText();
            description = descriptionBox.getText();
            location = String.valueOf(locationDropDown.getSelectionModel().getSelectedItem());
            type = String.valueOf(typeDropDown.getSelectionModel().getSelectedItem());
            createdBy = usersDataSQL.getCurrentUsers().getUsername();
            lastUpdateBy = usersDataSQL.getCurrentUsers().getUsername();

            if(customerIDBox.getText().isBlank() || contactName.isBlank() || type.isBlank() || description.isBlank() || locationDropDown.getValue() == null
                    || divisionDropDown.getValue() == null || hourDropDown.getValue() == null || minDropDown.getValue() == null || endHourDropDown.getValue() == null
                    || endMinDropDown.getValue() == null || dateBox.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "All boxes must be filled to continue.");
                alert.showAndWait();
            }
            else {
                dateSelected = dateBox.getValue();
                start = dateSelected + " " + hourDropDown.getSelectionModel().getSelectedItem() + ":" + minDropDown.getSelectionModel().getSelectedItem() + ":00";
                end = dateSelected + " " + endHourDropDown.getSelectionModel().getSelectedItem() + ":" + endMinDropDown.getSelectionModel().getSelectedItem() + ":00";
                Timestamp startTime = Timestamp.valueOf(start);
                Timestamp endTime = Timestamp.valueOf(end);
                startDateTime = LocalDateTime.of(dateBox.getValue(),
                        LocalTime.parse(endHourDropDown.getSelectionModel().getSelectedItem() + ":" + endMinDropDown.getSelectionModel().getSelectedItem(), formatter));
                endDateTime = LocalDateTime.of(dateBox.getValue(),
                        LocalTime.parse(endHourDropDown.getSelectionModel().getSelectedItem() + ":" + endMinDropDown.getSelectionModel().getSelectedItem(), formatter));
                createDate = ZonedDateTime.of(startDateTime, usersDataSQL.getUserTimeZone());
                lastUpdated = ZonedDateTime.of(endDateTime, usersDataSQL.getUserTimeZone());
                validOverLap = appointmentDataSQL.checkForOverLapAppointments(startDateTime, endDateTime, dateSelected);
                validBusinessHours = validateBusinessHours(startDateTime, endDateTime, dateSelected);

                if(!endTime.after(startTime)) {
                    alert = new Alert(Alert.AlertType.ERROR, "End time can't be after Start time");
                    alert.showAndWait();
                }
                else {
                    if (validOverLap && validBusinessHours) {
                        appointmentDataSQL.appointmentInsertSQL(customerID, appointmentID, userID, contactID, title, description, location, type, start, end, createDate, createdBy, lastUpdated, lastUpdateBy);
                        sceneController.switchScreen(event, "overviewMenu.fxml");
                    } else {
                        alert = new Alert(Alert.AlertType.ERROR, "Appointment either overlaps with another or the set time is out of business hours");
                        alert.showAndWait();
                    }
                }
            }
        }
        catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Customer ID must be filled in and using only integers");
            alert.showAndWait();
        }
    }
}