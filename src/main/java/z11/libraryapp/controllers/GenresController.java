package z11.libraryapp.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import z11.libraryapp.DbHandler;
import z11.libraryapp.errors.UnavailableDB;
import z11.libraryapp.model.Genre;
import z11.libraryapp.model.User;

public class GenresController {

    private User user_object;

    @FXML
    private Label usernameLabel;

    @FXML
    private Button authorsButton;

    @FXML
    private Button genresButton;

    @FXML
    private GridPane genreContainer;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button historyButton;

    @FXML
    private Button readingButton;

    @FXML
    void authorsButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = MainWindowController.changeScene(event, "/z11/libraryapp/fxml/Authors.fxml");
        AuthorsController controller = fxmlLoader.getController();
        controller.setData(user_object);
    }

    @FXML
    void genresButtonOnAction(ActionEvent event) throws IOException {
    }

    @FXML
    void dashboardButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = MainWindowController.changeScene(event, "/z11/libraryapp/fxml/MainWindow.fxml");
        MainWindowController controller = fxmlLoader.getController();
        controller.setData(user_object);
    }

    @FXML
    void historyButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = MainWindowController.changeScene(event, "/z11/libraryapp/fxml/History.fxml");
        HistoryController controller = fxmlLoader.getController();
        controller.setData(user_object);
    }

    @FXML
    void readingButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = MainWindowController.changeScene(event, "/z11/libraryapp/fxml/Reading.fxml");
        ReadingController controller = fxmlLoader.getController();
        controller.setData(user_object);
    }

    public void setData(User user){
        user_object = user;
        usernameLabel.setText(user_object.getLogin());
        int row = 1;
        try {
            DbHandler dbManager = new DbHandler();
            ArrayList<Genre> genres;
            genres = dbManager.getGenres();
            for (Genre genre : genres){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(AuthorController.class.getResource("/z11/libraryapp/fxml/GenreOverview.fxml"));
                VBox genreOverviewBox = fxmlLoader.load();
                GenreOverviewController genreOverviewController = fxmlLoader.getController();
                genreOverviewController.setData(genre, user_object);

                genreContainer.add(genreOverviewBox, 0, row);
                row++;
                }
        } catch (UnavailableDB | IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void logOutButtonOnAction(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = MainWindowController.changeScene(event, "/z11/libraryapp/fxml/SignIn.fxml");
        SignInController signInController = fxmlLoader.getController();
        signInController.setData(user_object);
    }

}
