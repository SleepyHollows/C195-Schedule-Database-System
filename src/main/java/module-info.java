module project.c195 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens project.c195 to javafx.fxml;
    exports project.c195;
    exports project.c195.controller;
    opens project.c195.controller to javafx.fxml;
}