package project.c195.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import project.c195.Main;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class overviewMenuController implements Initializable {

    @FXML
    private TableView customersTable;
    @FXML
    private TableColumn customerIDCol;
    @FXML
    private TableColumn customerNameCol;
    @FXML
    private TableColumn customerAddressCol;
    @FXML
    private TableColumn customerPostCol;
    @FXML
    private TableColumn customerPhoneCol;
    @FXML
    private TableColumn customerDivIDCol;
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
    private TableView appointmentTable;
    @FXML
    private TableColumn appIDCol;
    @FXML
    private TableColumn titleCol;
    @FXML
    private TableColumn appointmentCustomerIDCol;
    @FXML
    private TableColumn appointmentContactIDCol;
    @FXML
    private TableColumn appointmentDescriptionCol;
    @FXML
    private TableColumn appointmentLocationCol;
    @FXML
    private TableColumn appointmentTypeCol;
    @FXML
    private TableColumn appointmentStartCol;
    @FXML
    private TableColumn appointmentEndCol;
    @FXML
    private Button addAppointmentBtn;
    @FXML
    private Button updateAppointmentBtn;
    @FXML
    private Button deleteAppointmentBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public static void openAddCustomerMenu(ActionEvent event) throws IOException {
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
