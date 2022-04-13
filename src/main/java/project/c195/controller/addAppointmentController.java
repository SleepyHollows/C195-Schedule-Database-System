package project.c195.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import project.c195.Main;
import project.c195.helpers.JDBC;
import project.c195.helpers.appointmentDataSQL;
import project.c195.helpers.customerDataSQL;
import project.c195.model.appointmentData;
import project.c195.model.contactsData;
import project.c195.model.customerData;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.EventListener;
import java.util.ResourceBundle;

public class addAppointmentController implements Initializable {

    @FXML
    private ChoiceBox contactDropDown;

    @FXML
    private TextField customerIDBox;

    @FXML
    private TextField descriptionBox;

    @FXML
    private ChoiceBox divisionDropDown;

    @FXML
    private TextField endBox;

    @FXML
    private ChoiceBox hourDropDown;

    @FXML
    private ChoiceBox locationDropDown;

    @FXML
    private ChoiceBox minDropDown;

    @FXML
    private TextField startBox;

    @FXML
    private TextField titleBox;

    @FXML
    private ChoiceBox typeDropDown;

    @FXML
    private TextField userIDBox;

    ObservableList <contactsData> contactsList;

    public void openOverviewMenu(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("overviewMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 800);
        Stage overviewStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        overviewStage.setScene(scene);
        overviewStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactDropDown.getItems().addAll("5", "6");
        locationDropDown.getItems().addAll("test", "test1");
        typeDropDown.getItems().addAll("test", "test1");
    }

    public void addAppointment(ActionEvent event) throws IOException, SQLException {
        //appointmentDataSQL.appointmentInsertSQL(Integer.parseInt(customerIDBox.getText()),4, contactDropDown.getText(), Integer.parseInt(userIDBox.getText()),titleBox.getText(),descriptionBox.getText(), String.valueOf(locationDropDown), String.valueOf(typeDropDown),startBox.getText(), endBox.getText());

        sceneController.switchScreen(event, "overviewMenu.fxml");
    }
}
