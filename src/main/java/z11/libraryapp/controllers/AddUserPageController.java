package z11.libraryapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

public class AddUserPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField userNameTextField;

    @FXML
    private Button addUserButton;

    @FXML
    private Label informationField;

    @FXML
    private TextField userSurnameTextField;

    @FXML
    private TextField userLoginTextField;

    @FXML
    private PasswordField userPassTextField;

    @FXML
    void initialize() {
        addUserButton.setOnAction(actionEvent -> {
            String name = userNameTextField.getText();
            String surname = userSurnameTextField.getText();
            String login = userLoginTextField.getText();
            String password = userPassTextField.getText();

            try {
                DbHandler handler = new DbHandler();
                if (checkCreate(name, surname, login, password)) {
                    User user = handler.createUser(name, surname, login, password);
                    try {
                        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/z11/libraryapp/fxml/AdminUserPage.fxml")));
                        Stage stage = (Stage) addUserButton.getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (UnavailableDB | IOException | SQLException | DdlQueryError e) {
                e.printStackTrace();
            }
        });
    }

    private boolean checkCreate(String name, String surname, String login, String password) throws IOException, UnavailableDB, SQLException, DdlQueryError {
        if (name == "" || surname == "" || login == ""
                || password == "" ) {
            informationField.setText("Fill in all the fields");
            return false;
        }
        DbHandler dbManager = new DbHandler();
        if (!dbManager.isUniqueLogin(login)){
            informationField.setText("Username is already taken. Try another one.");
            return false;
        }
        return true;
    }
}

