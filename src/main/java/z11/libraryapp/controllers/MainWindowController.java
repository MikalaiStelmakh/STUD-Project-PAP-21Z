package z11.libraryapp.controllers;

import java.io.Console;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import z11.libraryapp.DbHandler;
import z11.libraryapp.errors.UnavailableDB;
import z11.libraryapp.model.Book;


public class MainWindowController implements Initializable {

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
    private HBox cardLayout;

    @FXML
    private GridPane bookContainer;

    public static void changeScene(ActionEvent event, String FXMLPath)
    {
        try {
            Scene scene = new Scene(FXMLLoader.load(MainWindowController.class.getResource(FXMLPath)));
            scene.getStylesheets().add(MainWindowController.class.getResource("/z11/libraryapp/css/styles.css").toExternalForm());
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
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
                    cardController.setData(book);
                    cardLayout.getChildren().add(cardBox);
                    cardCounter++;
                }
            }

            for(Book book_rom : books){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(BookController.class.getResource("/z11/libraryapp/fxml/Book.fxml"));
                VBox bookBox = fxmlLoader.load();
                BookController bookController = fxmlLoader.getController();
                bookController.setData(book_rom);

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
    void authorsButtonOnAction(ActionEvent event) {
        changeScene(event, "/z11/libraryapp/fxml/Authors.fxml");
    }

    @FXML
    void categoriesButtonOnAction(ActionEvent event) {
        changeScene(event, "/z11/libraryapp/fxml/Categories.fxml");
    }

    @FXML
    void dashboardButtonOnAction(ActionEvent event) {

    }

    @FXML
    void historyButtonOnAction(ActionEvent event) {
        changeScene(event, "/z11/libraryapp/fxml/History.fxml");
    }

    @FXML
    void readingButtonOnAction(ActionEvent event) {
        changeScene(event, "/z11/libraryapp/fxml/Reading.fxml");
    }
}
