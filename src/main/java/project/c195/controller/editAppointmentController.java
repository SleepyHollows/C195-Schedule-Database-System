package project.c195.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import project.c195.helpers.*;
import project.c195.model.appointmentData;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

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
    private ComboBox<String> locationDropDown;

    @FXML
    private ComboBox<String> divisionDropDown;

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

    //All the used variables and objects in the class
    Alert alert;
    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
    String start, end, contactName, title, description, location, type, createdBy, lastUpdateBy;
    int customerID, appointmentID, userID, contactID;
    boolean validOverLap, validBusinessHours;
    LocalDateTime startDateTime, endDateTime;
    LocalDate dateSelected;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    private Callback<DatePicker, DateCell> getDayCellFactory() {
        return new Callback<>() {
            @Override
            public DateCell call(final DatePicker dateBox1) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        LocalDate today = LocalDate.now();

                        // Disable Saturday, Sunday, Dates < Current Date.
                        if (item.getDayOfWeek() == DayOfWeek.SATURDAY //
                                || item.getDayOfWeek() == DayOfWeek.SUNDAY //
                                || item.compareTo(today) < 0) {
                            setDisable(true);
                            setStyle("-fx-background-color: #D3D3D3;");
                        }
                    }
                };
            }
        };
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactDropDown.setItems(contactsDataSQL.getContactsName());
        locationDropDown.setItems(countriesDataSQL.getCountryName());
        divisionDropDown.setItems(divisionsDataSQL.getDivisionName());
        typeDropDown.getItems().addAll("Medical", "Walk-in", "Misc");
        startHourDropDown.getItems().addAll("08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21");
        startMinDropDown.getItems().addAll("00", "15", "30", "45");
        endHourDropDown.getItems().addAll("08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21");
        endMinDropDown.getItems().addAll("00", "15", "30", "45");
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
        divisionDropDown.setValue(selectedAppointment.getLocation());
        locationDropDown.setValue(countriesDataSQL.getCountryNameByID(divisionsDataSQL.getDivisionCountryIDByName(divisionDropDown.getSelectionModel().getSelectedItem())));
    }

    public void openOverviewMenu(ActionEvent event) {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel appointment update");
        alert.setHeaderText("Are you sure you want to cancel, all changes will be lost");
        alert.setContentText("Cancel?");
        alert.getButtonTypes().setAll(yes, no);
        alert.showAndWait().ifPresent(type -> {
            if(type == yes) {
                try {
                    sceneController.switchScreen(event, "overviewMenu.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(type == no) {
                alert.close();
            }
        });
    }

    public Boolean validateBusinessHours(LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDate appointmentDate) {
        // (8am to 10pm EST)
        // Turn into zonedDateTimeObject, this allows for comparing customer's local date to EST
        ZonedDateTime startZoneDateTime = ZonedDateTime.of(startDateTime, usersDataSQL.getUserTimeZone());
        ZonedDateTime endZoneDateTime = ZonedDateTime.of(endDateTime, usersDataSQL.getUserTimeZone());
        ZonedDateTime startBusinessHours = ZonedDateTime.of(appointmentDate, LocalTime.of(8,0),
                ZoneId.of("America/New_York"));
        ZonedDateTime endBusinessHours = ZonedDateTime.of(appointmentDate, LocalTime.of(22, 0),
                ZoneId.of("America/New_York"));
        return !(startZoneDateTime.isBefore(startBusinessHours) | startZoneDateTime.isAfter(endBusinessHours) |
                endZoneDateTime.isBefore(startBusinessHours) | endZoneDateTime.isAfter(endBusinessHours) |
                startZoneDateTime.isAfter(endZoneDateTime));
    }

    public void updateAppointment(ActionEvent event) {
        try {
            customerID = Integer.parseInt(customerIDBox.getText());
            appointmentID = Integer.parseInt(appointmentIDBox.getText());
            userID = parseInt(userIDBox.getText());
            contactID = contactsDataSQL.getContactsIDByName(String.valueOf(contactDropDown.getSelectionModel().getSelectedItem()));
            contactName = String.valueOf(contactDropDown.getSelectionModel().getSelectedItem());
            title  = titleBox.getText();
            description = descriptionBox.getText();
            location = String.valueOf(divisionDropDown.getSelectionModel().getSelectedItem());
            type = String.valueOf(typeDropDown.getSelectionModel().getSelectedItem());
            createdBy = usersDataSQL.getCurrentUsers().getUsername();
            lastUpdateBy = usersDataSQL.getCurrentUsers().getUsername();

            if(customerIDBox.getText().isBlank() || contactName.isBlank() || type.isBlank() || description.isBlank() || locationDropDown.getValue() == null
                    || divisionDropDown.getValue() == null || startHourDropDown.getValue() == null || startMinDropDown.getValue() == null || endHourDropDown.getValue() == null
                    || endMinDropDown.getValue() == null || dateBox.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "All boxes must be filled to continue.");
                alert.showAndWait();
            }
            else {
                dateSelected = dateBox.getValue();
                start = dateSelected + " " + startHourDropDown.getSelectionModel().getSelectedItem() + ":" + startMinDropDown.getSelectionModel().getSelectedItem() + ":00";
                end = dateSelected + " " + endHourDropDown.getSelectionModel().getSelectedItem() + ":" + endMinDropDown.getSelectionModel().getSelectedItem() + ":00";
                Timestamp startTime = Timestamp.valueOf(start);
                Timestamp endTime = Timestamp.valueOf(end);
                startDateTime = LocalDateTime.of(dateBox.getValue(),
                        LocalTime.parse(startHourDropDown.getSelectionModel().getSelectedItem() + ":" + startMinDropDown.getSelectionModel().getSelectedItem(), formatter));
                endDateTime = LocalDateTime.of(dateBox.getValue(),
                        LocalTime.parse(endHourDropDown.getSelectionModel().getSelectedItem() + ":" + endMinDropDown.getSelectionModel().getSelectedItem(), formatter));
                validOverLap = appointmentDataSQL.checkForOverLapAppointments(startDateTime, endDateTime);
                validBusinessHours = validateBusinessHours(startDateTime, endDateTime, dateSelected);

                if(!endTime.after(startTime)) {
                    alert = new Alert(Alert.AlertType.ERROR, "End time can't be after Start time");
                    alert.showAndWait();
                }
                else {
                    if (validOverLap && validBusinessHours) {
                        alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Update appointment");
                        alert.setHeaderText("Are you sure you want to update this appointment?");
                        alert.setContentText("Update?");
                        alert.getButtonTypes().setAll(yes, no);
                        alert.showAndWait().ifPresent(button -> {
                            if(button == yes) {
                                try {
                                    System.out.println(start);
                                    System.out.println(end);
                                    appointmentDataSQL.updateAppointmentSQL(appointmentID, title, description, location, type, start, end, contactID, lastUpdateBy);
                                    sceneController.switchScreen(event, "overviewMenu.fxml");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            else if(button == no) {
                                alert.close();
                            }
                        });
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


