package project.c195.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import project.c195.helpers.appointmentDataSQL;
import project.c195.helpers.customerDataSQL;
import project.c195.model.appointmentData;
import project.c195.model.customerData;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class overviewMenuController implements Initializable {

    @FXML
    private TableView<customerData> customersTable;
    @FXML
    private TableColumn<customerData, Integer> customerIDCol;
    @FXML
    private TableColumn<customerData, String> customerNameCol;
    @FXML
    private TableColumn<customerData, String> customerAddressCol;
    @FXML
    private TableColumn<customerData, String> customerPostCol;
    @FXML
    private TableColumn<customerData, String> customerPhoneCol;
    @FXML
    private TableColumn<customerData, Integer> customerDivIDCol;
    @FXML
    private Button addCustomerBtn;
    @FXML
    private Button updateCustomerBtn;
    @FXML
    private Button deleteCustomerBtn;

    @FXML
    private RadioButton viewMonthRadioBtn;
    @FXML
    private RadioButton viewAllRadioBtn;
    @FXML
    private RadioButton viewCustomerRadioBtn;
    @FXML
    private RadioButton viewWeekRadioBtn;
    @FXML
    private TextField searchTextField;
    @FXML
    private Button searchBtn;
    @FXML
    private TableView<appointmentData> appointmentTable;
    @FXML
    private TableColumn<appointmentData, Integer> appointmentAppIDCol;
    @FXML
    private TableColumn<appointmentData, String> appointmentTitleCol;
    @FXML
    private TableColumn<appointmentData, Integer> appointmentCustomerIDCol;
    @FXML
    private TableColumn<appointmentData, Integer> appointmentContactIDCol;
    @FXML
    private TableColumn<appointmentData, String> appointmentDescriptionCol;
    @FXML
    private TableColumn<appointmentData, String> appointmentLocationCol;
    @FXML
    private TableColumn<appointmentData, String> appointmentTypeCol;
    @FXML
    private TableColumn<appointmentData, String> appointmentStartCol;
    @FXML
    private TableColumn<appointmentData, String> appointmentEndCol;
    @FXML
    private Button addAppointmentBtn;
    @FXML
    private Button updateAppointmentBtn;
    @FXML
    private Button deleteAppointmentBtn;

    ObservableList<customerData> customerList;
    ObservableList<appointmentData> appointmentList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerIDCol.setCellValueFactory(new PropertyValueFactory<customerData, Integer>("id"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<customerData, String>("name"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<customerData, String>("address"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<customerData, String>("phone"));
        customerPostCol.setCellValueFactory(new PropertyValueFactory<customerData, String>("postalCode"));
        customerDivIDCol.setCellValueFactory(new PropertyValueFactory<customerData, Integer>("divID"));

        customerList = customerDataSQL.getCustomerTableData();
        customersTable.setItems(customerList);

        appointmentAppIDCol.setCellValueFactory(new PropertyValueFactory<appointmentData, Integer>("appointmentID"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<appointmentData, String>("title"));
        appointmentCustomerIDCol.setCellValueFactory(new PropertyValueFactory<appointmentData, Integer>("customerID"));
        appointmentContactIDCol.setCellValueFactory(new PropertyValueFactory<appointmentData, Integer>("contactID"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<appointmentData, String>("description"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<appointmentData, String>("location"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<appointmentData, String>("type"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<appointmentData, String>("start"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<appointmentData, String>("end"));

        appointmentList = appointmentDataSQL.getAppointmentData();
        appointmentTable.setItems(appointmentList);
    }

    public void openAddCustomerMenu(ActionEvent event) throws IOException {
        sceneController.switchScreen(event, "/project/c195/addCustomerMenu.fxml");
    }

    public void openEditCustomerMenu(ActionEvent event) throws IOException {
        sceneController.switchScreen(event, "/project/c195/editCustomerMenu.fxml");
    }


    public void openAddAppointmentMenu(ActionEvent event) throws IOException {
        sceneController.switchScreen(event, "/project/c195/addAppointmentMenu.fxml");
    }

    public void openEditAppointmentMenu(ActionEvent event) throws IOException {
        sceneController.switchScreen(event, "/project/c195/editAppointmentMenu.fxml");
    }

    public void openReportsMenu(ActionEvent event) throws IOException {
        sceneController.switchScreen(event, "/project/c195/reportsMenu.fxml");
    }

    public void deleteAppointment(ActionEvent event) throws IOException {
        appointmentData selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        appointmentDataSQL.appointmentDeleteSQL(selectedAppointment.getAppointmentID());
        sceneController.switchScreen(event, "overviewMenu.fxml");
    }

    public void deleteCustomer(ActionEvent event) throws IOException {
        customerData selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        appointmentDataSQL.customerAppointmentDeleteSQL(selectedCustomer.getId());
        customerDataSQL.customerDeleteSQL(selectedCustomer.getId());
        sceneController.switchScreen(event, "overviewMenu.fxml");
    }
}
