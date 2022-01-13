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
import z11.libraryapp.model.Author;
import z11.libraryapp.model.Book;

public class AdminAuthorsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button AdmPg;

    @FXML
    private Button users;

    @FXML
    private Button authors;

    @FXML
    private Button genres;

    @FXML
    private Button changesBook;

    @FXML
    private TableView<Author> authorTable;

    @FXML
    private TableColumn<Author, Integer> bookId;

    @FXML
    private TableColumn<Author, String> name;

    @FXML
    private TableColumn<Author, String> surname;

    @FXML
    private TableColumn<Author, Integer> birth;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;


    ///////////////////////// so far for example,before revision ////////////////////////////////////////////////
    private ObservableList<Author> authors1 = FXCollections.observableArrayList();


    @FXML
    void initialize() throws UnavailableDB {

        DbHandler dbManager = new DbHandler();
        ArrayList<Author> a = dbManager.getAuthors();
        for (int i = 0; i< a.size(); i++){
            Author author = a.get(i);
            bookId.setCellValueFactory(new PropertyValueFactory<Author, Integer>("id"));
            name.setCellValueFactory(new PropertyValueFactory<Author, String>("firstName"));
            surname.setCellValueFactory(new PropertyValueFactory<Author, String>("lastName"));
            birth.setCellValueFactory(new PropertyValueFactory<Author, Integer>("birthYear"));
            authors1.add(new Author(author.getId(), author.getFirstName(), author.getLastName(), author.getBirthYear()));
            authorTable.setItems(authors1);
        }

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

//        bookStatus.setOnAction(actionEvent -> {
//
//            try {
//                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/z11/libraryapp/fxml/AdminBookStatus.fxml")));
//                Stage stage = (Stage) bookStatus.getScene().getWindow();
//                stage.setScene(scene);
//                stage.show();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });

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

    public void onSearchKeyPressed(KeyEvent keyEvent) {
    }

    public void onSearchIconClicked(MouseEvent mouseEvent) {
    }
}
