package z11.libraryapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import z11.libraryapp.DbHandler;
import z11.libraryapp.errors.UnavailableDB;
import z11.libraryapp.model.Book;


public class MainWindowController implements Initializable {

    @FXML
    private HBox cardLayout;

    @FXML
    private GridPane bookContainer;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        int col = 0;
        int row = 1;
        try {
            DbHandler dbManager = new DbHandler();
            ArrayList<Book> books = dbManager.getBooks();

            for (Book book : books){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(CardController.class.getResource("/z11/libraryapp/Card.fxml"));
                HBox cardBox = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setData(book);
                cardLayout.getChildren().add(cardBox);
            }

            for(Book book_rom : books){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(CardController.class.getResource("/z11/libraryapp/Book.fxml"));
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
}
