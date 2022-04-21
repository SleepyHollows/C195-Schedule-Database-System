package project.c195.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import project.c195.helpers.appointmentDataSQL;
import project.c195.helpers.errorMessages;
import project.c195.helpers.usersDataSQL;
import project.c195.model.appointmentData;

import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class loginMenuController implements Initializable {

    @FXML
    private TextField usernameTextField;

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
            if(Locale.getDefault().getLanguage().equals("es")) {
                usernameTitle.setText(rb.getString("Username"));
                passwordTitle.setText(rb.getString("Password"));
                loginTitle.setText(rb.getString("loginTitle"));
                loginBtn.setText(rb.getString("loginBtn"));
            }
        }
        catch(Exception e) {
            System.out.print("Language Error");
        }
    }

    /**
     * This will compare username and password to those in the users table and if they match then will alert you if
     * there are any appointments coming up in 15 minutes, then send you to the overviewMenu.
     * @param event opens the overviewMenu
     * @throws SQLException will catch exceptions coming from getAppointmentsIn15Min
     */
    public void openOverviewMenu(ActionEvent event) throws SQLException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        boolean authorizedUser = usersDataSQL.loginAttempt(username, password);
        loggerController.logLoginAttempt(username, authorizedUser);
        try {
            if(authorizedUser) {
                ObservableList<appointmentData> upcomingAppointments = appointmentDataSQL.getAppointmentsIn15Min();
                if(!upcomingAppointments.isEmpty()) {
                    for (appointmentData upcoming : upcomingAppointments) {
                        String message = "Upcoming appointment ID: " + upcoming.getAppointmentID() + " Starts at: " +
                                upcoming.getStart().toString();
                        ButtonType clickOkay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                        Alert alert = new Alert(Alert.AlertType.WARNING, message, clickOkay);
                        alert.showAndWait();
                    }
                }
                else {
                    ButtonType clickOkay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                    Alert invalidInput = new Alert(Alert.AlertType.CONFIRMATION, "No Appointments in the next 15 minutes", clickOkay);
                    invalidInput.showAndWait();
                }
                sceneController.switchScreen(event, "overviewMenu.fxml");
            }
            else {
                errorMessages.translatedError("errorMessages");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
