package project.c195.helpers;

import project.c195.model.usersData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.ZoneId;
import java.util.Locale;

public class usersDataSQL {

    //user data
    private static usersData currentUsers;
    private static Locale userLocale;
    private static ZoneId userTimeZone;

    /**
     * Attempts to log in to the database using the provided username and password. All users are checked for matching
     * username and password.
     * UserLocale and userTimeZone are assigned to a system default ZoneID
     * @param userName provided username
     * @param password provided password
     * @return boolean confirming a good username and password combination
     */
    public static boolean loginAttempt(String userName, String password) {
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT * FROM users WHERE User_Name='" + userName + "' AND Password='" + password + "'");
            ResultSet rs = ps.executeQuery();

            if(!rs.next())  {
                return false;
            }
            else {
                currentUsers = new usersData(rs.getString("User_Name"), rs.getInt("User_ID"));
                userLocale = Locale.getDefault();
                userTimeZone = ZoneId.systemDefault();
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static Locale getUserLocale() { return userLocale; }

    public static ZoneId getUserTimeZone() { return userTimeZone; }

    public static usersData getCurrentUsers() { return currentUsers; }
}
