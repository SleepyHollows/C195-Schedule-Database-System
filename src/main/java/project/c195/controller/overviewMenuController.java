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
    private TableColumn<appointmentData, Integer> appointmentUserIDCol;

    @FXML
    private ComboBox<String> miscDropDown;

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

    //All the used variables and objects in the class
    ObservableList<customerData> customerList;
    ObservableList<appointmentData> appointmentList;
    Alert alert;
    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
    String selectedMonth, alertAppointmentType, alertCustomerName;
    int alertAppointmentID, alertCustomerID;

    /**
     * Fills both tables on the overviewMenu with the list of customers and appointments in the database
     */
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
        appointmentUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));

        appointmentList = appointmentDataSQL.getAppointmentData();
        appointmentTable.setItems(appointmentList);
    }

    /**
     * Allows the user to open addCustomerMenu to add a new customer
     * @param event opens addCustomerMenu
     * @throws IOException catches the exceptions thrown by the scene controller
     */
    public void openAddCustomerMenu(ActionEvent event) throws IOException {
        sceneController.switchScreen(event, "/project/c195/addCustomerMenu.fxml");
    }

    /**
     * Gets the data from the selected customer and assigns it to a new object "selectedCustomer" of the customerData
     * class. If selectedCustomer is null (no customer was selected) then it will throw an alert to the user to let them
     * know.
     * It sends the selectedCustomer data over to the new scenes' controller class where the data is plug into the method
     * "setData" which fills in the boxes on that screen with selectedCustomer's data.
     * @throws IOException catches the exceptions thrown by the scene controller
     */
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

    /**
     * Gets the data from the selected customer and assigns it to a new object "selectedCustomer" of the customerData
     * class. If selectedCustomer is null (no customer was selected) then it will throw an alert to the user to let them
     * know.
     * It sends the selectedCustomer data over to the new scenes' controller class where the data is plug into the method
     * "setData" which fills in the boxes on that screen with selectedCustomer's data.
     * @throws IOException catches the exceptions thrown by the scene controller
     */
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

    /**
     * Gets the data from the selected appointment and assigns it to a new object "selectedAppointment" of the
     * appointmentData class. If selectedCustomer is null (no customer was selected) then it will throw an alert to the
     * user to let them know.
     * It sends the selectedAppointment data over to the new scenes' controller class where the data is plug into the
     * method "setData" which fills in the boxes on that screen with selectedAppointments' data.
     * @throws IOException catches the exceptions thrown by the scene controller
     */
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

    /**
     * Allows the user to open reportsMenu to view multiple reports
     * @param event opens reportMenu
     * @throws IOException catches the exceptions thrown by the scene controller
     */
    public void openReportsMenu(ActionEvent event) throws IOException {
        sceneController.switchScreen(event, "/project/c195/reportsMenu.fxml");
    }

    /**
     * Gets the data from the selected appointment and assigns it to a new object "selectedAppointment" of the
     * appointmentData class. If selectedAppointment is null (no customer was selected) then it will throw an alert to
     * the user to let them know.
     * An alert confirms with the user that they want to delete the selected appointment before deletion initiates.
     * selectedAppointments' data is sent to the method "appointmentDeleteSQL" in appointmentDataSQl where it is removed
     * from the database.
     * The event then re-opens the overviewMenu to update the overviewMenu and reflects the deletion.
     * @param event re-opens overviewMenu
     */
    public void deleteAppointment(ActionEvent event) {
        appointmentData selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        if(selectedAppointment == null) {
            alert = new Alert(Alert.AlertType.ERROR, "No appointment was selected, please selected an appointment and try again");
            alert.showAndWait();
        }
        else {
            alertAppointmentID = selectedAppointment.getAppointmentID();
            alertAppointmentType = selectedAppointment.getType();
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete appointment");
            alert.setHeaderText("Are you sure you want to delete this appointment, all data will be lost?" +
                    "\n Appointment ID: " + alertAppointmentID + "\n Appointment Type: " + alertAppointmentType);
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

    /**
     * Gets the data from the selected customer and assigns it to a new object "selectedCustomer" of the
     * customerData class. If selectedCustomer is null (no customer was selected) then it will throw an alert to the
     * user to let them know.
     * An alert confirms with the user that they want to delete the selected customer before deletion initiates.
     * The database restricts us to deleting appointments associated with the customer before we can delete the customer
     * so first the customer ID is sent to "customerAppointmentDeleteSQL" where it will delete any appointments with
     * the customer ID attached to it.
     * selectedCustomers' data is sent to the method "customerDeleteSQL" in customerDataSQl where it is removed
     * from the database.
     * The event then re-opens the overviewMenu to update the overviewMenu and reflects the deletion.
     * @param event re-opens overviewMenu
     */
    public void deleteCustomer(ActionEvent event) {
        customerData selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        if(selectedCustomer == null) {
            alert = new Alert(Alert.AlertType.ERROR, "No customer was selected, please selected a customer and try again");
            alert.showAndWait();
        }
        else {
            alertCustomerName = selectedCustomer.getName();
            alertCustomerID = selectedCustomer.getId();
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete customer");
            alert.setHeaderText("Are you sure you want to delete this customer, all data will be lost including appointments?\n" +
                    "Customer Name: " + alertCustomerName + "\nCustomer ID: " + alertCustomerID);
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

    /**
     * This search looks for any matching appointment ID, title, type, or description and updates the appointment list
     * with the found appointments with matching terms. If the search box is blank then it will just update the
     * appointment list with all the appointments in the database.
     */
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

    /**
     * Allows the user to log out and return to the loginMenu
     * @param event opens loginMenu
     */
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

    /**
     * Sets all the data in the appointment table to be all appointments in the database
     * Sets the miscDropDown to false so that it can't be clicked on
     */
    public void viewAllRadio() {
        appointmentList = appointmentDataSQL.getAppointmentData();
        appointmentTable.setItems(appointmentList);
        miscDropDown.setVisible(false);
    }

    /**
     * Sets the value in the miscDropDown to "" to reset month selected when switched back to this option
     * Sets the miscDropDown to visible, so you can view all appointments in each month
     * fills the miscDropDown with required data to pick months
     */
    public void viewMonthRadio() {
        miscDropDown.setValue("");
        miscDropDown.setVisible(true);
        miscDropDown.getItems().addAll("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
                "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER");
    }

    /**
     * Sets the miscDropDown to false so that it can't be clicked on
     * Calls the filterByWeek method to update the table
     */
    public void viewWeekRadio() {
        miscDropDown.setVisible(false);
        filterByWeek();
    }

    /**
     * Calls the getMonthFilteredAppointments method where it will build an Observable list that will be used to
     * populate the appointmentTable
     */
    public void filterByMonth() {
        selectedMonth = miscDropDown.getSelectionModel().getSelectedItem();
        appointmentList = appointmentDataSQL.getMonthFilteredAppointments(selectedMonth);
        appointmentTable.setItems(appointmentList);
    }

    /**
     * Calls the getWeekFilterAppointments method that and assigns the return Observable list to appointmentList
     * the table is then updated with the new Observable list.
     */
    public void filterByWeek() {
        miscDropDown.setVisible(false);
        appointmentList = appointmentDataSQL.getWeekFilterAppointments();
        appointmentTable.setItems(appointmentList);
    }
}
