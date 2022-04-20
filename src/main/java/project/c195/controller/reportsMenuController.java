package project.c195.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import project.c195.helpers.appointmentDataSQL;
import project.c195.model.appointmentData;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class reportsMenuController implements Initializable {

    @FXML
    private TableColumn<appointmentData, Integer> appIDColContact;

    @FXML
    private TableColumn<appointmentData, Integer> appIDColMonth;

    @FXML
    private TableColumn<appointmentData, Integer> appIDColPast;

    @FXML
    private TableColumn<appointmentData, Integer> appIDColType;

    @FXML
    private TableView<appointmentData> appointmentByContactTable;

    @FXML
    private TableView<appointmentData> appointmentByMonthTable;

    @FXML
    private TableView<appointmentData> appointmentByTypeTable;

    @FXML
    private TableView<appointmentData> appointmentPastTable;

    @FXML
    private Text contactCountDisplay;

    @FXML
    private TableColumn<appointmentData, Integer> contactIDColContact;

    @FXML
    private TableColumn<appointmentData, Integer> contactIDColMonth;

    @FXML
    private TableColumn<appointmentData, Integer> contactIDColPast;

    @FXML
    private TableColumn<appointmentData, Integer> contactIDColType;

    @FXML
    private TableColumn<appointmentData, Integer> customerIDColContact;

    @FXML
    private TableColumn<appointmentData, Integer> customerIDColMonth;

    @FXML
    private TableColumn<appointmentData, Integer> customerIDColPast;

    @FXML
    private TableColumn<appointmentData, Integer> customerIDColType;

    @FXML
    private TableColumn<appointmentData, String> descriptionColContact;

    @FXML
    private TableColumn<appointmentData, String> descriptionColMonth;

    @FXML
    private TableColumn<appointmentData, String> descriptionColPast;

    @FXML
    private TableColumn<appointmentData, String> descriptionColType;

    @FXML
    private TableColumn<appointmentData, String> endColContact;

    @FXML
    private TableColumn<appointmentData, String> endColMonth;

    @FXML
    private TableColumn<appointmentData, String> endColPast;

    @FXML
    private TableColumn<appointmentData, String> endColType;

    @FXML
    private TableColumn<appointmentData, String> locationColContact;

    @FXML
    private TableColumn<appointmentData, String> locationColMonth;

    @FXML
    private TableColumn<appointmentData, String> locationColPast;

    @FXML
    private TableColumn<appointmentData, String> locationColType;

    @FXML
    private Text monthCountDisplay;

    @FXML
    private Text pastCountDisplay;

    @FXML
    private TableColumn<appointmentData, String> startColContact;

    @FXML
    private TableColumn<appointmentData, String> startColMonth;

    @FXML
    private TableColumn<appointmentData, String> startColPast;

    @FXML
    private TableColumn<appointmentData, String> startColType;

    @FXML
    private TableColumn<appointmentData, String> titleColContact;

    @FXML
    private TableColumn<appointmentData, String> titleColMonth;

    @FXML
    private TableColumn<appointmentData, String> titleColPast;

    @FXML
    private TableColumn<appointmentData, String> titleColType;

    @FXML
    private TableColumn<appointmentData, String> typeColContact;

    @FXML
    private TableColumn<appointmentData, String> typeColMonth;

    @FXML
    private TableColumn<appointmentData, String> typeColPast;

    @FXML
    private TableColumn<appointmentData, String> typeColType;

    @FXML
    private Text typeCountDisplay;

    int typeCount;
    int monthCount;
    int contactCount;
    int pastCount;
    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);

    ObservableList<appointmentData> monthList;
    ObservableList<appointmentData> contactList;
    ObservableList<appointmentData> typeList;
    ObservableList<appointmentData> pastList;

    @FXML
    public void openOverviewMenu(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Go to Overview Menu");
        alert.setHeaderText("Are you sure you want to return to the overview menu?");
        alert.setContentText("Return?");
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appIDColType.setCellValueFactory(new PropertyValueFactory<appointmentData, Integer>("appointmentID"));
        titleColType.setCellValueFactory(new PropertyValueFactory<appointmentData, String>("title"));
        customerIDColType.setCellValueFactory(new PropertyValueFactory<appointmentData, Integer>("customerID"));
        contactIDColType.setCellValueFactory(new PropertyValueFactory<appointmentData, Integer>("contactID"));
        descriptionColType.setCellValueFactory(new PropertyValueFactory<appointmentData, String>("description"));
        locationColType.setCellValueFactory(new PropertyValueFactory<appointmentData, String>("location"));
        typeColType.setCellValueFactory(new PropertyValueFactory<appointmentData, String>("type"));
        startColType.setCellValueFactory(new PropertyValueFactory<appointmentData, String>("start"));
        endColType.setCellValueFactory(new PropertyValueFactory<appointmentData, String>("end"));

        typeList = appointmentDataSQL.getAppointmentData();
        appointmentByTypeTable.setItems(typeList);

        appIDColMonth.setCellValueFactory(new PropertyValueFactory<appointmentData, Integer>("appointmentID"));
        titleColMonth.setCellValueFactory(new PropertyValueFactory<appointmentData, String>("title"));
        customerIDColMonth.setCellValueFactory(new PropertyValueFactory<appointmentData, Integer>("customerID"));
        contactIDColMonth.setCellValueFactory(new PropertyValueFactory<appointmentData, Integer>("contactID"));
        descriptionColMonth.setCellValueFactory(new PropertyValueFactory<appointmentData, String>("description"));
        locationColMonth.setCellValueFactory(new PropertyValueFactory<appointmentData, String>("location"));
        typeColMonth.setCellValueFactory(new PropertyValueFactory<appointmentData, String>("type"));
        startColMonth.setCellValueFactory(new PropertyValueFactory<appointmentData, String>("start"));
        endColMonth.setCellValueFactory(new PropertyValueFactory<appointmentData, String>("end"));

        monthList = appointmentDataSQL.getAppointmentData();
        appointmentByMonthTable.setItems(monthList);

        appIDColContact.setCellValueFactory(new PropertyValueFactory<appointmentData, Integer>("appointmentID"));
        titleColContact.setCellValueFactory(new PropertyValueFactory<appointmentData, String>("title"));
        customerIDColContact.setCellValueFactory(new PropertyValueFactory<appointmentData, Integer>("customerID"));
        contactIDColContact.setCellValueFactory(new PropertyValueFactory<appointmentData, Integer>("contactID"));
        descriptionColContact.setCellValueFactory(new PropertyValueFactory<appointmentData, String>("description"));
        locationColContact.setCellValueFactory(new PropertyValueFactory<appointmentData, String>("location"));
        typeColContact.setCellValueFactory(new PropertyValueFactory<appointmentData, String>("type"));
        startColContact.setCellValueFactory(new PropertyValueFactory<appointmentData, String>("start"));
        endColContact.setCellValueFactory(new PropertyValueFactory<appointmentData, String>("end"));

        contactList = appointmentDataSQL.getAppointmentData();
        appointmentByContactTable.setItems(monthList);

        appIDColPast.setCellValueFactory(new PropertyValueFactory<appointmentData, Integer>("appointmentID"));
        titleColPast.setCellValueFactory(new PropertyValueFactory<appointmentData, String>("title"));
        customerIDColPast.setCellValueFactory(new PropertyValueFactory<appointmentData, Integer>("customerID"));
        contactIDColPast.setCellValueFactory(new PropertyValueFactory<appointmentData, Integer>("contactID"));
        descriptionColPast.setCellValueFactory(new PropertyValueFactory<appointmentData, String>("description"));
        locationColPast.setCellValueFactory(new PropertyValueFactory<appointmentData, String>("location"));
        typeColPast.setCellValueFactory(new PropertyValueFactory<appointmentData, String>("type"));
        startColPast.setCellValueFactory(new PropertyValueFactory<appointmentData, String>("start"));
        endColPast.setCellValueFactory(new PropertyValueFactory<appointmentData, String>("end"));

        pastList = appointmentDataSQL.getAppointmentData();
        appointmentPastTable.setItems(pastList);
    }
}

