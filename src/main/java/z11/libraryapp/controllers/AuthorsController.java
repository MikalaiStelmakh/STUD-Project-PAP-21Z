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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import z11.libraryapp.DbHandler;
import z11.libraryapp.errors.UnavailableDB;
import z11.libraryapp.model.Author;
import z11.libraryapp.model.User;

public class AuthorsController {

    private User user_object;

    @FXML
    private Button authorsButton;

    @FXML
    private GridPane authorsContainer;

    @FXML
    private Button genresButton;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button historyButton;

    @FXML
    private Button readingButton;

    @FXML
    private Label usernameLabel;


    @FXML
    void authorsButtonOnAction(ActionEvent event) throws IOException {
    }

    @FXML
    void genresButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = MainWindowController.changeScene(event, "/z11/libraryapp/fxml/Genres.fxml");
        GenresController controller = fxmlLoader.getController();
        controller.setData(user_object);
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
        int col = 0;
        int row = 1;
        try {
            DbHandler dbManager = new DbHandler();
            ArrayList<Author> authors;
            authors = dbManager.getAuthors();
            for (Author author : authors){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(AuthorController.class.getResource("/z11/libraryapp/fxml/Author.fxml"));
                VBox authorBox = fxmlLoader.load();
                AuthorController authorController = fxmlLoader.getController();
                authorController.setData(author, user_object);

                if (col==6){
                    col = 0;
                    ++row;
                }
                authorsContainer.add(authorBox, col++, row);
                GridPane.setMargin(authorBox, new Insets(10));
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