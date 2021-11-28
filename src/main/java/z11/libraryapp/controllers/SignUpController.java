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
    private Button cancelButton;

    @FXML
    private Label createErrorLabel;

    @FXML
    void initialize() {
        cancelButton.setOnAction(actionEvent -> {
            cancelButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader(SignUpController.class.getResource("/z11/libraryapp/hello-view.fxml"));
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

        signUpButtonCreate.setOnAction(actionEvent -> {
            try {
                checkCreate();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void checkCreate() throws IOException {
        if (signUpLoginField.getText().toString() == "" || signUpPassField.getText().toString() == "" || signUpUsername.getText().toString() == "" || confirmSignUpPassField.getText().toString() == "") {
            createErrorLabel.setText("Fill in all the fields");
        } else if (signUpPassField.getText().toString() != confirmSignUpPassField.getText().toString()) {
            createErrorLabel.setText("Password mismatch");
        }
    }
}
