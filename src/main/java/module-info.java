module project.c195 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens project.c195 to javafx.fxml;
    exports project.c195;
    exports project.c195.controller;
    exports project.c195.helpers;
    exports project.c195.model;
    opens project.c195.controller to javafx.fxml;
}