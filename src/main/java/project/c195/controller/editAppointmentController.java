package project.c195.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import project.c195.helpers.appointmentDataSQL;
import project.c195.helpers.contactsDataSQL;
import project.c195.helpers.divisionsDataSQL;
import project.c195.helpers.usersDataSQL;
import project.c195.model.appointmentData;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class editAppointmentController implements Initializable {

    @FXML
    private TextField appointmentIDBox;

    @FXML
    private DatePicker dateBox;

    @FXML
    private ComboBox<String> contactDropDown;

    @FXML
    private TextField customerIDBox;

    @FXML
    private TextField descriptionBox;

    @FXML
    private ComboBox<String> endHourDropDown;

    @FXML
    private ComboBox<String> endMinDropDown;

    @FXML
    private ComboBox<String> locationsDropDown;

    @FXML
    private ComboBox<String> startHourDropDown;

    @FXML
    private ComboBox<String> startMinDropDown;

    @FXML
    private TextField titleBox;

    @FXML
    private ComboBox<String> typeDropDown;

    @FXML
    private TextField userIDBox;

    String title;
    String description;
    String location;
    String type;
    String start;
    String end;
    int contactID;
    int appointmentID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactDropDown.setItems(contactsDataSQL.getContactsName());
        locationsDropDown.setItems(divisionsDataSQL.getDivisionName());
        typeDropDown.getItems().addAll("Medical", "Walk-in", "Misc");
        startHourDropDown.getItems().addAll("08", "09", "10", "11", "12", "1", "2", "3", "4");
        startMinDropDown.getItems().addAll("00", "15", "30", "45");
        endHourDropDown.getItems().addAll("08", "09", "10", "11", "12", "1", "2", "3", "4");
        endMinDropDown.getItems().addAll("00", "15", "30", "45");
    }

    public String getDate() {
        LocalDate datePicked = dateBox.getValue();
        return datePicked.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public void openOverviewMenu(ActionEvent event) throws IOException {
        sceneController.switchScreen(event, "overviewMenu.fxml");
    }

    public void setData(appointmentData selectedAppointment) {
        ZonedDateTime startDateTimeUTC = selectedAppointment.getStart().toInstant().atZone(ZoneOffset.UTC);
        ZonedDateTime endDateTimeUTC = selectedAppointment.getEnd().toInstant().atZone(ZoneOffset.UTC);

        DateTimeFormatter hourFormat = DateTimeFormatter.ofPattern("HH");
        DateTimeFormatter minFormat = DateTimeFormatter.ofPattern("mm");

        ZonedDateTime localStartDateTime = startDateTimeUTC.withZoneSameInstant(usersDataSQL.getUserTimeZone());
        ZonedDateTime localEndDateTime = endDateTimeUTC.withZoneSameInstant(usersDataSQL.getUserTimeZone());

        String localStartHour = localStartDateTime.format(hourFormat);
        String localStartMin = localStartDateTime.format(minFormat);
        String localEndHour = localEndDateTime.format(hourFormat);
        String localEndMin = localEndDateTime.format(minFormat);

        customerIDBox.setText(String.valueOf(selectedAppointment.getCustomerID()));
        userIDBox.setText(String.valueOf(selectedAppointment.getUserID()));
        titleBox.setText(selectedAppointment.getTitle());
        descriptionBox.setText(selectedAppointment.getDescription());
        appointmentIDBox.setText(String.valueOf(selectedAppointment.getAppointmentID()));
        dateBox.setValue(selectedAppointment.getStart().toLocalDateTime().toLocalDate());
        contactDropDown.setValue(contactsDataSQL.getContactsNameByID(selectedAppointment.getContactID()));
        typeDropDown.setValue(selectedAppointment.getType());
        startHourDropDown.setValue(localStartHour);
        startMinDropDown.setValue(localStartMin);
        endHourDropDown.setValue(localEndHour);
        endMinDropDown.setValue(localEndMin);

    }

    @FXML
    void updateAppointment(ActionEvent event) throws IOException {
        title = titleBox.getText();
        description = descriptionBox.getText();
        location = String.valueOf(locationsDropDown.getSelectionModel().getSelectedItem());
        type = String.valueOf(typeDropDown.getSelectionModel().getSelectedItem());
        start = getDate() + " " + startHourDropDown.getSelectionModel().getSelectedItem() + ":" + startMinDropDown.getSelectionModel().getSelectedItem() + ":00";
        end = getDate() + " " + endHourDropDown.getSelectionModel().getSelectedItem() + ":" + endMinDropDown.getSelectionModel().getSelectedItem() + ":00";
        contactID = Integer.parseInt(customerIDBox.getText());
        appointmentID = Integer.parseInt(appointmentIDBox.getText());

        appointmentDataSQL.updateAppointmentSQL(title, description, location, type, start, end, contactID, appointmentID);
        sceneController.switchScreen(event, "overviewMenu.fxml");
    }


}


