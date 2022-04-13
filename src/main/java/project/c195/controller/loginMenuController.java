package project.c195.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import project.c195.Main;
import project.c195.helpers.errorMessages;
import project.c195.helpers.logWriter;
import project.c195.helpers.usersDataSQL;
import project.c195.model.usersData;

import java.io.IOException;
import java.net.URL;
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
    private TextField passwordTextField;

    ResourceBundle rb = ResourceBundle.getBundle("Lang", Locale.getDefault());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

    //Need to add login credential check!
    public void openOverviewMenu(ActionEvent event) {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        boolean authorizedUser = usersDataSQL.loginAttempt(username, password);
        try {
            if(authorizedUser) {
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
