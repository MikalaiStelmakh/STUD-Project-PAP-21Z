module com.example.libraryapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.libraryapp to javafx.fxml;
    exports com.example.libraryapp;
    exports com.example.libraryapp.controllers;
    opens com.example.libraryapp.controllers to javafx.fxml;
}