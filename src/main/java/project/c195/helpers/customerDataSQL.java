package project.c195.helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.c195.model.customerData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class customerDataSQL {

    /**
     * Pulls the required data for the customerData class, it then makes a list of all customers in the database
     * @return a list of all customerData objects
     */
    public static ObservableList<customerData> getCustomerTableData() {
        ObservableList<customerData> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID FROM customers");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                list.add(
                        new customerData(
                                rs.getInt("Customer_ID"),
                                rs.getString("Customer_Name"),
                                rs.getString("Address"),
                                rs.getString("Postal_Code"),
                                rs.getString("Phone"),
                                rs.getInt("Division_ID")
                        )
                );
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * This method inserts the new customer into the database with all the data that the customer table holds
     * The creation DateTime and Update DateTIme are pulled from the current users ZoneID converted to UTC DateTime
     * @param customerID customer ID
     * @param name customer Name
     * @param address Customer address
     * @param postal Customer Postal Code
     * @param phone Customer phone number
     * @param divisionID Customer Division ID
     * @param createdBy The day the customer was created
     * @param lastUpdatedBy The last day the customer was updated
     */
    public static void customerInsertSQL (
            int customerID,
            String name,
            String address,
            String postal,
            String phone,
            int divisionID,
            String createdBy,
            String lastUpdatedBy
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone,  Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customerID);
            ps.setString(2, name);
            ps.setString(3, address);
            ps.setString(4, postal);
            ps.setString(5, phone);
            ps.setInt(6, divisionID);
            ps.setString(7, ZonedDateTime.now(ZoneOffset.UTC).format(formatter));
            ps.setString(8, createdBy);
            ps.setString(9, ZonedDateTime.now(ZoneOffset.UTC).format(formatter));
            ps.setString(10, lastUpdatedBy);
            ps.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This allows the customer ID to be unique. a sudo random number is generated, if it matches any existing ID it
     * will continue to increment by 1
     * @return a random number
     */
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

    /**
     * This deletes the selected customer from the database using a provided customer ID from the selectedCustomer
     * object.
     * @param selectedCustomer the data of the customer selected in the overviewMenu
     */
    public static void customerDeleteSQL(int selectedCustomer) {
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("DELETE FROM customers WHERE Customer_ID ='" + selectedCustomer + "'");
            ps.executeUpdate();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This sends an update SQL statement to the database where the selected customer's data will be updated with the
     * provided data from the editCustomerMenu.
     * the last updated DateTime is pulled from the current users ZoneID converted to UTC DateTime
     * @param customerID customer ID
     * @param name customer Name
     * @param address Customer address
     * @param postal Customer Postal Code
     * @param phone Customer phone number
     * @param divisionID Customer Division ID
     * @param lastUpdatedBy The last day the customer was updated
     */
    public static void updateCustomerSQL(
            int customerID,
            String name,
            String address,
            String postal,
            String phone,
            int divisionID,
            String lastUpdatedBy
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("UPDATE customers SET " +
                    "Customer_Name = ?, " +
                    "Address = ?, " +
                    "Postal_Code = ?, " +
                    "Phone = ?, " +
                    "Division_ID = ?, " +
                    "Last_Update = ?, " +
                    "Last_Updated_By = ? " +
                    "WHERE Customer_ID = '" + customerID + "'");
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postal);
            ps.setString(4, phone);
            ps.setInt(5, divisionID);
            ps.setString(6, ZonedDateTime.now(ZoneOffset.UTC).format(formatter));
            ps.setString(7, lastUpdatedBy);
            ps.executeUpdate();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get a specific customer ID using only a provided name
     * @param customerName name used to find ID
     * @return a customer ID
     */
    public static int getCustomerIDByName(String customerName) {
        int customerID = 0;
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT Customer_ID FROM customers WHERE " +
                    "Customer_Name = '" + customerName + "'");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                customerID = rs.getInt("Customer_ID");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return customerID;
    }

    /**
     * Creates an Observable list of all customer names in the database
     * @return a list of all customer names in the database
     */
    public static ObservableList<String> getCustomerName() {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT Customer_Name FROM customers");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                list.add(rs.getString("Customer_Name"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
