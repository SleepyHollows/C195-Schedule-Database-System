package project.c195.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import project.c195.helpers.appointmentDataSQL;
import project.c195.helpers.usersDataSQL;
import project.c195.model.appointmentData;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class loginMenuController implements Initializable {

    @FXML
    private TextField usernameTextField;

    @FXML
    private Text locationText;

    @FXML
    private Text usernameTitle;

    @FXML
    private Text passwordTitle;

    @FXML
    private Button loginBtn;

    @FXML
    private Text loginTitle;

    @FXML
    private Label currentLocationDisplay;

    @FXML
    private TextField passwordTextField;

    ResourceBundle rb = ResourceBundle.getBundle("Lang", Locale.getDefault());

    /**
     * Will convert all text on screen and alerts to spanish if it is detected as the default language on the system
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentLocationDisplay.setText(ZoneId.systemDefault().toString());
        try {
            if (Locale.getDefault().getLanguage().equals("fr")) {
                locationText.setText(rb.getString("Location"));
                usernameTitle.setText(rb.getString("Username"));
                passwordTitle.setText(rb.getString("Password"));
                loginTitle.setText(rb.getString("loginTitle"));
                loginBtn.setText(rb.getString("loginBtn"));
            }
        } catch (Exception e) {
            System.out.print("Language Error");
        }
    }

    /**
     * This will compare username and password to those in the users table and if they match then will alert you if
     * there are any appointments coming up in 15 minutes, then sends you to the overviewMenu.
     *
     * @param event opens the overviewMenu
     * @throws SQLException will catch exceptions coming from getAppointmentsIn15Min
     */
    public void openOverviewMenu(ActionEvent event) throws SQLException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        boolean authorizedUser = usersDataSQL.loginAttempt(username, password);
        loggerController.logLoginAttempt(username, authorizedUser);
        try {
            if (authorizedUser) {
                upcomingAppointments();
                sceneController.switchScreen(event, "overviewMenu.fxml");
            }
            else {
                ButtonType clickOkay = new ButtonType(rb.getString("Okay"), ButtonBar.ButtonData.OK_DONE);
                Alert invalidInput = new Alert(Alert.AlertType.CONFIRMATION, rb.getString("errorMessages"), clickOkay);
                invalidInput.setTitle(rb.getString("errorTitle"));
                invalidInput.setHeaderText(rb.getString("errorTitle"));
                invalidInput.showAndWait();
            }
        }
            catch(Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * This gets all appointments in the database, then checks if there are in a 15-minute difference (greater than and
     * less than) of the current time. It will then display an alert for the user to see that there was one or more appointments
     * within 15-minutes, or it will show no appointments if no appointments were found to be in the time range.
     */
    public void upcomingAppointments() {
        ObservableList<appointmentData> getAllAppointments = appointmentDataSQL.getAppointmentData();
        LocalDateTime currentTimeMinus15Min = LocalDateTime.now().minusMinutes(15);
        LocalDateTime currentTimePlus15Min = LocalDateTime.now().plusMinutes(15);
        LocalDateTime startTime;
        int getAppointmentID = 0;
        int appointmentCount = 0;
        LocalDateTime displayTime = null;
        boolean appointmentWithin15Min;

        for (appointmentData appointment : getAllAppointments) {
            startTime = appointment.getStart().toLocalDateTime();
            appointmentWithin15Min = false;
            if ((startTime.isAfter(currentTimeMinus15Min) || startTime.isEqual(currentTimeMinus15Min)) && (startTime.isBefore(currentTimePlus15Min) || (startTime.isEqual(currentTimePlus15Min)))) {
                getAppointmentID = appointment.getAppointmentID();
                displayTime = startTime;
                appointmentWithin15Min = true;
            }
            if (appointmentWithin15Min) {
                String message = rb.getString("Upcoming") + getAppointmentID +
                        rb.getString("Starts") + displayTime;
                ButtonType clickOkay = new ButtonType(rb.getString("Okay"), ButtonBar.ButtonData.OK_DONE);
                Alert alert = new Alert(Alert.AlertType.WARNING, message, clickOkay);
                alert.setTitle(rb.getString("appointmentTitle"));
                alert.setHeaderText(rb.getString("appointmentTitle"));
                alert.showAndWait();
                appointmentCount++;
            }

        }
        if(appointmentCount == 0) {
            ButtonType clickOkay = new ButtonType(rb.getString("Okay"), ButtonBar.ButtonData.OK_DONE);
            Alert invalidInput = new Alert(Alert.AlertType.CONFIRMATION, rb.getString("Nope"), clickOkay);
            invalidInput.setTitle(rb.getString("appointmentTitle"));
            invalidInput.setHeaderText(rb.getString("appointmentTitle"));
            invalidInput.showAndWait();
        }
    }
}