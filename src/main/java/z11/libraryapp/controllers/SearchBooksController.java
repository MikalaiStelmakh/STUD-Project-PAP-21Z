package z11.libraryapp.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import z11.libraryapp.model.Book;
import z11.libraryapp.model.User;

public class SearchBooksController {

    private User userObject;

    @FXML
    private VBox searchBooksBox;

    @FXML
    private GridPane searchBooksContainer;

    public void setData(ArrayList<Book> books, User user) throws IOException{
        userObject = user;
        int column = 0;
        for (Book book : books){
            FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(BookController.class.getResource("/z11/libraryapp/fxml/Book.fxml"));
                VBox bookBox = fxmlLoader.load();
                BookController bookController = fxmlLoader.getController();
                bookController.setData(book, userObject);
                searchBooksContainer.add(bookBox, column, 0);
                GridPane.setMargin(bookBox, new Insets(10));
                column++;
        }
    }
}
