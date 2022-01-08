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
import javafx.stage.Stage;
import z11.libraryapp.errors.UnavailableDB;
import z11.libraryapp.model.Book;

public class SameSeriesBookController {

    private Book bookObject;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookNumber;

    @FXML
    private Hyperlink bookTitle;

    @FXML
    private HBox box;

    public void setData(Book book, int id){
        bookObject = book;
        bookNumber.setText(Integer.toString(id) + ".");
        Image image = new Image(getClass().getResourceAsStream("/z11/libraryapp/img/covers/" + book.getCoverSrc()));
        bookImage.setImage(image);
        bookTitle.setText(book.getTitle() + " (" + book.getPublicationYear() + ").");
        bookTitle.setWrapText(true);
    }

    @FXML
    void bookOnMouseClicked(ActionEvent event) {
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
