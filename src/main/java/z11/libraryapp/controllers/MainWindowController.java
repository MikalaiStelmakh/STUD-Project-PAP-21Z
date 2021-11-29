package z11.libraryapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import z11.libraryapp.model.Book;

public class MainWindowController implements Initializable {

    @FXML
    private HBox cardLayout;

    @FXML
    private GridPane bookContainer;

    private List<Book> recentlyAdded;
    private List<Book> books;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        recentlyAdded = new ArrayList<>(recentlyAdded());
        books = new ArrayList<>(books());
        int col = 0;
        int row = 1;
        try {
            for (Book book : recentlyAdded){
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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<Book> recentlyAdded(){
        List<Book> ls = new ArrayList<>();
        Book book = new Book();
        for (int i = 0; i < 5; i++){
            book = new Book();
            book.setTitle("Harry Potter\nand the\nsorcerer's stone");
            book.setImageSrc("/z11/libraryapp/img/harry-potter.jpg");
            book.setAuthor("JK Rowling");
            ls.add(book);

            book = new Book();
            book.setTitle("Romeo and\nJuliet");
            book.setImageSrc("/z11/libraryapp/img/romeo-and-juliet.jpg");
            book.setAuthor("William Shakespeare");
            ls.add(book);
        }
        return ls;
    }

    private List<Book> books(){
        List<Book> ls = new ArrayList<>();
        Book book = new Book();
        for (int i = 0; i < 5; i++){
            book = new Book();
            book.setTitle("Whispered Promises");
            book.setImageSrc("/z11/libraryapp/img/whispered-promises.jpg");
            book.setAuthor("Brenda Jackson");
            ls.add(book);

            book = new Book();
            book.setTitle("Harry Potter\nand the\nsorcerer's stone");
            book.setImageSrc("/z11/libraryapp/img/harry-potter.jpg");
            book.setAuthor("JK Rowling");
            ls.add(book);

            book = new Book();
            book.setTitle("Prince Hafiz's\nOnly Vice");
            book.setImageSrc("/z11/libraryapp/img/prince-hafiz.jpg");
            book.setAuthor("Susanna Carr");
            ls.add(book);
        }
        return ls;
    }
}
