package z11.libraryapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignInController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button signUpButton;

    @FXML
    private PasswordField passField;

    @FXML
    private Button logInButton;

    @FXML
    private TextField loginField;

    @FXML
    private Label errorLabel;

    public void signUpButtonOnAction(ActionEvent event){
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/z11/libraryapp/fxml/SignUp.fxml")));
            scene.getStylesheets().add(getClass().getResource("/z11/libraryapp/css/styles.css").toExternalForm());
            Stage stage = (Stage) signUpButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logInButtonOnAction(ActionEvent event){
        logInButton.getScene().getWindow().hide();

        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/z11/libraryapp/fxml/MainWindow.fxml")));
            scene.getStylesheets().add(getClass().getResource("/z11/libraryapp/css/styles.css").toExternalForm());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void displayName(String username){
        loginField.setText(username);
    }

    @FXML
    void initialize() {
    }
}
