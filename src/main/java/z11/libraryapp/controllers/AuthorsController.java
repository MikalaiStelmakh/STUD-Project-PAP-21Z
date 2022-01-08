package z11.libraryapp.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AuthorsController {

    @FXML
    private GridPane authorContainer;

    @FXML
    private HBox authorLayout;

    @FXML
    private Button authorsButton;

    @FXML
    private Button categoriesButton;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button historyButton;

    @FXML
    private Button readingButton;

    @FXML
    void authorsButtonOnAction(ActionEvent event) {

    }

    @FXML
    void categoriesButtonOnAction(ActionEvent event) {
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/z11/libraryapp/fxml/Categories.fxml")));
            scene.getStylesheets().add(getClass().getResource("/z11/libraryapp/css/styles.css").toExternalForm());
            Stage stage = (Stage) categoriesButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void dashboardButtonOnAction(ActionEvent event) {
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/z11/libraryapp/fxml/MainWindow.fxml")));
            scene.getStylesheets().add(getClass().getResource("/z11/libraryapp/css/styles.css").toExternalForm());
            Stage stage = (Stage) dashboardButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void historyButtonOnAction(ActionEvent event) {
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/z11/libraryapp/fxml/History.fxml")));
            scene.getStylesheets().add(getClass().getResource("/z11/libraryapp/css/styles.css").toExternalForm());
            Stage stage = (Stage) historyButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void readingButtonOnAction(ActionEvent event) {
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/z11/libraryapp/fxml/Reading.fxml")));
            scene.getStylesheets().add(getClass().getResource("/z11/libraryapp/css/styles.css").toExternalForm());
            Stage stage = (Stage) readingButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}