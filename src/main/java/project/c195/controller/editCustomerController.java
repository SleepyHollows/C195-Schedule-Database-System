package project.c195.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import project.c195.helpers.countriesDataSQL;
import project.c195.helpers.customerDataSQL;
import project.c195.helpers.divisionsDataSQL;
import project.c195.helpers.usersDataSQL;
import project.c195.model.customerData;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class editCustomerController implements Initializable {

    @FXML
    private TextField addressTextBox;

    @FXML
    private ComboBox<String> countryDropDown;

    @FXML
    private TextField idTextBox;

    @FXML
    private TextField nameTextBox;

    @FXML
    private TextField phoneTextBox;

    @FXML
    private TextField postalTextBox;

    @FXML
    private ComboBox<String> stateDropDown;

    Alert alert;
    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
    String name, address, postal, phone, updatedBy;
    int customerID, divisionID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            countryDropDown.setItems(countriesDataSQL.getCountryName());
            stateDropDown.setItems(divisionsDataSQL.getDivisionName());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDivisions() {
        stateDropDown.setItems(divisionsDataSQL.getDivisionNameByCountryID(countriesDataSQL.getCountryIDByName(countryDropDown.getSelectionModel().getSelectedItem())));
    }

    public void openOverviewMenu(ActionEvent event) {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel customer update");
        alert.setHeaderText("Are you sure you want to cancel, all changes made will be lost");
        alert.setContentText("Cancel?");
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

    public void updateCustomer(ActionEvent event) {
        try {
            name = nameTextBox.getText();
            address = addressTextBox.getText();
            postal = postalTextBox.getText();
            phone = phoneTextBox.getText();
            updatedBy = usersDataSQL.getCurrentUsers().getUsername();
            customerID = Integer.parseInt(idTextBox.getText());
            divisionID = divisionsDataSQL.getDivisionIDByName(String.valueOf(stateDropDown.getSelectionModel().getSelectedItem()));
            System.out.println(updatedBy);
            if (name.isBlank() || address.isBlank() || postal.isBlank() || phone.isBlank()
                    || countryDropDown.getValue() == null || stateDropDown.getValue() == null) {
                alert = new Alert(Alert.AlertType.ERROR, "All boxes must be filled");
                alert.showAndWait();
            }
            else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Update customer");
                alert.setHeaderText("Are you sure you want to update this customer?");
                alert.setContentText("Update?");
                alert.getButtonTypes().setAll(yes, no);
                alert.showAndWait().ifPresent(button -> {
                    if (button == yes) {
                        try {
                            customerDataSQL.updateCustomerSQL(customerID, name, address, postal, phone, divisionID, updatedBy);
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
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void setData(customerData selectedCustomer) {
        idTextBox.setText(String.valueOf(selectedCustomer.getId()));
        nameTextBox.setText(selectedCustomer.getName());
        addressTextBox.setText(selectedCustomer.getAddress());
        postalTextBox.setText(selectedCustomer.getPostalCode());
        phoneTextBox.setText(selectedCustomer.getPhone());
        stateDropDown.setValue(divisionsDataSQL.getDivisionNameByID(selectedCustomer.getDivID()));
        countryDropDown.setValue(countriesDataSQL.getCountryNameByID(divisionsDataSQL.getDivisionCountryIDByName(stateDropDown.getSelectionModel().getSelectedItem())));
    }
}
