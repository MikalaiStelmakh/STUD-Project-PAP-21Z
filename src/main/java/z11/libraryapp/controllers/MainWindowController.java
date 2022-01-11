package z11.libraryapp.controllers;

import java.io.Console;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.Node;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import z11.libraryapp.DbHandler;
import z11.libraryapp.errors.UnavailableDB;
import z11.libraryapp.model.Book;
import z11.libraryapp.model.User;


public class MainWindowController{

    private User user_object;

    @FXML
    private Label usernameLabel;

    @FXML
    private Button authorsButton;

    @FXML
    private Button genresButton;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button historyButton;

    @FXML
    private Button readingButton;

    @FXML
    private GridPane cardContainer;

    @FXML
    private GridPane bookContainer;

    public static FXMLLoader changeScene(Object event, String FXMLPath) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindowController.class.getResource(FXMLPath));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(MainWindowController.class.getResource("/z11/libraryapp/css/styles.css").toExternalForm());
        Stage stage = (Stage)((Node)((EventObject) event).getSource()).getScene().getWindow();
        stage.setScene(scene);
        return fxmlLoader;
    }

    public void setData(User user){
        user_object = user;
        usernameLabel.setText(user_object.getLogin());
        int n_books_in_card = 6;
        int col = 0;
        int row = 1;
        try {
            DbHandler dbManager = new DbHandler();
            ArrayList<Book> books = dbManager.getBooks();
            int cardCounter = 0;
            for (Book book : books){
                if (cardCounter < n_books_in_card){
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(CardController.class.getResource("/z11/libraryapp/fxml/Card.fxml"));
                    HBox cardBox = fxmlLoader.load();
                    CardController cardController = fxmlLoader.getController();
                    cardController.setData(book, user_object);
                    cardContainer.add(cardBox, cardCounter, 0);
                    cardCounter++;
                }
            }

            for(Book book_rom : books){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(BookController.class.getResource("/z11/libraryapp/fxml/Book.fxml"));
                VBox bookBox = fxmlLoader.load();
                BookController bookController = fxmlLoader.getController();
                bookController.setData(book_rom, user_object);

                if (col==6){
                    col = 0;
                    ++row;
                }
                bookContainer.add(bookBox, col++, row);
                GridPane.setMargin(bookBox, new Insets(10));
            }
            dbManager.closeConnetion();
        } catch (IOException | UnavailableDB e) {
            e.printStackTrace();
        }
    }

    @FXML
    void authorsButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = MainWindowController.changeScene(event, "/z11/libraryapp/fxml/Authors.fxml");
        AuthorsController controller = fxmlLoader.getController();
        controller.setData(user_object);
    }

    @FXML
    void genresButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = MainWindowController.changeScene(event, "/z11/libraryapp/fxml/Genres.fxml");
        GenresController controller = fxmlLoader.getController();
        controller.setData(user_object);
    }

    @FXML
    void dashboardButtonOnAction(ActionEvent event) throws IOException {
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

    @FXML
    void logOutButtonOnAction(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = changeScene(event, "/z11/libraryapp/fxml/SignIn.fxml");
        SignInController signInController = fxmlLoader.getController();
        signInController.setData(user_object);
    }
}
