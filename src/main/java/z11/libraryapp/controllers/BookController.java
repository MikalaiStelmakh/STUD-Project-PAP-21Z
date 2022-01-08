package z11.libraryapp.controllers;

import java.io.IOException;

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

public class BookController {

    private Book bookObject;

    @FXML
    private Label bookAuthor;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookTitle;

    public void setData(Book book){
        bookObject = book;
        Image image = new Image(getClass().getResourceAsStream("/z11/libraryapp/img/covers/" + book.getCoverSrc()));
        bookImage.setImage(image);
        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthorsNames());
    }

    @FXML
    void bookOnMouseClicked(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BookViewController.class.getResource("/z11/libraryapp/fxml/BookView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            BookViewController bookViewController = fxmlLoader.getController();
            bookViewController.setData(bookObject);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnavailableDB e) {
            e.printStackTrace();
        }
    }
}
