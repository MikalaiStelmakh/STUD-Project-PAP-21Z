package z11.libraryapp.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import z11.libraryapp.errors.UnavailableDB;
import z11.libraryapp.model.Book;
import z11.libraryapp.model.User;

public class BookController {

    private Book bookObject;

    private User userObject;

    @FXML
    private Label bookAuthor;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookTitle;

    public void setData(Book book, User user){
        bookObject = book;
        userObject = user;
        Image image = new Image(getClass().getResourceAsStream("/z11/libraryapp/img/covers/" + book.getCoverSrc()));
        bookImage.setImage(image);
        bookTitle.setText(book.getTitle());
        bookTitle.setWrapText(true);
        bookAuthor.setWrapText(true);
        bookAuthor.setText(book.getAuthorsNames());
    }

    @FXML
    void bookOnMouseClicked(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = MainWindowController.changeScene(event, "/z11/libraryapp/fxml/BookView.fxml");
            BookViewController bookViewController = fxmlLoader.getController();
            bookViewController.setData(bookObject, userObject);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnavailableDB e) {
            e.printStackTrace();
        }
    }
}
