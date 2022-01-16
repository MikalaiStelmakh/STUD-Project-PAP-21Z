package z11.libraryapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import z11.libraryapp.DbHandler;
import z11.libraryapp.errors.UnavailableDB;

public class AddGenrePageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField genreIdTextField;

    @FXML
    private TextField genreNameTextField;

    @FXML
    private Button addGenreButton;

    @FXML
    private Label informationField;

    @FXML
    void initialize() throws UnavailableDB {
        DbHandler dbManager = new DbHandler();

        addGenreButton.setOnAction(actionEvent -> {
            try {
                String genreName = genreNameTextField.getText();
                if (genreName == "") {
                    informationField.setText("Fill in all the fields");
                } else {
                    try {
                        dbManager.addGenre(genreName);
                    } catch (UnavailableDB e) {
                        e.printStackTrace();
                    }
                    try {
                        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/z11/libraryapp/fxml/AdminCategories.fxml")));
                        Stage stage = (Stage) addGenreButton.getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }catch (NumberFormatException e) {
                System.err.println("Wrong string format!");
            }
        });
    }
}

