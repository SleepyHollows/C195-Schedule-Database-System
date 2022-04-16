package project.c195.helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.c195.model.customerData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class customerDataSQL {
    public static ObservableList<customerData> getCustomerTableData() {
        ObservableList<customerData> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID FROM customers");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                list.add(
                        new customerData(rs.getInt("Customer_ID"),
                                rs.getString("Customer_Name"), rs.getString("Address"),
                                rs.getString("Postal_Code"), rs.getString("Phone"),
                                rs.getInt("Division_ID")));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void customerInsertSQL (
            int customerID,
            int divisionID,
            String name,
            String address,
            String phone,
            String postal

    ) {
        try {
            String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customerID);
            ps.setString(2, name);
            ps.setString(3, address);
            ps.setString(4, postal);
            ps.setString(5, phone);
            ps.setInt(6, divisionID);
            ps.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static int getCustomerID() {
        Random rnd = new Random();
        int customerID = rnd.nextInt(999999);
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT Customer_ID FROM customers");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                if(customerID == rs.getInt("Customer_ID")) {
                    customerID++;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return customerID;
    }
}
