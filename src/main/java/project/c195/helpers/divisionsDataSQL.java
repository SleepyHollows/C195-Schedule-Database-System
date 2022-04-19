package project.c195.helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class divisionsDataSQL {
    public static ObservableList getDivisionID() {
        ObservableList list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT Division_ID FROM first_level_divisions");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getInt("Division_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ObservableList getDivisionName() {
        ObservableList list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT Division FROM first_level_divisions");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("Division"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static int getDivisionIDByName(String divisionName) {
        int divisionID = 0;
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT * FROM first_level_divisions WHERE Division = '" + divisionName + "'");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (divisionName.equals(rs.getString("Division"))) {
                    divisionID = rs.getInt("Division_ID");
                    return divisionID;
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "no division found");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisionID;
    }

    public static String getDivisionNameByID(int divisionID) {
        String divisionName = "";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT Division FROM first_level_divisions WHERE Division_ID = '" + divisionID + "'");
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                divisionName = rs.getString("Division");
            }
            return divisionName;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisionName;
    }

    public static ObservableList getDivisionNameByCountryID(int countryID) {
        ObservableList list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT Division FROM first_level_divisions WHERE Country_ID = '" + countryID + "'");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("Division"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
