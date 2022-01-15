package z11.libraryapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import z11.libraryapp.DbHandler;
import z11.libraryapp.errors.DmlQueryError;
import z11.libraryapp.errors.UnavailableDB;
import z11.libraryapp.model.Genre;

public class DelGenrePageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField idTextField;

    @FXML
    private Button delButton;

    @FXML
    private Label informationField;

    @FXML
    void initialize() throws UnavailableDB {

        DbHandler dbManager = new DbHandler();

        delButton.setOnAction(actionEvent -> {

            try {
                Integer genreId = Integer.parseInt(idTextField.getText());

                try {
                    dbManager.delGenre(genreId);
                } catch (UnavailableDB e) {
                    e.printStackTrace();
                }
                try {
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/z11/libraryapp/fxml/AdminCategories.fxml")));
                    Stage stage = (Stage) delButton.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }catch (NumberFormatException e) {
                informationField.setText("Select the genre_id you want to delete");
                System.err.println("Wrong string format!");
            }
        });
    }
}

