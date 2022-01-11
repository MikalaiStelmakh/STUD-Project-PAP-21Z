package z11.libraryapp.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import z11.libraryapp.DbHandler;
import z11.libraryapp.errors.UnavailableDB;
import z11.libraryapp.model.Author;
import z11.libraryapp.model.Book;
import z11.libraryapp.model.Genre;
import z11.libraryapp.model.User;

public class SearchViewController {

    private User userObject;

    @FXML
    private Button authorsButton;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button genresButton;

    @FXML
    private Label searchQueryLabel;

    @FXML
    private Button historyButton;

    @FXML
    private Button readingButton;

    @FXML
    private GridPane searchContainer;

    @FXML
    private Label usernameLabel;

    @FXML
    void authorsButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = MainWindowController.changeScene(event, "/z11/libraryapp/fxml/Authors.fxml");
        AuthorsController controller = fxmlLoader.getController();
        controller.setData(userObject);
    }

    @FXML
    void genresButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = MainWindowController.changeScene(event, "/z11/libraryapp/fxml/Genres.fxml");
        GenresController controller = fxmlLoader.getController();
        controller.setData(userObject);
    }

    @FXML
    void dashboardButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = MainWindowController.changeScene(event, "/z11/libraryapp/fxml/MainWindow.fxml");
        MainWindowController controller = fxmlLoader.getController();
        controller.setData(userObject);
    }

    @FXML
    void historyButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = MainWindowController.changeScene(event, "/z11/libraryapp/fxml/History.fxml");
        HistoryController controller = fxmlLoader.getController();
        controller.setData(userObject);
    }

    @FXML
    void readingButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = MainWindowController.changeScene(event, "/z11/libraryapp/fxml/Reading.fxml");
        ReadingController controller = fxmlLoader.getController();
        controller.setData(userObject);
    }

    @FXML
    void logOutButtonOnAction(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = MainWindowController.changeScene(event, "/z11/libraryapp/fxml/SignIn.fxml");
        SignInController signInController = fxmlLoader.getController();
        signInController.setData(userObject);
    }

    private void setSearchLabel(String query){
        searchQueryLabel.setText(query);
    }

    private void setSearchBooks(ArrayList<Book> books, int column, int row) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(BookController.class.getResource("/z11/libraryapp/fxml/SearchBooks.fxml"));
        VBox searchBooksBox = fxmlLoader.load();
        SearchBooksController searchBooksController = fxmlLoader.getController();
        searchContainer.add(searchBooksBox, column, row);
        searchBooksController.setData(books, userObject);
    }

    private void setSearchAuthors(ArrayList<Author> authors, int column, int row) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(BookController.class.getResource("/z11/libraryapp/fxml/SearchAuthors.fxml"));
        VBox searchAuthorsBox = fxmlLoader.load();
        SearchAuthorsController searchAuthorsController = fxmlLoader.getController();
        searchContainer.add(searchAuthorsBox, column, row);
        searchAuthorsController.setData(authors, userObject);
    }

    private void setSearchGenres(ArrayList<Genre> genres, int column, int row) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(BookController.class.getResource("/z11/libraryapp/fxml/SearchGenres.fxml"));
        VBox searchGenresBox = fxmlLoader.load();
        SearchGenresController searchGenresController = fxmlLoader.getController();
        searchContainer.add(searchGenresBox, column, row);
        searchGenresController.setData(genres, userObject);
    }

    public void setData(User user, String query) throws UnavailableDB, IOException{
        userObject = user;
        setSearchLabel(query);
        DbHandler dbManager = new DbHandler();
        // TODO: Pass only found books
        ArrayList<Book> books = dbManager.getBooks();
        setSearchBooks(books, 0, 1);
        // TODO: Pass only found authors
        ArrayList<Author> authors = dbManager.getAuthors();
        setSearchAuthors(authors, 0, 2);
        // TODO: Pass only found genres
        ArrayList<Genre> genres = dbManager.getGenres();
        setSearchGenres(genres, 0, 3);
    }
}
