package z11.libraryapp.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
    private TableView<?> booksTable;

    @FXML
    private TableColumn<?, ?> bookId;

    @FXML
    private TableColumn<?, ?> title;

    @FXML
    private TableColumn<?, ?> summary;

    @FXML
    private TableColumn<?, ?> published;

    @FXML
    private TableColumn<?, ?> pages;

    @FXML
    private TableColumn<?, ?> cover;

    @FXML
    private TableColumn<?, ?> country;

    @FXML
    private TableColumn<?, ?> series;

    @FXML
    private TableColumn<?, ?> lengua;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    void initialize() {

    }
}
