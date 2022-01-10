package z11.libraryapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import z11.libraryapp.DbHandler;
import z11.libraryapp.errors.DdlQueryError;
import z11.libraryapp.errors.DmlQueryError;
import z11.libraryapp.errors.UnavailableDB;
import z11.libraryapp.model.User;
import javafx.scene.Node;

import static java.lang.Thread.sleep;

public class SignUpController {


    @FXML
    private Button cancelButton;

    @FXML
    private PasswordField confirmSignUpPasswordField;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField signUpLoginField;

    @FXML
    private TextField signUpNameField;

    @FXML
    private PasswordField signUpPasswordField;

    @FXML
    private TextField signUpSurnameField;

    @FXML
    private Label messageField;


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

    public void signUpButtonOnAction(ActionEvent event) throws IOException, UnavailableDB, DmlQueryError, SQLException, DdlQueryError {
        String name = signUpNameField.getText();
        String surname = signUpSurnameField.getText();
        String login = signUpLoginField.getText();
        String password = signUpPasswordField.getText();
        String password_confirm = confirmSignUpPasswordField.getText();
        if (checkCreate(name, surname, login, password, password_confirm)) {
            DbHandler dbManager = new DbHandler();
            User user = dbManager.createUser(name, surname, login, password);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/z11/libraryapp/fxml/SignIn.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            SignInController signInController = fxmlLoader.getController();
            signInController.setData(user);
            scene.getStylesheets().add(getClass().getResource("/z11/libraryapp/css/styles.css").toExternalForm());
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        }

    }

    @FXML
    void initialize() {
    }

    private boolean checkCreate(String name, String surname, String login, String password, String password_confirm) throws IOException, UnavailableDB {
        if (name == "" || surname == "" || login == ""
            || password == "" || password_confirm == "") {
            messageField.setText("Fill in all the fields");
            return false;

        } else if (!password.equals(password_confirm)) {
            messageField.setText("Password mismatch");
            return false;
        }
        try {
            DbHandler dbManager = new DbHandler();
            if (!dbManager.isUniqueLogin(login)){
                messageField.setText("Username is already taken. Try another one.");
                return false;
            }
        } catch (SQLException | DdlQueryError e) {
            e.printStackTrace();
        }
        return true;
    }
}
