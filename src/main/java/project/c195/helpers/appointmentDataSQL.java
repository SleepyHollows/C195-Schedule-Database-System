package project.c195.helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.c195.model.appointmentData;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class appointmentDataSQL {

    /**
     * Creates an Observable list of all appointments in the database
     * @return lost of all appointments
     */
    public static ObservableList<appointmentData> getAppointmentData() {
        ObservableList<appointmentData> list = FXCollections.observableArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, Contact_ID, User_ID FROM appointments");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                        int customerID = rs.getInt("Customer_ID");
                        int appointmentID = rs.getInt("Appointment_ID");
                        int userID = rs.getInt("User_ID");
                        int contactID = rs.getInt("Contact_ID");
                        String title = rs.getString("Title");
                        String description = rs.getString("Description");
                        String location = rs.getString("Location");
                        String type = rs.getString("Type");
                        String starting = String.valueOf(rs.getTimestamp("Start")).substring(0, 19);
                        String ending = String.valueOf(rs.getTimestamp("End")).substring(0, 19);

                LocalDateTime utcStartDT = LocalDateTime.parse(starting, formatter);
                LocalDateTime utcEndDT = LocalDateTime.parse(ending, formatter);

                //convert times UTC zoneId to local zoneId
                ZonedDateTime localZoneStart = utcStartDT.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
                ZonedDateTime localZoneEnd = utcEndDT.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());

                //convert ZonedDateTime to a string for insertion into AppointmentsTableView
                Timestamp localStartDT = Timestamp.valueOf(localZoneStart.format(formatter));
                Timestamp localEndDT = Timestamp.valueOf(localZoneEnd.format(formatter));

                list.add (
                        new appointmentData(customerID, appointmentID, userID, contactID, title, description,
                                location, type, localStartDT, localEndDT)
                );
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Creates a new appointment in the database using all the provided data from the addAppointmentMenuController
     * @param customerID customer ID
     * @param appointmentID appointment ID
     * @param userID user ID
     * @param contactID contact ID
     * @param title appointment title
     * @param description appointment description
     * @param location appointment location
     * @param type appointment type
     * @param start appointment start time
     * @param end appointment end time
     * @param createdBy Who created the appointment
     * @param lastUpdatedBy who last updated the appointment
     */
    public static void appointmentInsertSQL (
            int customerID,
            int appointmentID,
            int userID,
            int contactID,
            String title,
            String description,
            String location,
            String type,
            Timestamp start,
            Timestamp end,
            String createdBy,
            String lastUpdatedBy
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            String sql = "INSERT INTO appointments (" +
                    "Appointment_ID, " +
                    "Title, " +
                    "Description, " +
                    "Location, " +
                    "Type, " +
                    "Start, " +
                    "End, " +
                    "Customer_ID, " +
                    "User_ID, " +
                    "Contact_ID, " +
                    "Create_Date, " +
                    "Created_By, " +
                    "Last_Update, " +
                    "Last_Updated_By) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, appointmentID);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setString(4, location);
            ps.setString(5, type);
            ps.setTimestamp(6, start);
            ps.setTimestamp(7, end);
            ps.setInt(8, customerID);
            ps.setInt(9, userID);
            ps.setInt(10, contactID);
            ps.setString(11, ZonedDateTime.now(ZoneOffset.UTC).format(formatter));
            ps.setString(12, createdBy);
            ps.setString(13, ZonedDateTime.now(ZoneOffset.UTC).format(formatter));
            ps.setString(14, lastUpdatedBy);
            ps.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This allows the appointment ID to be unique. a sudo random number is generated, if it matches any existing ID it
     * will continue to increment by 1
     * @return a random number
     */
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

    /**
     * This deletes a selected appointment from the database using the provided appointment ID
     * @param selectedAppointment appointment ID from the appointmentData object
     */
    public static void appointmentDeleteSQL(int selectedAppointment) {
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("DELETE FROM appointments WHERE Appointment_ID ='" + selectedAppointment + "'");
            ps.executeUpdate();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This deletes all appointments associated with the provided customer ID
     * @param selectedCustomer the customerData object the customer ID is pulled from
     */
    public static void customerAppointmentDeleteSQL(int selectedCustomer) {
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("DELETE FROM appointments WHERE Customer_ID ='" + selectedCustomer + "'");
            ps.executeUpdate();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the existing selected appointment using the data provided from editAppointmentController
     * @param appointmentID appointment ID
     * @param title appointment title
     * @param description appointment desciption
     * @param location appointment location
     * @param type appointment type
     * @param start appointment start time
     * @param end appointment end time
     * @param contactID customer contact ID
     * @param lastUpdatedBy who last updated the appointment
     */
    public static void updateAppointmentSQL(
            int appointmentID,
            String title,
            String description,
            String location,
            String type,
            Timestamp start,
            Timestamp end,
            int contactID,
            String lastUpdatedBy
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("UPDATE appointments SET " +
                    "Title = ?, " +
                    "Description = ?, " +
                    "Location = ?, " +
                    "Type = ?, " +
                    "Start = ?, " +
                    "End = ?, " +
                    "Contact_ID = ?, " +
                    "Last_Update = ?, " +
                    "Last_Updated_By = ? " +
                    "WHERE Appointment_ID = ?");
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, start);
            ps.setTimestamp(6, end);
            ps.setInt(7, contactID);
            ps.setString(8, ZonedDateTime.now(ZoneOffset.UTC).format(formatter));
            ps.setString(9, lastUpdatedBy);
            ps.setInt(10, appointmentID);
            ps.executeUpdate();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This searches for all appointments that match the provided String, allow us to look for a specific appointment
     * @param searchTerm the term looked for
     * @return list of appointments matching the search term
     */
    public static ObservableList<appointmentData> searchAppointmentSQL (String searchTerm) {
        ObservableList<appointmentData> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT * FROM appointments " +
                    "WHERE Appointment_ID = '" + searchTerm +
                    "' OR Title = '" + searchTerm + "' " +
                    "OR Description = '" + searchTerm + "'" +
                    "OR Type = '" + searchTerm + "'");
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

    /**
     * Will search for any appointments that are coming up within 15 minutes of the users DateTime converted to UTC
     * @return a list of upcoming appointment
     */
    public static ObservableList<appointmentData> getAppointmentsIn15Min(){
        ObservableList<appointmentData> allAppointments = FXCollections.observableArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime now15 = now.plusMinutes(15);
        int logonUserID = usersDataSQL.getCurrentUsers().getUserID();

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT * FROM appointments WHERE " +
                    "Start BETWEEN '" + now + "' AND '" + now15 + "' AND User_ID = '" + logonUserID + "'");
            ResultSet rs = ps.executeQuery();


            while(rs.next()) {
                // get data from the returned rows
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                String starting = String.valueOf(rs.getTimestamp("Start")).substring(0, 19);
                String ending = String.valueOf(rs.getTimestamp("End")).substring(0, 19);
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                LocalDateTime utcStartDT = LocalDateTime.parse(starting, formatter);
                LocalDateTime utcEndDT = LocalDateTime.parse(ending, formatter);

                //convert times UTC zoneId to local zoneId
                ZonedDateTime localZoneStart = utcStartDT.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
                ZonedDateTime localZoneEnd = utcEndDT.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());

                //convert ZonedDateTime to a string for insertion into AppointmentsTableView
                Timestamp localStartDT = Timestamp.valueOf(localZoneStart.format(formatter));
                Timestamp localEndDT = Timestamp.valueOf(localZoneEnd.format(formatter));

                appointmentData newAppointment = new appointmentData(
                        customerID, appointmentID, contactID, userID, title, description, location, type, localStartDT, localEndDT
                );
                // Add to the observables list
                allAppointments.add(newAppointment);
            }
            return allAppointments;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return allAppointments;
    }

    /**
     * Gets all appointments for a specific customer using a customer ID
     * @param selectedCustomerID customer ID used to find appointments
     * @return list of appointments
     */
    public static ObservableList<appointmentData> getCustomerFilteredAppointments(int selectedCustomerID) {
        ObservableList<appointmentData> filteredAppointments = null;
        try {
            // Prepare SQL statement
            filteredAppointments = FXCollections.observableArrayList();
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT * FROM appointments WHERE Customer_ID = '" + selectedCustomerID + "'");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // get data from the returned rows
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                // populate into an appointmentData object
                appointmentData newAppointment = new appointmentData(
                        customerID, appointmentID, contactID, userID, title, description, location, type, start, end
                );
                filteredAppointments.add(newAppointment);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return filteredAppointments;
    }

    /**
     * Creates a list of all appointments that match the provided month name
     * @param selectedMonth The name of month filtering by
     * @return list of appointments for a specific month
     */
    public static ObservableList<appointmentData> getMonthFilteredAppointments(String selectedMonth) {
        ObservableList<appointmentData> filteredAppointments = null;
        try {
            // Prepare SQL statement
            filteredAppointments = FXCollections.observableArrayList();
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT * FROM appointments WHERE MONTHNAME(Start) = '" + selectedMonth + "'");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // get data from the returned rows
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                // populate into an appointmentData object
                appointmentData newAppointment = new appointmentData(
                        customerID, appointmentID, contactID, userID, title, description, location, type, start, end
                );
                filteredAppointments.add(newAppointment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filteredAppointments;
    }

    /**
     * Creates a list of all appointments that match the provided contact ID
     * @param selectedContact The ID of contact filtering by
     * @return list of appointments for a specific contact
     */
    public static ObservableList<appointmentData> getContactFilteredAppointments(int selectedContact) {
        ObservableList<appointmentData> filteredAppointments = null;
        try {
            // Prepare SQL statement
            filteredAppointments = FXCollections.observableArrayList();
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT * FROM appointments WHERE Contact_ID = '" + selectedContact + "'");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // get data from the returned rows
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                // populate into an appointmentData object
                appointmentData newAppointment = new appointmentData(
                        customerID, appointmentID, contactID, userID, title, description, location, type, start, end
                );
                filteredAppointments.add(newAppointment);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return filteredAppointments;
    }

    /**
     * Creates a list of all appointments that match the provided type
     * @param selectedType The name of type filtering by
     * @return list of appointments for a specific type
     */
    public static ObservableList<appointmentData> getTypeFilteredAppointments(String selectedType) {
        ObservableList<appointmentData> filteredAppointments = null;
        try {
            // Prepare SQL statement
            filteredAppointments = FXCollections.observableArrayList();
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT * FROM appointments WHERE Type = '" + selectedType + "'");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // get data from the returned rows
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                // populate into an appointmentData object
                appointmentData newAppointment = new appointmentData(
                        customerID, appointmentID, contactID, userID, title, description, location, type, start, end
                );
                filteredAppointments.add(newAppointment);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return filteredAppointments;
    }

    /**
     * Gets the user's current zonedDateTime in a range from current day and 1 week ahead.
     * Offsets the current zoned date/time to that of UTC
     * Creates a new Observable list of appointments in that time range and updates the table with those appointments.
     * @return a list appointments in a range of week
     */
    public static ObservableList<appointmentData> getWeekFilterAppointments() {
        ObservableList<appointmentData> filteredAppointments = FXCollections.observableArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        ZonedDateTime startRangeMarker = ZonedDateTime.now(usersDataSQL.getUserTimeZone());
        ZonedDateTime endRangeMarker = startRangeMarker.plusWeeks(1);
        ZonedDateTime startRange = startRangeMarker.withZoneSameInstant(ZoneOffset.UTC);
        ZonedDateTime endRange = endRangeMarker.withZoneSameInstant(ZoneOffset.UTC);
        String startRangeString = startRange.format(formatter);
        String endRangeString = endRange.format(formatter);

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT * FROM appointments as a LEFT OUTER JOIN contacts as c ON a.Contact_ID = c.Contact_ID WHERE" +
                    " Start between '" + startRangeString + "' AND '" + endRangeString + "'");
            ResultSet rs = ps.executeQuery();

            while( rs.next() ) {
                // get data from the returned rows
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                // populate into an appointmentData object
                appointmentData newAppointment = new appointmentData(
                        customerID, appointmentID, contactID, userID, title, description, location, type, start, end
                );
                filteredAppointments.add(newAppointment);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return filteredAppointments;
    }

    /**
     * The SQL statements is looking for any appointments that are in range of the selected datetime of the current
     * appointment in question, and will not look at any appointments with the same appointment ID.
     * @param startTime start time selected
     * @param endTime end time selected
     * @param thisAppointmentID appointment ID of the current appointment being added/updated
     * @return a true or false boolean stating if there are any conflicts in the database
     */
    public static boolean checkForOverlap(LocalDateTime startTime, LocalDateTime endTime, int thisAppointmentID) throws SQLException {
        // Prepare SQL statement
        PreparedStatement ps =  JDBC.connection.prepareStatement(
                "SELECT * FROM appointments "
                        + "WHERE (? BETWEEN Start AND End OR ? BETWEEN Start AND End OR ? < Start AND ? > End) "
                        + "AND (Appointment_ID != ?)");

        ps.setTimestamp(1, Timestamp.valueOf(startTime));
        ps.setTimestamp(2, Timestamp.valueOf(endTime));
        ps.setTimestamp(3, Timestamp.valueOf(startTime));
        ps.setTimestamp(4, Timestamp.valueOf(endTime));
        ps.setInt(5, thisAppointmentID);
        ResultSet rs = ps.executeQuery();

        return !rs.next();
    }


}