package project.c195.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import project.c195.helpers.appointmentDataSQL;
import project.c195.helpers.contactsDataSQL;
import project.c195.helpers.customerDataSQL;
import project.c195.model.appointmentData;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class reportsMenuController implements Initializable {

    @FXML
    private TableColumn<appointmentData, Integer> appIDColContact;

    @FXML
    private TableColumn<appointmentData, Integer> appIDColCustomer;

    @FXML
    private TableColumn<appointmentData, Integer> appIDColMonth;

    @FXML
    private TableColumn<appointmentData, Integer> appIDColType;

    @FXML
    private TableView<appointmentData> appointmentByContactTable;

    @FXML
    private TableView<appointmentData> appointmentByCustomerTable;

    @FXML
    private TableView<appointmentData> appointmentByMonthTable;

    @FXML
    private TableView<appointmentData> appointmentByTypeTable;

    @FXML
    private ComboBox<String> contactDropDown;

    @FXML
    private TableColumn<appointmentData, Integer> contactIDColContact;

    @FXML
    private TableColumn<appointmentData, Integer> contactIDColCustomer;

    @FXML
    private TableColumn<appointmentData, Integer> contactIDColMonth;

    @FXML
    private TableColumn<appointmentData, Integer> contactIDColType;

    @FXML
    private TableColumn<appointmentData, Integer> customerIDColContact;

    @FXML
    private TableColumn<appointmentData, Integer> customerIDColCustomer;

    @FXML
    private TableColumn<appointmentData, Integer> customerIDColMonth;

    @FXML
    private TableColumn<appointmentData, Integer> customerIDColType;

    @FXML
    private ComboBox<String> customerIDDropDown;

    @FXML
    private TableColumn<appointmentData, String> descriptionColContact;

    @FXML
    private TableColumn<appointmentData, String> descriptionColCustomer;

    @FXML
    private TableColumn<appointmentData, String> descriptionColMonth;

    @FXML
    private TableColumn<appointmentData, String> descriptionColType;

    @FXML
    private TableColumn<appointmentData, String> endColContact;

    @FXML
    private TableColumn<appointmentData, String> endColCustomer;

    @FXML
    private TableColumn<appointmentData, String> endColMonth;

    @FXML
    private TableColumn<appointmentData, String> endColType;

    @FXML
    private TableColumn<appointmentData, String> locationColContact;

    @FXML
    private TableColumn<appointmentData, String> locationColCustomer;

    @FXML
    private TableColumn<appointmentData, String> locationColMonth;

    @FXML
    private TableColumn<appointmentData, String> locationColType;

    @FXML
    private ComboBox<String> monthDropDown;

    @FXML
    private TableColumn<appointmentData, String> startColContact;

    @FXML
    private TableColumn<appointmentData, String> startColCustomer;

    @FXML
    private TableColumn<appointmentData, String> startColMonth;

    @FXML
    private TableColumn<appointmentData, String> startColType;

    @FXML
    private TableColumn<appointmentData, String> titleColContact;

    @FXML
    private TableColumn<appointmentData, String> titleColCustomer;

    @FXML
    private TableColumn<appointmentData, String> titleColMonth;

    @FXML
    private TableColumn<appointmentData, String> titleColType;

    @FXML
    private TableColumn<appointmentData, String> typeColContact;

    @FXML
    private TableColumn<appointmentData, String> typeColCustomer;

    @FXML
    private TableColumn<appointmentData, String> typeColMonth;

    @FXML
    private TableColumn<appointmentData, String> typeColType;

    @FXML
    private ComboBox<String> typeDropDown;

    //All variables and objects used in this class
    String selectedMonth, type;
    int customerID, contactID;
    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
    ObservableList<appointmentData> monthList;
    ObservableList<appointmentData> contactList;
    ObservableList<appointmentData> customerList;
    ObservableList<appointmentData> typeList;

    /**
     * This prompts the user to confirm that they want to clear all data and return to overViewMenu
     * @param event opens the overviewMenu
     */
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

    /**
     * Fills all the tables with all appointments in the database when the reportsMenu is opened.
     * Fills in all the combo boxes with relevant options.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appIDColCustomer.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColCustomer.setCellValueFactory(new PropertyValueFactory<>("title"));
        customerIDColCustomer.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        contactIDColCustomer.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        descriptionColCustomer.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColCustomer.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeColCustomer.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColCustomer.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColCustomer.setCellValueFactory(new PropertyValueFactory<>("end"));

        customerList = appointmentDataSQL.getAppointmentData();
        appointmentByCustomerTable.setItems(customerList);

        appIDColMonth.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColMonth.setCellValueFactory(new PropertyValueFactory<>("title"));
        customerIDColMonth.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        contactIDColMonth.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        descriptionColMonth.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColMonth.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeColMonth.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColMonth.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColMonth.setCellValueFactory(new PropertyValueFactory<>("end"));

        monthList = appointmentDataSQL.getAppointmentData();
        appointmentByMonthTable.setItems(monthList);

        appIDColContact.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColContact.setCellValueFactory(new PropertyValueFactory<>("title"));
        customerIDColContact.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        contactIDColContact.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        descriptionColContact.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColContact.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeColContact.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColContact.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColContact.setCellValueFactory(new PropertyValueFactory<>("end"));

        contactList = appointmentDataSQL.getAppointmentData();
        appointmentByContactTable.setItems(contactList);

        appIDColType.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColType.setCellValueFactory(new PropertyValueFactory<>("title"));
        customerIDColType.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        contactIDColType.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        descriptionColType.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColType.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeColType.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColType.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColType.setCellValueFactory(new PropertyValueFactory<>("end"));

        typeList = appointmentDataSQL.getAppointmentData();
        appointmentByTypeTable.setItems(typeList);

        customerIDDropDown.setItems(customerDataSQL.getCustomerName());
        monthDropDown.getItems().addAll("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
                "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER");
        contactDropDown.setItems(contactsDataSQL.getContactsName());
        typeDropDown.getItems().addAll("Medical", "Walk-in", "Misc");
    }

    /**
     * Pulls the selected customer name in the customerIDDropDown combo box, finds the ID for
     * that customer, creates a new Observables with only appointments linked to that customer ID
     * Refills the table with the new Observable list
     */
    public void filterCustomerTable() {
        customerID = customerDataSQL.getCustomerIDByName(customerIDDropDown.getSelectionModel().getSelectedItem());
        customerList = appointmentDataSQL.getCustomerFilteredAppointments(customerID);
        appointmentByCustomerTable.setItems(customerList);
    }

    /**
     * Pulls the selected month name in the selectedMonth combo box,
     * and creates a new Observables with only appointments with the same month name
     * Refills the table with the new Observable list
     */
    public void filterMonthTable() {
        selectedMonth = monthDropDown.getSelectionModel().getSelectedItem();
        monthList = appointmentDataSQL.getMonthFilteredAppointments(selectedMonth);
        appointmentByMonthTable.setItems(monthList);
    }

    /**
     * Pulls the selected contact name in the contactDropDown combo box, finds the ID for
     * that contact, creates a new Observables with only appointments linked to that contact ID
     * Refills the table with the new Observable list
     */
    public void filterContactTable() {
        contactID = contactsDataSQL.getContactsIDByName(contactDropDown.getSelectionModel().getSelectedItem());
        contactList = appointmentDataSQL.getContactFilteredAppointments(contactID);
        appointmentByContactTable.setItems(contactList);
    }

    /**
     * Pulls the selected type name in the typeDropDown combo box,
     * creates a new Observables with only appointments with the same type
     * Refills the table with the new Observable list
     */
    public void filterTypeTable() {
        type = typeDropDown.getSelectionModel().getSelectedItem();
        typeList = appointmentDataSQL.getTypeFilteredAppointments(type);
        appointmentByTypeTable.setItems(typeList);
    }
}

