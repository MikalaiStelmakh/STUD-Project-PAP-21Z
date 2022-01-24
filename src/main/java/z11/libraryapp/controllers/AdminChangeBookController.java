package z11.libraryapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import z11.libraryapp.DbHandler;
import z11.libraryapp.errors.TransactionError;
import z11.libraryapp.errors.UnavailableDB;
import z11.libraryapp.model.Author;
import z11.libraryapp.model.Book;
import z11.libraryapp.model.Genre;

public class AdminChangeBookController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button users;

    @FXML
    private Button bookInstances;

    @FXML
    private Button seriesBtn;

    @FXML
    private Button authors;

    @FXML
    private Button genres;

    @FXML
    private Button changesBook;

    @FXML
    private TableView<Book> booksTable;

    @FXML
    private TableColumn<Book, Integer> bookId;

    @FXML
    private TableColumn<Book, String> title;

    @FXML
    private TableColumn<Book, String> summary;

    @FXML
    private TableColumn<Book, Integer> published;

    @FXML
    private TableColumn<Book, Integer> pages;

    @FXML
    private TableColumn<Book, String> cover;

    @FXML
    private TableColumn<Book, String> country;

    @FXML
    private TableColumn<Book, String> series;

    @FXML
    private TableColumn<Book, String> languages;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    private ObservableList<Book> books = FXCollections.observableArrayList();

    @FXML
    void initialize() throws UnavailableDB {

        DbHandler dbManager = new DbHandler();
        ArrayList<Book> books1 = dbManager.getBooks();
        for (int i = 0; i< books1.size(); i++){
            Book book = books1.get(i);
            bookId.setCellValueFactory(new PropertyValueFactory<Book, Integer>("id"));
            title.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
            summary.setCellValueFactory(new PropertyValueFactory<Book, String>("summary"));
            published.setCellValueFactory(new PropertyValueFactory<Book, Integer>("publicationYear"));
            pages.setCellValueFactory(new PropertyValueFactory<Book, Integer>("pages"));
            cover.setCellValueFactory(new PropertyValueFactory<Book, String>("coverSrc"));
            country.setCellValueFactory(new PropertyValueFactory<Book, String>("country"));
            series.setCellValueFactory(new PropertyValueFactory<Book, String>("series"));
            languages.setCellValueFactory(new PropertyValueFactory<Book, String>("language"));
            books.add(new Book(book.getId(), book.getTitle(), book.getSummary(), book.getPublicationYear(), book.getPages(), book.getCoverSrc(), book.getCountry(), book.getSeries(), book.getLanguage()));
            booksTable.setItems(books);
        }

        users.setOnAction(actionEvent -> {

            try {
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/z11/libraryapp/fxml/AdminUserPage.fxml")));
                Stage stage = (Stage) users.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        bookInstances.setOnAction(actionEvent -> {

            try {
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/z11/libraryapp/fxml/AdminBookInstances.fxml")));
                Stage stage = (Stage) genres.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        authors.setOnAction(actionEvent -> {

            try {
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/z11/libraryapp/fxml/AdminAuthors.fxml")));
                Stage stage = (Stage) authors.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        genres.setOnAction(actionEvent -> {

            try {
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/z11/libraryapp/fxml/AdminCategories.fxml")));
                Stage stage = (Stage) genres.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        seriesBtn.setOnAction(actionEvent -> {

            try {
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/z11/libraryapp/fxml/AdminSeries.fxml")));
                Stage stage = (Stage) genres.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        changesBook.setOnAction(actionEvent -> {

            try {
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/z11/libraryapp/fxml/AdminChangeBook.fxml")));
                Stage stage = (Stage) changesBook.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        changesBook.setOnAction(actionEvent -> {

            try {
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/z11/libraryapp/fxml/AdminChangeBook.fxml")));
                Stage stage = (Stage) changesBook.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
