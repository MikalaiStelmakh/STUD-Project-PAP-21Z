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
import z11.libraryapp.errors.UnavailableDB;
import z11.libraryapp.model.Book;
import z11.libraryapp.model.BookInstance;
import z11.libraryapp.model.User;

public class AdminBookInstancesController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

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
    private TableView<BookInstance> bookInstancesTable;

    @FXML
    private TableColumn<BookInstance, Integer> bi_id;

    @FXML
    private TableColumn<BookInstance, Integer> book_id;

    @FXML
    private TableColumn<BookInstance, Integer> user_id;

    @FXML
    private TableColumn<BookInstance, String> status;


    private void initData(ArrayList<BookInstance> book_instances_list) {
        ObservableList<BookInstance> book_instances_table = FXCollections.observableArrayList(book_instances_list);

        bi_id.setCellValueFactory(new PropertyValueFactory<BookInstance, Integer>("id"));
        book_id.setCellValueFactory(new PropertyValueFactory<BookInstance, Integer>("book_id"));
        user_id.setCellValueFactory(new PropertyValueFactory<BookInstance, Integer>("user_id"));
        status.setCellValueFactory(new PropertyValueFactory<BookInstance, String>("status"));

        bookInstancesTable.setItems(book_instances_table);
    }

    @FXML
    void initialize() {
        DbHandler dbManager;
        try {
            dbManager = new DbHandler();
            ArrayList<Book> books = dbManager.getBooks();
            ArrayList<BookInstance> book_instance_list = new ArrayList<BookInstance>();
            for (Book book : books){
                ArrayList<BookInstance> book_instances = dbManager.getBookInstances(book.getId());
                for (BookInstance bookInstance : book_instances){
                    book_instance_list.add(bookInstance);
                }
            }
            initData(book_instance_list);
        } catch (UnavailableDB e1) {
            e1.printStackTrace();
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
    }
}
