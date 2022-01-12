package z11.libraryapp.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private Button readingButton;

    @FXML
    private GridPane searchContainer;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField searchField;

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
    void readingButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = MainWindowController.changeScene(event, "/z11/libraryapp/fxml/Reading.fxml");
        ReadingController controller = fxmlLoader.getController();
        controller.setData(userObject);
    }

    private void search(Object event, String query) throws IOException, UnavailableDB{
        FXMLLoader fxmlLoader = MainWindowController.changeScene(event, "/z11/libraryapp/fxml/SearchView.fxml");
        SearchViewController searchViewController = fxmlLoader.getController();
        searchViewController.setData(userObject, query);
    }

    @FXML
    void onSearchIconClicked(MouseEvent event) throws IOException, UnavailableDB {
        String query = searchField.getText();
        search(event, query);
    }

    @FXML
    void onSearchKeyPressed(KeyEvent event) throws IOException, UnavailableDB {
        if (event.getCode().equals(KeyCode.ENTER)){
            String query = searchField.getText();
            search(event, query);
        }
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

    private ArrayList<Book> filterBooks(DbHandler dbManager, String query) throws UnavailableDB{
        ArrayList<Book> allBooks = dbManager.getBooks();
        ArrayList<Book> filteredBooks = new ArrayList<Book>();
        for (Book book : allBooks){
            Pattern pattern = Pattern.compile(".*" + query.toLowerCase() + ".*");
            Matcher matcher1 = pattern.matcher(book.getTitle().toLowerCase());
            Matcher matcher2 = pattern.matcher(book.getCountry().toLowerCase());
            boolean matchFound = matcher1.matches() || matcher2.matches();
            if (matchFound)
                filteredBooks.add(book);
        }
        return filteredBooks;
    }

    private ArrayList<Author> filterAuthors(DbHandler dbManager, String query) throws UnavailableDB{
        ArrayList<Author> allAuthors = dbManager.getAuthors();
        ArrayList<Author> filteredAuthors = new ArrayList<Author>();
        for (Author author : allAuthors){
            Pattern pattern = Pattern.compile(".*" + query.toLowerCase() + ".*");
            Matcher matcher1 = pattern.matcher(author.getName().toLowerCase());
            boolean matchFound = matcher1.matches();
            if (matchFound)
                filteredAuthors.add(author);
        }
        return filteredAuthors;
    }

    private ArrayList<Genre> filterGenres(DbHandler dbManager, String query) throws UnavailableDB{
        ArrayList<Genre> allGenres = dbManager.getGenres();
        ArrayList<Genre> filteredGenres = new ArrayList<Genre>();
        for (Genre genre : allGenres){
            Pattern pattern = Pattern.compile(".*" + query.toLowerCase() + ".*");
            Matcher matcher1 = pattern.matcher(genre.getName().toLowerCase());
            boolean matchFound = matcher1.matches();
            if (matchFound)
                filteredGenres.add(genre);
        }
        return filteredGenres;
    }

    public void setData(User user, String query) throws UnavailableDB, IOException{
        userObject = user;
        usernameLabel.setText(user.getLogin());
        setSearchLabel(query);
        DbHandler dbManager = new DbHandler();
        int row = 1;
        ArrayList<Book> filteredBooks = filterBooks(dbManager, query);
        if (filteredBooks.size() > 0){
            setSearchBooks(filteredBooks, 0, row++);
        }
        ArrayList<Author> filteredAuthors = filterAuthors(dbManager, query);
        if (filteredAuthors.size() > 0){
            setSearchAuthors(filteredAuthors, 0, row++);
        }
        ArrayList<Genre> filteredGenres = filterGenres(dbManager, query);
        if (filteredGenres.size() > 0){
            setSearchGenres(filteredGenres, 0, row++);
        }
    }
}
