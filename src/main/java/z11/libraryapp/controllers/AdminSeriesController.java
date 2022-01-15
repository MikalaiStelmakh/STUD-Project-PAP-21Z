package z11.libraryapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import z11.libraryapp.errors.DmlQueryError;
import z11.libraryapp.model.Genre;
import z11.libraryapp.model.Series;
import z11.libraryapp.DbHandler;
import z11.libraryapp.errors.DdlQueryError;
import z11.libraryapp.errors.UnavailableDB;
import z11.libraryapp.model.User;

public class AdminSeriesController {

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
    private TableView<Series> seriesTable;

    @FXML
    private TableColumn<Series, Integer> seriesId;

    @FXML
    private TableColumn<Series, String> seriesName;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;


    private void initData(ArrayList<Series> series_list) {
        ObservableList<Series> series_table = FXCollections.observableArrayList(series_list);

        seriesId.setCellValueFactory(new PropertyValueFactory<Series, Integer>("id"));
        seriesName.setCellValueFactory(new PropertyValueFactory<Series, String>("name"));

        seriesTable.setItems(series_table);
    }

    @FXML
    void initialize() throws UnavailableDB {

        DbHandler dbManager = new DbHandler();
        ArrayList<Series> series_list = dbManager.getSeries();
        initData(series_list);

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

        addButton.setOnAction(actionEvent -> {

            try {
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/z11/libraryapp/fxml/AddSeriesPage.fxml")));
                Stage stage = (Stage) addButton.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        deleteButton.setOnAction(actionEvent -> {

            try {
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/z11/libraryapp/fxml/DelSeriesPage.fxml")));
                Stage stage = (Stage) deleteButton.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

