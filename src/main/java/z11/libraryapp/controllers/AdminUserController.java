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
import z11.libraryapp.model.User;

public class AdminUserController {

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
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, Integer> userId;

    @FXML
    private TableColumn<User, String> name;

    @FXML
    private TableColumn<User, String> surname;

    @FXML
    private TableColumn<User, String> login;

    @FXML
    private TableColumn<User, String> password;

    @FXML
    private TableColumn<User, String> permission;

    private void initData(ArrayList<User> users_list) {
        ObservableList<User> users_table = FXCollections.observableArrayList(users_list);

        userId.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        surname.setCellValueFactory(new PropertyValueFactory<User, String>("surname"));
        login.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
        password.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        permission.setCellValueFactory(new PropertyValueFactory<User, String>("permission"));
        userTable.setItems(users_table);
    }

    @FXML
    void initialize() {
        DbHandler dbManager;
        try {
            dbManager = new DbHandler();
            ArrayList<User> users_list = dbManager.getUsers();
            initData(users_list);
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
}
