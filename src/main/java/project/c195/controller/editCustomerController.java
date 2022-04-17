package project.c195.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import project.c195.helpers.customerDataSQL;
import project.c195.helpers.divisionsDataSQL;
import project.c195.model.customerData;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class editCustomerController implements Initializable {

    @FXML
    private TextField addressBox;

    @FXML
    private ComboBox<String> countryDropDown;

    @FXML
    private TextField customerIDBox;

    @FXML
    private TextField customerNameBox;

    @FXML
    private TextField phoneBox;

    @FXML
    private TextField postalBox;

    @FXML
    private ComboBox<String> stateDropDown;

    String address;
    String name;
    String postal;
    String phone;
    int divisionID;
    int customerID;

    public void openOverviewMenu(ActionEvent event) throws IOException {
        sceneController.switchScreen(event, "overviewMenu.fxml");
    }

    public void updateCustomer(ActionEvent event) throws IOException {
        address = addressBox.getText();
        name = customerNameBox.getText();
        postal = postalBox.getText();
        phone = phoneBox.getText();
        divisionID = divisionsDataSQL.getDivisionIDByName(String.valueOf(stateDropDown.getSelectionModel().getSelectedItem()));
        customerID = Integer.parseInt(customerIDBox.getText());

        customerDataSQL.updateCustomerSQL(name, address, postal, phone, divisionID, customerID);

        sceneController.switchScreen(event, "overviewMenu.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stateDropDown.setItems(divisionsDataSQL.getDivisionName());
    }
    public void setData(customerData selectedCustomer) {
        customerIDBox.setText(String.valueOf(selectedCustomer.getId()));
        customerNameBox.setText(selectedCustomer.getName());
        addressBox.setText(selectedCustomer.getAddress());
        postalBox.setText(selectedCustomer.getPostalCode());
        phoneBox.setText(selectedCustomer.getPhone());
        stateDropDown.setValue(divisionsDataSQL.getDivisionNameByID(selectedCustomer.getDivID()));
    }
}
