module z11.libraryapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens z11.libraryapp to javafx.fxml;
    exports z11.libraryapp;
    exports z11.libraryapp.controllers;
    exports z11.libraryapp.errors;
    opens z11.libraryapp.controllers to javafx.fxml;
}