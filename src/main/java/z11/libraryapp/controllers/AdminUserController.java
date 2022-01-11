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
import z11.libraryapp.model.User;

public class AdminUserController {

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
    private Button genres;

    @FXML
    private Button changesBook;

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, Integer> userId;

    @FXML
    private TableColumn<User, String> firstname;

    @FXML
    private TableColumn<User, String> lastname;

    @FXML
    private TableColumn<User, String> login;

    @FXML
    private TableColumn<User, String> salt;

    @FXML
    private TableColumn<User, Integer> isAdmin;


    ///////////////////////// so far for example,before revision ////////////////////////////////////////////////
    private ObservableList<User> users1 = FXCollections.observableArrayList();

    private void initData() {
        users1.add(new User(1, "John", "Brown", "login", "password", 0));
        users1.add(new User(1, "William", "White", "mylogin", "mypassword", 0));
        users1.add(new User(1, "Conor", "McChicken", "mma", "mma", 0));
    }

    @FXML
    void initialize() {
        initData();
        userId.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        firstname.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        lastname.setCellValueFactory(new PropertyValueFactory<User, String>("surname"));
        login.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
        salt.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        isAdmin.setCellValueFactory(new PropertyValueFactory<User, Integer>("isAdmin"));

        userTable.setItems(users1);

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
