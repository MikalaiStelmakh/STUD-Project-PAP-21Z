package z11.libraryapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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

    @FXML
    void initialize() {
        signUpButton.setOnAction(actionEvent -> {
            signUpButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader(SignInController.class.getResource("/z11/libraryapp/SignUp.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });

        logInButton.setOnAction(actionEvent -> {
            logInButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader(SignInController.class.getResource("/z11/libraryapp/MainWindow.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });
    }
}
