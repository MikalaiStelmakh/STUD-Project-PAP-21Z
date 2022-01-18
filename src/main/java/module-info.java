module LibraryApp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.eclipse.paho.mqttv5.client;
    requires spring.security.crypto;
    requires org.junit.jupiter.api;
    requires org.junit.platform.engine;
    requires org.junit.platform.launcher;
    requires org.junit.jupiter;

    opens z11.libraryapp to javafx.fxml;
    opens z11.tests to org.junit.platform.commons;
    exports z11.libraryapp;
    exports z11.libraryapp.model;
    exports z11.libraryapp.controllers;
    exports z11.libraryapp.errors;
    opens z11.libraryapp.controllers to javafx.fxml;
}