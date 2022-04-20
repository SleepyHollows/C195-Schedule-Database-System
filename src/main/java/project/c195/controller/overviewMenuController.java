package project.c195.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import project.c195.helpers.appointmentDataSQL;
import project.c195.helpers.customerDataSQL;
import project.c195.model.appointmentData;
import project.c195.model.customerData;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class overviewMenuController implements Initializable {

    @FXML
    private Button addAppointmentBtn;

    @FXML
    private ToggleGroup radioButton;

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
    private Button updateCustomerBtn;

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
    private Button logoutBtn;

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
    private Button updateAppointmentBtn;

    ObservableList<customerData> customerList;
    ObservableList<appointmentData> appointmentList;
    Alert alert;
    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerPostCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerDivIDCol.setCellValueFactory(new PropertyValueFactory<>("divID"));

        customerList = customerDataSQL.getCustomerTableData();
        customersTable.setItems(customerList);

        appointmentAppIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appointmentContactIDCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));

        appointmentList = appointmentDataSQL.getAppointmentData();
        appointmentTable.setItems(appointmentList);
    }

    public void openAddCustomerMenu(ActionEvent event) throws IOException {
        sceneController.switchScreen(event, "/project/c195/addCustomerMenu.fxml");
    }

    public void openEditCustomerMenu() throws IOException {
        customerData selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        if(selectedCustomer == null){
            alert = new Alert(Alert.AlertType.ERROR, "No customer was selected, please selected a customer and try again");
            alert.showAndWait();
        }
        else {
            Stage stage = (Stage) updateCustomerBtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/c195/editCustomerMenu.fxml"));
            Parent root = loader.load();
            editCustomerController controller = loader.getController();
            controller.setData(selectedCustomer);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void openAddAppointmentMenu() throws IOException {
        customerData selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        if(selectedCustomer == null){
            alert = new Alert(Alert.AlertType.ERROR, "No customer was selected, please selected a customer and try again");
            alert.showAndWait();
        }
        else {
            Stage stage = (Stage) addAppointmentBtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/c195/addAppointmentMenu.fxml"));
            Parent root = loader.load();
            addAppointmentController controller = loader.getController();
            controller.setCustomerID(selectedCustomer);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    public void openEditAppointmentMenu() throws IOException {
        appointmentData selectedAppointment  = appointmentTable.getSelectionModel().getSelectedItem();
        if(selectedAppointment == null){
            alert = new Alert(Alert.AlertType.ERROR, "No appointment was selected, please selected an appointment and try again");
            alert.showAndWait();
        }
        else {
            Stage stage = (Stage) updateAppointmentBtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/c195/editAppointmentMenu.fxml"));
            Parent root = loader.load();
            editAppointmentController controller = loader.getController();
            controller.setData(selectedAppointment);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    public void openReportsMenu(ActionEvent event) throws IOException {
        sceneController.switchScreen(event, "/project/c195/reportsMenu.fxml");
    }

    public void deleteAppointment(ActionEvent event) {
        appointmentData selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        if(selectedAppointment == null) {
            alert = new Alert(Alert.AlertType.ERROR, "No appointment was selected, please selected an appointment and try again");
            alert.showAndWait();
        }
        else {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete appointment");
            alert.setHeaderText("Are you sure you want to delete this appointment, all data will be lost?");
            alert.setContentText("Delete?");
            alert.getButtonTypes().setAll(yes, no);
            alert.showAndWait().ifPresent(button -> {
                if (button == yes) {
                    try {
                        appointmentDataSQL.appointmentDeleteSQL(selectedAppointment.getAppointmentID());
                        sceneController.switchScreen(event, "overviewMenu.fxml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (button == no) {
                    alert.close();
                }
            });
        }

    }

    public void deleteCustomer(ActionEvent event) {
        customerData selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        if(selectedCustomer == null) {
            alert = new Alert(Alert.AlertType.ERROR, "No customer was selected, please selected a customer and try again");
            alert.showAndWait();
        }
        else {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete customer");
            alert.setHeaderText("Are you sure you want to delete this customer, all data will be lost including appointments?");
            alert.setContentText("Delete?");
            alert.getButtonTypes().setAll(yes, no);
            alert.showAndWait().ifPresent(button -> {
                if (button == yes) {
                    try {
                        appointmentDataSQL.customerAppointmentDeleteSQL(selectedCustomer.getId());
                        customerDataSQL.customerDeleteSQL(selectedCustomer.getId());
                        sceneController.switchScreen(event, "overviewMenu.fxml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (button == no) {
                    alert.close();
                }
            });
        }

    }

    public void searchAppointments() {
        if(searchTextField.getText().equals("")) {
            appointmentList = appointmentDataSQL.getAppointmentData();
            appointmentTable.setItems(appointmentList);
        }
        else {
            appointmentList = appointmentDataSQL.searchAppointmentSQL(searchTextField.getText());
            appointmentTable.setItems(appointmentList);
            searchTextField.setText("");
        }
    }

    public void openLoginMenu(ActionEvent event) {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout of account");
        alert.setHeaderText("Are you sure you want to logout?");
        alert.setContentText("Logout?");
        alert.getButtonTypes().setAll(yes, no);
        alert.showAndWait().ifPresent(type -> {
            if(type == yes) {
                try {
                    sceneController.switchScreen(event, "loginMenu.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(type == no) {
                alert.close();
            }
        });
    }

    public void radioButtons() {
        if (viewCustomerRadioBtn.isSelected()) {

        }
    }
}
