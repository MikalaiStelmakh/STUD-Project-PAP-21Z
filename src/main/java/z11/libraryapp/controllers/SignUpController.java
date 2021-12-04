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
import javafx.scene.Node;

import static java.lang.Thread.sleep;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField confirmSignUpPassField;

    @FXML
    private Label errorLabel;

    @FXML
    private Label successLabel;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField signUpEmailField;

    @FXML
    private PasswordField signUpPassField;

    @FXML
    private TextField signUpUsernameField;

    private Parent root;
    private Stage stage;
    private Scene scene;

    public void cancelButtonOnAction(ActionEvent event){
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/z11/libraryapp/fxml/SignIn.fxml")));
            scene.getStylesheets().add(getClass().getResource("/z11/libraryapp/css/styles.css").toExternalForm());
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void signUpButtonOnAction(ActionEvent event) throws IOException {
        errorLabel.setText("");
        successLabel.setText("");
        if (checkCreate()) {

//            signUpButton.getScene().getWindow().hide();
//            FXMLLoader loader = new FXMLLoader(SignUpController.class.getResource("/z11/libraryapp/SignIn.fxml"));
//            try {
//                root = loader.load();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            SignInController signInController = loader.getController();
//            signInController.displayName(signUpUsernameField.getText());
//            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//            scene = new Scene(root);
//            stage.setScene(scene);
//            stage.show();
        }

    }

    @FXML
    void initialize() {
    }

    private boolean checkCreate() throws IOException {
        if (signUpEmailField.getText() == "" || signUpPassField.getText() == "" || signUpUsernameField.getText().toString() == "" || confirmSignUpPassField.getText().toString() == "") {
            errorLabel.setText("Fill in all the fields");
            return false;

        } else if (!signUpPassField.getText().equals(confirmSignUpPassField.getText())) {
            System.out.println(signUpPassField.getText());
            System.out.println(confirmSignUpPassField.getText());
            errorLabel.setText("Password mismatch");
            return false;
        }
        else {
            successLabel.setText("Success!");
            return true;
        }
    }
}
