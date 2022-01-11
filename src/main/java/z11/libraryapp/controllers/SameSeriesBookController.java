package z11.libraryapp.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import z11.libraryapp.errors.UnavailableDB;
import z11.libraryapp.model.Book;
import z11.libraryapp.model.User;

public class SameSeriesBookController {

    private Book bookObject;

    private User userObject;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookNumber;

    @FXML
    private Hyperlink bookTitle;

    @FXML
    private HBox box;

    public void setData(Book book, User user, int id, boolean is_opened){
        bookObject = book;
        userObject = user;
        bookNumber.setText(Integer.toString(id) + ".");
        Image image = new Image(getClass().getResourceAsStream("/z11/libraryapp/img/covers/" + book.getCoverSrc()));
        bookImage.setImage(image);
        bookTitle.setText(book.getTitle() + " (" + book.getPublicationYear() + ").");
        bookTitle.setWrapText(true);
        if (is_opened){
            bookNumber.setDisable(true);
            bookTitle.setDisable(true);
            bookTitle.setStyle("-fx-underline: false");
            bookTitle.setTextFill(Color.BLACK);
        }
    }

    @FXML
    void bookOnMouseClicked(ActionEvent event) {
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
