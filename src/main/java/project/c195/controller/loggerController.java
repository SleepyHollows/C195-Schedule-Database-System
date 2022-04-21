package project.c195.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class loggerController {
    private static final String logFile = "logger.txt";

    /**
     * If the login fails, then we log a failed statement, otherwise we will log a successful statement.
     * @param userName the name used to attempt a login
     * @param authorizedUser the boolean can be true or false based on if the username and password were correct or not
     */
    public static void logLoginAttempt(String userName, boolean authorizedUser) {
        try {
            if (!authorizedUser) {
                BufferedWriter logger = new BufferedWriter(new FileWriter(logFile, true));
                logger.append(String.valueOf(ZonedDateTime.now(ZoneOffset.UTC))).append(" UTC-LOGIN ATTEMPT-USERNAME: ").append(userName).append(" LOGIN FAILED \n");
                logger.flush();
                logger.close();
            }
            else {
                BufferedWriter logger = new BufferedWriter(new FileWriter(logFile, true));
                logger.append(String.valueOf(ZonedDateTime.now(ZoneOffset.UTC))).append(" UTC-LOGIN ATTEMPT-USERNAME: ").append(userName).append(" LOGIN SUCCESSFUL \n");
                logger.flush();
                logger.close();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
