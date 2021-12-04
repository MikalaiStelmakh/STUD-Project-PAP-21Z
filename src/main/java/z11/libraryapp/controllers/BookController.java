package z11.libraryapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import z11.libraryapp.model.Book;

public class BookController {
    @FXML
    private Label bookAuthor;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookTitle;

    public void setData(Book book){
        Image image = new Image(getClass().getResourceAsStream("/z11/libraryapp/img/covers/" + book.getCoverSrc()));
        bookImage.setImage(image);
        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthorsNames());
    }
}
