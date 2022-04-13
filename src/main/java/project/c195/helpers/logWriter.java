package project.c195.helpers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class logWriter {
    private static final String fileName = ("logger.txt");
    static FileWriter fileWriter;
    static BufferedWriter bufferedWriter;
    static PrintWriter printWriter;

    public static String getTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static void successfulLoginLog(String userName) {
        try {
            fileWriter = new FileWriter(fileName);
            bufferedWriter =  new BufferedWriter(fileWriter);
            printWriter = new PrintWriter(bufferedWriter);

            printWriter.println(userName + " successfully logged in at: " + getTime());

            printWriter.flush();

            printWriter.close();
            fileWriter.close();
            bufferedWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void failedLoginLog(String userName) {
        try {
            fileWriter = new FileWriter(fileName);
            bufferedWriter =  new BufferedWriter(fileWriter);
            printWriter = new PrintWriter(bufferedWriter);

            printWriter.println(userName + " Failed to login at: " + getTime());

            printWriter.flush();

            printWriter.close();
            fileWriter.close();
            bufferedWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


}
