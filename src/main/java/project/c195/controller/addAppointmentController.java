package project.c195.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import project.c195.helpers.*;
import project.c195.model.customerData;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class addAppointmentController implements Initializable {

    @FXML
    private ComboBox<String> contactDropDown;

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
    String contactName, title, description, division, type, createdBy, lastUpdateBy, start, end;
    int customerID, appointmentID, userID, contactID;
    boolean validOverLap, validBusinessHours;
    LocalDate dateSelected;
    DateTimeFormatter hourMinFormatter = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Sets all date for each combo box dropdown options so that the user can pick them.
     * Lambda expression is used to disable dates prior to current day and weekends in the date picker
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            dateBox.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate dateBox, boolean empty) {
                    super.updateItem(dateBox, empty);
                    setDisable(
                            empty || dateBox.getDayOfWeek() == DayOfWeek.SATURDAY ||
                                    dateBox.getDayOfWeek() == DayOfWeek.SUNDAY || dateBox.isBefore(LocalDate.now()));
                }
            });

            contactDropDown.setItems(contactsDataSQL.getContactsName());
            locationDropDown.setItems(countriesDataSQL.getCountryName());
            typeDropDown.getItems().addAll("Medical", "Walk-in", "Misc");
            divisionDropDown.setItems(divisionsDataSQL.getDivisionNameByCountryID(countriesDataSQL.getCountryIDByName(locationDropDown.getSelectionModel().getSelectedItem())));
            hourDropDown.getItems().addAll("05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22");
            minDropDown.getItems().addAll("00", "15", "30", "45");
            endHourDropDown.getItems().addAll("05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22");
            endMinDropDown.getItems().addAll("00", "15", "30", "45");
            userIDBox.setText(String.valueOf(usersDataSQL.getCurrentUsers().getUserID()));
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
     * @param selectedCustomer the data pulled from the selected customer on the overviewMenu
     */
    public void setCustomerID(customerData selectedCustomer) {
        customerIDBox.setText(String.valueOf(selectedCustomer.getId()));
    }

    /**
     * This prompts the user to confirm that they want to clear all data and return to overViewMenu
     * @param event opens the overviewMenu
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
     *
     * @param startDateTime   zonedDateTime of the start appointment DateTime
     * @param endDateTime     zonedDateTime of the end appointment DateTime
     * @param appointmentDate ZonedDateTime of the selected appointment date
     * @return returns false if any conflicts are found
     */
    public boolean validateBusinessHours(LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDate appointmentDate) {
        // (8am to 10pm EST)
        //Converting selected datetime to EST so that we can verify it's not outside of business hours

        ZonedDateTime startZonedDateTime = ZonedDateTime.of(startDateTime, usersDataSQL.getUserTimeZone());
        ZonedDateTime endZonedDateTime = ZonedDateTime.of(endDateTime, usersDataSQL.getUserTimeZone());
        ZonedDateTime startBusinessHours = ZonedDateTime.of(appointmentDate, LocalTime.of(8,0),
                ZoneId.of("America/New_York"));
        ZonedDateTime endBusinessHours = ZonedDateTime.of(appointmentDate, LocalTime.of(22, 0),
                ZoneId.of("America/New_York"));

        //All possible conflicts for the appointment
        return !(startZonedDateTime.isBefore(startBusinessHours) | startZonedDateTime.isAfter(endBusinessHours) |
                endZonedDateTime.isBefore(startBusinessHours) | endZonedDateTime.isAfter(endBusinessHours) |
                startZonedDateTime.isAfter(endZonedDateTime));
    }

    /**
     * We send over the start and end time as well as the appointment ID to appointmentDataSQL where it will look for
     * any appointments that may conflict with the selected time.
     * @param thisAppointmentID The appointment ID used to make sure we aren't comparing an appointment with itself
     * @param startDateTime The start time selected for the appointment
     * @param endDateTime The end time selected for the appointment
     * @return a boolean of true of false depending on if there are conflicts or not
     * @throws SQLException catches any exceptions from the SQL statements
     */
    public Boolean validateOverlapAppointments(int thisAppointmentID, LocalDateTime startDateTime,
                                               LocalDateTime endDateTime) throws SQLException {

        // Get list of appointments that might have conflicts
        return appointmentDataSQL.checkForOverlap(startDateTime, endDateTime, thisAppointmentID);
    }

    /**
     * This method is the primary creation method.
     * On pressing "save" all the variables are assigned from the appropriate dropdowns or textboxes
     * The date picker and all the hour/min dropdowns are converted to Strings and concatenated together to form  the whole
     * Datetime value required by the database
     * There is a checker for if any empty boxes as well as checking if the selected appointment date/time conflicts
     * with business hours or an already existing appointments. the start and end times are also verified to be in
     * the correct order so that start can't come after end and vis a versa.
     * @param event switches screen back to overview if insert is error free
     */
    public void addAppointment(ActionEvent event) throws SQLException {
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
                //All the data needed for setting the appointment datetime for insert and getting the current datetime
                //of the user
                dateSelected = dateBox.getValue();
                LocalTime localStartHour = LocalTime.parse(hourDropDown.getSelectionModel().getSelectedItem() + ":" + minDropDown.getSelectionModel().getSelectedItem(), hourMinFormatter);
                LocalTime localEndHour = LocalTime.parse(endHourDropDown.getSelectionModel().getSelectedItem() + ":" + endMinDropDown.getSelectionModel().getSelectedItem(), hourMinFormatter);

                //combine date and start/end times together
                LocalDateTime startDT = LocalDateTime.of(dateSelected, LocalTime.from(localStartHour));
                LocalDateTime endDT = LocalDateTime.of(dateSelected, LocalTime.from(localEndHour));
                //convert startDT and endDT to UTC
                ZonedDateTime startUTC = startDT.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
                ZonedDateTime endUTC = endDT.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
                //convert UTC time to a timestamp for database insertion
                Timestamp sqlStartTS = Timestamp.valueOf(startUTC.toLocalDateTime());
                Timestamp sqlEndTS = Timestamp.valueOf(endUTC.toLocalDateTime());
                start = dateSelected + " " + hourDropDown.getSelectionModel().getSelectedItem() + ":" + minDropDown.getSelectionModel().getSelectedItem() + ":00";
                end = dateSelected + " " + endHourDropDown.getSelectionModel().getSelectedItem() + ":" + endMinDropDown.getSelectionModel().getSelectedItem() + ":00";
                Timestamp startTime = Timestamp.valueOf(start);
                Timestamp endTime = Timestamp.valueOf(end);
                validOverLap = validateOverlapAppointments(appointmentID, sqlStartTS.toLocalDateTime(), sqlEndTS.toLocalDateTime());
                validBusinessHours = validateBusinessHours(startDT, endDT, dateSelected);

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
                                    appointmentDataSQL.appointmentInsertSQL(customerID, appointmentID, userID, contactID, title, description, division, type, sqlStartTS, sqlEndTS, createdBy, lastUpdateBy);
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