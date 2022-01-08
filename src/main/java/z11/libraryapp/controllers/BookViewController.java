package z11.libraryapp.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import z11.libraryapp.DbHandler;
import z11.libraryapp.errors.UnavailableDB;


import z11.libraryapp.model.Book;

public class BookViewController {

    @FXML
    private Button authorsButton;

    @FXML
    private Hyperlink bookAuthor;

    @FXML
    private Button bookBorrowButton;

    @FXML
    private Label bookCountry;

    @FXML
    private ImageView bookImage;

    @FXML
    private ImageView bookIsAvailableIcon;

    @FXML
    private Label bookPublicationYear;

    @FXML
    private VBox bookSameSeriesBooks;

    @FXML
    private Label bookSeries;

    @FXML
    private Label bookTitle;

    @FXML
    private Label bookSummary;

    @FXML
    private Button categoriesButton;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button historyButton;

    @FXML
    private Button readingButton;

    @FXML
    void authorsButtonOnAction(ActionEvent event) {

    }

    @FXML
    void categoriesButtonOnAction(ActionEvent event) {
        MainWindowController.changeScene(event, "/z11/libraryapp/fxml/Categories.fxml");
    }

    @FXML
    void dashboardButtonOnAction(ActionEvent event) {
        MainWindowController.changeScene(event, "/z11/libraryapp/fxml/MainWindow.fxml");
    }

    @FXML
    void historyButtonOnAction(ActionEvent event) {
        MainWindowController.changeScene(event, "/z11/libraryapp/fxml/History.fxml");
    }

    @FXML
    void readingButtonOnAction(ActionEvent event) {
        MainWindowController.changeScene(event, "/z11/libraryapp/fxml/Reading.fxml");
    }

    private ArrayList<Book> getBooksInSameSeries(int book_id) throws UnavailableDB{
        DbHandler dbManager;
        dbManager = new DbHandler();
        ArrayList<Book> booksInSameSeries = dbManager.getBooksInSameSeries(book_id);
        return booksInSameSeries;
    }

    public void setData(Book book) throws UnavailableDB, IOException{
        Image image = new Image(getClass().getResourceAsStream("/z11/libraryapp/img/covers/" + book.getCoverSrc()));
        bookImage.setImage(image);
        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthorsNames());
        bookCountry.setText(book.getCountry());
        bookPublicationYear.setText(Integer.toString(book.getPublicationYear()));
        bookSeries.setText(book.getSeries());
        if (book.getSummary() != null)
        {
            bookSummary.setText(book.getSummary());
            bookSummary.setWrapText(true);
        }
        else{
            bookSummary.setText("No summary available.\n");
        }

        ArrayList<Book> sameSeriesBooks = getBooksInSameSeries(book.getId());
        if (sameSeriesBooks.size() > 0){
            int counter = 1;
            for (Book book_obj : sameSeriesBooks){
                if (counter < 4){
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(SameSeriesBookController.class.getResource("/z11/libraryapp/fxml/SameSeriesBook.fxml"));
                    HBox cardBox = fxmlLoader.load();
                    SameSeriesBookController bookController = fxmlLoader.getController();
                    bookController.setData(book_obj, counter);
                    bookSameSeriesBooks.getChildren().add(cardBox);
                    counter++;
                }
            }
        }
        else{
            Label error = new Label("No books available in this series :(");
            error.setPadding(new Insets(10, 0, 0, 0));
            bookSameSeriesBooks.getChildren().add(error);
        }

    }

}