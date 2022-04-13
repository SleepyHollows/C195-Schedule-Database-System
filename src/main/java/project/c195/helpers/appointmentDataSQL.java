package project.c195.helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.c195.model.appointmentData;
import project.c195.model.customerData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class appointmentDataSQL {
    public static ObservableList<appointmentData> getAppointmentData() {
        ObservableList<appointmentData> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, Contact_ID FROM appointments");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                list.add (
                        new appointmentData(rs.getInt("Customer_ID"), rs.getInt("Appointment_ID"),
                                rs.getInt("Contact_ID"), rs.getString("Title"),
                                rs.getString("Description"), rs.getString("Location"),
                                rs.getString("Type"), rs.getString("Start"), rs.getString("End"))
                );
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void appointmentInsertSQL(
            int customerID,
            int appointmentID,
            int contactID,
            int userID, String title,
            String description,
            String location,
            String type,
            String start,
            String end
    ) {
        try {
            String sql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, " +
                    "User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
}
