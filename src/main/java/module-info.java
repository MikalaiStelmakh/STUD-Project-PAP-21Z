module com.example.libraryapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens z11.libraryapp to javafx.fxml;
    exports z11.libraryapp;
    exports z11.libraryapp.controllers;
    opens z11.libraryapp.controllers to javafx.fxml;
}