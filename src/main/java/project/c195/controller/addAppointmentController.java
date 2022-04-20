package project.c195.controller;

import project.c195.helpers.*;
import project.c195.model.customerData;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
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

    //All the used variables and objects in the class
    Alert alert;
    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
    String start, end, contactName, title, description, division, type, createdBy, lastUpdateBy;
    int customerID, appointmentID, userID, contactID;
    boolean validOverLap, validBusinessHours;
    LocalDateTime startDateTime, endDateTime;
    LocalDate dateSelected;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    /**
    This will set all days that are prior to LocalDate or weekends as disabled, so they can't be picked.
     */
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
        try {
            contactDropDown.setItems(contactsDataSQL.getContactsName());
            locationDropDown.setItems(countriesDataSQL.getCountryName());
            typeDropDown.getItems().addAll("Medical", "Walk-in", "Misc");
            divisionDropDown.setItems(divisionsDataSQL.getDivisionNameByCountryID(countriesDataSQL.getCountryIDByName(locationDropDown.getSelectionModel().getSelectedItem())));
            hourDropDown.getItems().addAll("08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21");
            minDropDown.getItems().addAll("00", "15", "30", "45");
            endHourDropDown.getItems().addAll("08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21");
            endMinDropDown.getItems().addAll("00", "15", "30", "45");
            userIDBox.setText(String.valueOf(usersDataSQL.getCurrentUsers().getUserID()));
            Callback<DatePicker, DateCell> dayCellFactory= this.getDayCellFactory();
            dateBox.setDayCellFactory(dayCellFactory);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This Action-event sets the divisions the corresponding Country divisions.
     */
    public void setDivisions() {
        divisionDropDown.setItems(divisionsDataSQL.getDivisionNameByCountryID(countriesDataSQL.getCountryIDByName(locationDropDown.getSelectionModel().getSelectedItem())));
    }

    /**
     * Method used to pull/assign the customers ID from the customer list on the overviewMenu
     * @param selectedCustomer
     */
    public void setCustomerID(customerData selectedCustomer) {
        customerIDBox.setText(String.valueOf(selectedCustomer.getId()));
    }

    /**
     * This prompts the user to confirm that they want to clear all data and return to overViewMenu
     * @param event
     */
    public void openOverviewMenu(ActionEvent event) {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel appointment creation");
        alert.setHeaderText("Are you sure you want to cancel, all data enter will be lost");
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

    /**
     * This is the boolean method used to verify that date/time entered doesn't conflict with any already existing
     * appointments or business hours. if there is any conflict it will return false which triggers an alert in the
     * addAppointment method.
     * @param startDateTime
     * @param endDateTime
     * @param appointmentDate
     * @return
     */
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

    /**
     * This method is the primary creation method.
     * On pressing "save" all the variables are assigned from the appropriate dropdowns or textboxes
     * The date picker and all the hour/min dropdowns are converted to Strings and concatenated together to form  the whole
     * Datetime value required by the database
     * There is a checker for if any empty boxes as well as checking if the selected appointment date/time conflicts
     * with business hours or an already existing appointments. the start and end times are also verified to be in
     * the correct order so that start can't come after end and vis a versa.
     * @param event
     */
    public void addAppointment(ActionEvent event) {
        try {
            customerID = Integer.parseInt(customerIDBox.getText());
            appointmentID = appointmentDataSQL.getAppointmentID();
            userID = parseInt(userIDBox.getText());
            contactID = contactsDataSQL.getContactsIDByName(String.valueOf(contactDropDown.getSelectionModel().getSelectedItem()));
            contactName = String.valueOf(contactDropDown.getSelectionModel().getSelectedItem());
            title  = titleBox.getText();
            description = descriptionBox.getText();
            division = String.valueOf(divisionDropDown.getSelectionModel().getSelectedItem());
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
                        LocalTime.parse(hourDropDown.getSelectionModel().getSelectedItem() + ":" + minDropDown.getSelectionModel().getSelectedItem(), formatter));
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
                        alert.setTitle("Create appointment");
                        alert.setHeaderText("Are you sure you want to create this appointment?");
                        alert.setContentText("Create?");
                        alert.getButtonTypes().setAll(yes, no);
                        alert.showAndWait().ifPresent(button -> {
                            if(button == yes) {
                                try {
                                    appointmentDataSQL.appointmentInsertSQL(customerID, appointmentID, userID, contactID, title, description, division, type, start, end, createdBy, lastUpdateBy);
                                    sceneController.switchScreen(event, "overviewMenu.fxml");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            else if(button == no) {
                                alert.close();
                            }
                        });
                    }
                    else {
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