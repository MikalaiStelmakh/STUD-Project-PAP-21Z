package z11.libraryapp.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import z11.libraryapp.DbHandler;
import z11.libraryapp.errors.UnavailableDB;
import z11.libraryapp.model.Book;
import z11.libraryapp.model.Genre;
import z11.libraryapp.model.User;

public class GenreOverviewController {

    private Genre genreObject;

    private User userObject;

    @FXML
    private HBox genreBooks;

    @FXML
    private Label genreNameLabel;

    @FXML
    private VBox genreOverviewBox;

    @FXML
    private Hyperlink genreSeeAllLink;

    private void setBooks(Genre genre, User user, int n_books){
        try {
            DbHandler dbManager = new DbHandler();
            ArrayList<Book> books = dbManager.getBooksByGenre(genre.getId());
            int cardCounter = 0;
            if (books.size() == 0){
                genreOverviewBox.setMinHeight(50);
                genreOverviewBox.setMaxHeight(80);
                genreBooks.setMinHeight(24);
                genreBooks.setMaxHeight(50);
                Label message = new Label("No books :(");
                message.setFont(Font.font("Berlin Sans FB", 14));
                genreBooks.getChildren().add(message);
                genreSeeAllLink.setVisible(false);
            }
            for (Book book : books){
                if (cardCounter < n_books){
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(BookController.class.getResource("/z11/libraryapp/fxml/Book.fxml"));
                    VBox bookBox = fxmlLoader.load();
                    BookController bookController = fxmlLoader.getController();
                    bookController.setData(book, user);
                    genreBooks.getChildren().add(bookBox);
                    cardCounter++;
                }
            }
        } catch (IOException | UnavailableDB e) {
            e.printStackTrace();
        }
    }

    public void setData(Genre genre, User user){
        int n_books = 4;
        genreObject = genre;
        userObject = user;
        genreNameLabel.setText(genre.getName());
        setBooks(genre, user, n_books);
    }

}
