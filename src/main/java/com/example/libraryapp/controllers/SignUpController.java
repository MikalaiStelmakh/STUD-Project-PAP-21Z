package com.example.libraryapp.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField signUpPassField;

    @FXML
    private TextField signUpLoginField;

    @FXML
    private Button signUpButtonCreate;

    @FXML
    private TextField signUpUsername;

    @FXML
    private TextField confirmSignUpPassField;

    @FXML
    void initialize() {

    }
}

