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

        customerList = customerDataSQL.getCustomerData();
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
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("addCustomerMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        Stage overviewStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        overviewStage.setScene(scene);
        overviewStage.show();
    }

    public void openEditCustomerMenu(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("editCustomerMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        Stage overviewStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        overviewStage.setScene(scene);
        overviewStage.show();
    }


    public void openAddAppointmentMenu(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("addAppointmentMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        Stage overviewStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        overviewStage.setScene(scene);
        overviewStage.show();
    }

    public void openEditAppointmentMenu(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("editAppointmentMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        Stage overviewStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        overviewStage.setScene(scene);
        overviewStage.show();
    }

    public void openReportsMenu(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("reportsMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        Stage overviewStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        overviewStage.setScene(scene);
        overviewStage.show();
    }
}
