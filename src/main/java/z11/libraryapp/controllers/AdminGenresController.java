package z11.libraryapp.controllers;

import java.io.IOException;
import java.net.URL;
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
import z11.libraryapp.model.Genre;

public class AdminGenresController {

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
    private Button genresBtn;

    @FXML
    private Button changesBook;

    @FXML
    private TableView<Genre> genreTable;

    @FXML
    private TableColumn<Genre, Integer> genreId;

    @FXML
    private TableColumn<Genre, String> name;

    ///////////////////////// so far for example,before revision ////////////////////////////////////////////////
    private ObservableList<Genre> genres = FXCollections.observableArrayList();

    private void initData() {
        genres.add(new Genre(1, "Alex"));
        genres.add(new Genre(2, "Bob"));
        genres.add(new Genre(3, "Jeck"));
        genres.add(new Genre(4, "Mike"));
        genres.add(new Genre(5, "colin"));
    }

    @FXML
    void initialize() {
        initData();
        genreId.setCellValueFactory(new PropertyValueFactory<Genre, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Genre, String>("name"));

        genreTable.setItems(genres);
    ///////////////////////////////////////////////////////////////////

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

        genresBtn.setOnAction(actionEvent -> {

            try {
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/z11/libraryapp/fxml/AdminGenres.fxml")));
                Stage stage = (Stage) genresBtn.getScene().getWindow();
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

