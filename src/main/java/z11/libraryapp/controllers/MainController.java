package z11.libraryapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField fieldSearch;

    @FXML
    private Button searchButton;

    @FXML
    private MenuButton chooseAction;

    @FXML
    private MenuItem optionsButton;

    @FXML
    private MenuItem logOutButton;

    @FXML
    private ChoiceBox<?> chooseActionTest;

    @FXML
    void initialize() {

    }
}
