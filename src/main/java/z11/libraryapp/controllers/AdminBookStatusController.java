package z11.libraryapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class AdminBookStatusController {

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
    private TableView<?> statusTable;

    @FXML
    private TableColumn<?, Integer> bookInstanceId;

    @FXML
    private TableColumn<?, Integer> bookId;

    @FXML
    private TableColumn<?, Integer> userId;

    @FXML
    private TableColumn<?, Date> lendDate;

    @FXML
    private TableColumn<?, Date> returnDate;

    @FXML
    private TableColumn<?, Integer> isAvailable;

    @FXML
    void initialize() {
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
