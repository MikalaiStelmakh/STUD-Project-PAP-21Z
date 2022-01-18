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
import z11.libraryapp.errors.DmlQueryError;
import z11.libraryapp.errors.UnavailableDB;

public class AddSeriesPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField seriesIdTextField;

    @FXML
    private TextField seriesNameTextField;

    @FXML
    private Button addSeriesButton;

    @FXML
    private Label informationField;

    @FXML
    void initialize() throws UnavailableDB {
        DbHandler dbManager = new DbHandler();

        addSeriesButton.setOnAction(actionEvent -> {
            try {
                String seriesName = seriesNameTextField.getText();
                if (seriesName == "") {
                    informationField.setText("Fill in all the fields");
                } else {
                    try {
                        dbManager.addSeries(seriesName);
                    } catch (UnavailableDB e) {
                        e.printStackTrace();
                    }
                    try {
                        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/z11/libraryapp/fxml/AdminSeries.fxml")));
                        Stage stage = (Stage) addSeriesButton.getScene().getWindow();
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

