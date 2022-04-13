package project.c195.helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.c195.model.appointmentData;
import project.c195.model.customerData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
}
