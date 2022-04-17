package project.c195.helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.c195.model.appointmentData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class appointmentDataSQL {
    public static ObservableList<appointmentData> getAppointmentData() {
        ObservableList<appointmentData> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, Contact_ID, User_ID FROM appointments");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                list.add (
                        new appointmentData(
                                rs.getInt("Customer_ID"),
                                rs.getInt("Appointment_ID"),
                                rs.getInt("User_ID"),
                                rs.getInt("Contact_ID"),
                                rs.getString("Title"),
                                rs.getString("Description"),
                                rs.getString("Location"),
                                rs.getString("Type"),
                                rs.getTimestamp("Start"),
                                rs.getTimestamp("End"))
                );
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void appointmentInsertSQL (
            int customerID,
            int appointmentID,
            int userID,
            int contactID,
            String title,
            String description,
            String location,
            String type,
            String start,
            String end
    ) {
        try {
            String sql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, appointmentID);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setString(4, location);
            ps.setString(5, type);
            ps.setString(6, start);
            ps.setString(7, end);
            ps.setInt(8, customerID);
            ps.setInt(9, userID);
            ps.setInt(10, contactID);
            ps.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static int getAppointmentID() {
        Random rnd = new Random();
        int appointmentID = rnd.nextInt(999999);
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT Appointment_ID FROM appointments");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                if(appointmentID == rs.getInt("Appointment_ID")) {
                    appointmentID++;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return appointmentID;
    }

    public static void appointmentDeleteSQL(int selectedAppointment) {
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("DELETE FROM appointments WHERE Appointment_ID ='" + selectedAppointment + "'");
            ps.executeUpdate();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void customerAppointmentDeleteSQL(int selectedCustomer) {
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("DELETE FROM appointments WHERE Customer_ID ='" + selectedCustomer + "'");
            ps.executeUpdate();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateAppointmentSQL(
            String title,
            String description,
            String location,
            String type,
            String start,
            String end,
            int contactID,
            int appointmentID
    ) {
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("UPDATE appointments SET " +
                    "Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, " +
                            "Contact_ID = ? WHERE Appointment_ID = '" + appointmentID + "'");
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setString(5, start);
            ps.setString(6, end);
            ps.setInt(7, contactID);
            ps.executeUpdate();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<appointmentData> searchAppointmentSQL (String searchTerm) {
        ObservableList<appointmentData> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT * FROM appointments " +
                    "WHERE Appointment_ID = '" + searchTerm +
                    "' OR Title = '" + searchTerm + "' " +
                    "OR Description = '" + searchTerm + "'");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                list.add (
                        new appointmentData(
                                rs.getInt("Customer_ID"),
                                rs.getInt("Appointment_ID"),
                                rs.getInt("User_ID"),
                                rs.getInt("Contact_ID"),
                                rs.getString("Title"),
                                rs.getString("Description"),
                                rs.getString("Location"),
                                rs.getString("Type"),
                                rs.getTimestamp("Start"),
                                rs.getTimestamp("End"))
                );
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
