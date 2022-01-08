package z11.libraryapp.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HistoryController {

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
        MainWindowController.changeScene(event, "/z11/libraryapp/fxml/History.fxml");

    }

    @FXML
    void categoriesButtonOnAction(ActionEvent event) {
        MainWindowController.changeScene(event, "/z11/libraryapp/fxml/Categories.fxml");

    }

    @FXML
    void dashboardButtonOnAction(ActionEvent event) {
        MainWindowController.changeScene(event, "/z11/libraryapp/fxml/MainWindow.fxml");
    }

    @FXML
    void historyButtonOnAction(ActionEvent event) {
    }

    @FXML
    void readingButtonOnAction(ActionEvent event) {
        MainWindowController.changeScene(event, "/z11/libraryapp/fxml/Reading.fxml");
    }

}
