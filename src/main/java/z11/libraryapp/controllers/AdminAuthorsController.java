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
    private TableView<Author> authorTable;

    @FXML
    private TableColumn<Author, Integer> id;

    @FXML
    private TableColumn<Author, String> name;

    @FXML
    private TableColumn<Author, String> surname;

    @FXML
    private TableColumn<Author, Integer> birthDate;

    @FXML
    private TableColumn<Author, Integer> deathDate;

    @FXML
    private TableColumn<Author, String> biography;

    @FXML
    private TableColumn<Author, String> photo;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;



    private void initData(ArrayList<Author> authors_list) {
        ObservableList<Author> authors_table = FXCollections.observableArrayList(authors_list);
        id.setCellValueFactory(new PropertyValueFactory<Author, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Author, String>("firstName"));
        surname.setCellValueFactory(new PropertyValueFactory<Author, String>("lastName"));
        birthDate.setCellValueFactory(new PropertyValueFactory<Author, Integer>("birthYear"));
        deathDate.setCellValueFactory(new PropertyValueFactory<Author, Integer>("deathYear"));
        biography.setCellValueFactory(new PropertyValueFactory<Author, String>("biography"));
        photo.setCellValueFactory(new PropertyValueFactory<Author, String>("photoSrc"));
        authorTable.setItems(authors_table);
    }


    @FXML
    void initialize() throws UnavailableDB {

        DbHandler dbManager = new DbHandler();
        ArrayList<Author> authors_list = dbManager.getAuthors();
        initData(authors_list);

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
