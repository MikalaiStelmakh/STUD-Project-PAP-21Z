package z11.libraryapp.controllers;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import z11.libraryapp.model.Book;

public class AdminChangeBookController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button AdmPg;

    @FXML
    private Button users;

    @FXML
    private Button bookStatus;

    @FXML
    private Button authors;

    @FXML
    private Button categories;

    @FXML
    private Button changesBook;

    @FXML
    private TableView<Book> booksTable;

    @FXML
    private TableColumn<Book, Integer> bookId;

    @FXML
    private TableColumn<Book, String> title;

    @FXML
    private TableColumn<Book, Integer> summary;

    @FXML
    private TableColumn<Book, Date> published;

    @FXML
    private TableColumn<Book, Integer> pages;

    @FXML
    private TableColumn<Book, String> cover;

    @FXML
    private TableColumn<Book, Integer> country;

    @FXML
    private TableColumn<Book, Integer> series;

    @FXML
    private TableColumn<Book, Integer> languages;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    ///////////////////////// so far for example,before revision ////////////////////////////////////////////////
    private ObservableList<Book> books = FXCollections.observableArrayList();

    private void initData() {
        books.add(new Book(5, "dfd", "5", 5, 5, "df", "fdf", "dgdg", "fdf"));
    }

    @FXML
    void initialize() {
        initData();
        bookId.setCellValueFactory(new PropertyValueFactory<Book, Integer>("id"));
        title.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        summary.setCellValueFactory(new PropertyValueFactory<Book, Integer>("summary"));
        published.setCellValueFactory(new PropertyValueFactory<Book, Date>("publicationYear"));
        pages.setCellValueFactory(new PropertyValueFactory<Book, Integer>("pages"));
        cover.setCellValueFactory(new PropertyValueFactory<Book, String>("coverSrc"));
        country.setCellValueFactory(new PropertyValueFactory<Book, Integer>("country"));
        series.setCellValueFactory(new PropertyValueFactory<Book, Integer>("series"));
        languages.setCellValueFactory(new PropertyValueFactory<Book, Integer>("language"));

        booksTable.setItems(books);

    ///////////////////////////////////////////////////////
        AdmPg.setOnAction(actionEvent -> {

            try {
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/z11/libraryapp/fxml/AdminPage.fxml")));
                Stage stage = (Stage) AdmPg.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

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

        bookStatus.setOnAction(actionEvent -> {

            try {
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/z11/libraryapp/fxml/AdminBookStatus.fxml")));
                Stage stage = (Stage) bookStatus.getScene().getWindow();
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

        categories.setOnAction(actionEvent -> {

            try {
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/z11/libraryapp/fxml/AdminCategories.fxml")));
                Stage stage = (Stage) categories.getScene().getWindow();
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
