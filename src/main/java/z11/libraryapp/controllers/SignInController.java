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
import z11.libraryapp.errors.UnavailableDB;
import z11.libraryapp.model.User;

public class SignInController {

    private User user_object;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button signUpButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button logInButton;

    @FXML
    private TextField loginField;

    @FXML
    private Label errorLabel;

    public void setData(User user){
        loginField.setText(user.getLogin());
        passwordField.requestFocus();
    }

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

    public void logInButtonOnAction(ActionEvent event) throws UnavailableDB{
        String login = loginField.getText();
        String password = passwordField.getText();
        if (login == "" || password == "") {
            errorLabel.setText("Fill in all the fields");
            return;
        }

        DbHandler dbManager;
        dbManager = new DbHandler();

        if (dbManager.validateUser(login, password)){
            try {
                user_object = dbManager.getUserByLogin(login);
                FXMLLoader fxmlLoader;
                Scene scene;
                if (user_object.getPermission().equals("User")){
                    fxmlLoader = new FXMLLoader(getClass().getResource("/z11/libraryapp/fxml/MainWindow.fxml"));
                    scene = new Scene(fxmlLoader.load());
                    scene.getStylesheets().add(getClass().getResource("/z11/libraryapp/css/styles.css").toExternalForm());
                    MainWindowController mainWindowController = fxmlLoader.getController();
                    mainWindowController.setData(user_object);
                }
                else {
                    fxmlLoader = new FXMLLoader(getClass().getResource("/z11/libraryapp/fxml/AdminPage.fxml"));
                    scene = new Scene(fxmlLoader.load());
                    scene.getStylesheets().add(getClass().getResource("/z11/libraryapp/css/styles.css").toExternalForm());
                }
                Stage stage = new Stage();
                stage.setScene(scene);
                logInButton.getScene().getWindow().hide();
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (DdlQueryError e) {
                e.printStackTrace();
            }
        }
        else {
            errorLabel.setText("Invalid login or password");
            return;
        }
    }

    public void displayName(String username){
        loginField.setText(username);
    }

    @FXML
    void initialize() {
    }
}
