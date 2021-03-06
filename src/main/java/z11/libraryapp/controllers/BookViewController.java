package z11.libraryapp.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import z11.libraryapp.DbHandler;
import z11.libraryapp.errors.DmlQueryError;
import z11.libraryapp.errors.UnavailableDB;
import z11.libraryapp.model.Author;
import z11.libraryapp.model.Book;
import z11.libraryapp.model.BookInstance;
import z11.libraryapp.model.Genre;
import z11.libraryapp.model.User;

public class BookViewController {

    private User user_object;

    private Book book_object;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField searchField;

    @FXML
    private Button authorsButton;

    @FXML
    private Hyperlink bookAuthor;

    @FXML
    private Button bookBorrowButton;

    @FXML
    private Label bookCountry;

    @FXML
    private HBox bookAuthorsBox;

    @FXML
    private HBox bookGenresBox;

    @FXML
    private ImageView bookImage;

    @FXML
    private ImageView bookIsAvailableIcon;

    @FXML
    private Label bookIsAvailableLabel;

    @FXML
    private Label bookPublicationYear;

    @FXML
    private GridPane sameSeriesBooksContainer;

    @FXML
    private Label bookSeries;

    @FXML
    private Label bookTitle;

    @FXML
    private Label bookSummary;

    @FXML
    private Button genresButton;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button readingButton;

    @FXML
    void authorsButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = MainWindowController.changeScene(event, "/z11/libraryapp/fxml/Authors.fxml");
        AuthorsController controller = fxmlLoader.getController();
        controller.setData(user_object);
    }

    @FXML
    void genresButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = MainWindowController.changeScene(event, "/z11/libraryapp/fxml/Genres.fxml");
        GenresController controller = fxmlLoader.getController();
        controller.setData(user_object);
    }

    @FXML
    void dashboardButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = MainWindowController.changeScene(event, "/z11/libraryapp/fxml/MainWindow.fxml");
        MainWindowController controller = fxmlLoader.getController();
        controller.setData(user_object);
    }

    @FXML
    void readingButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = MainWindowController.changeScene(event, "/z11/libraryapp/fxml/Reading.fxml");
        ReadingController controller = fxmlLoader.getController();
        controller.setData(user_object);
    }


    private ArrayList<Book> getBooksInSameSeries(int book_id, DbHandler dbHandler) throws UnavailableDB{
        ArrayList<Book> booksInSameSeries = dbHandler.getBooksInSameSeries(book_id);
        return booksInSameSeries;
    }

    private void setAuthors(Book book){
        ArrayList<Author> authors = book.getAuthors();
        book_object = book;

        if (authors.size() == 0){
            Label message = new Label("Undefined");
            message.setPadding(new Insets(5, 0, 0, 5));
            message.setFont(Font.font("Arial", 24));
            bookAuthorsBox.getChildren().add(message);
            return;
        }
        else {
            for (int i = 0; i< authors.size(); i++){
                Author author = authors.get(i);
                Hyperlink author_link = new Hyperlink();
                if (i < authors.size()-1){
                    author_link.setText(author.getName() + ",");;
                }
                else {
                    author_link.setText(author.getName());;
                }

                author_link.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            FXMLLoader fxmlLoader = MainWindowController.changeScene(event, "/z11/libraryapp/fxml/AuthorView.fxml");
                            AuthorViewController authorViewController = fxmlLoader.getController();
                            authorViewController.setData(author, user_object);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                author_link.setFont(Font.font("Arial", 24));
                bookAuthorsBox.getChildren().add(author_link);
            }
        }
    }

    private void setGenres(Book book){
        ArrayList<Genre> genres = book.getGenres();

        if (genres.size() > 0){
            for (int i = 0; i< genres.size(); i++){
                Genre genre = genres.get(i);
                Hyperlink genre_link = new Hyperlink();
                if (i < genres.size()-1){
                    genre_link.setText(genre.getName() + ",");;
                }
                else {
                    genre_link.setText(genre.getName());;
                }

                genre_link.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        try {
                            FXMLLoader fxmlLoader = MainWindowController.changeScene(e, "/z11/libraryapp/fxml/GenreView.fxml");
                            GenreViewController genreViewController = fxmlLoader.getController();
                            genreViewController.setData(genre, user_object);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
                genre_link.setFont(Font.font("Arial", 24));
                bookGenresBox.getChildren().add(genre_link);
            }
        }
        else{
            Label message = new Label("Undefined");
            message.setPadding(new Insets(5, 0, 0, 5));
            message.setFont(Font.font("Arial", 24));
            bookGenresBox.getChildren().add(message);
            return;
        }
    }

    private void setSummary(Book book){
        String summary = book.getSummary();
        if (summary != null)
        {
            bookSummary.setText(summary);
            bookSummary.setWrapText(true);
        }
        else{
            bookSummary.setText("No summary available.\n");
        }
    }

    private void setSameSeriesBooks(ArrayList<Book> sameSeriesBooks, Book book) throws IOException{
        if (sameSeriesBooks.size() > 0){
            int counter = 1;
            for (Book book_obj : sameSeriesBooks){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(SameSeriesBookController.class.getResource("/z11/libraryapp/fxml/SameSeriesBook.fxml"));
                HBox cardBox = fxmlLoader.load();
                SameSeriesBookController bookController = fxmlLoader.getController();
                if (book.getId() == book_obj.getId()){
                    bookController.setData(book_obj, user_object, counter, true);
                }
                else {
                    bookController.setData(book_obj, user_object, counter, false);
                }
                sameSeriesBooksContainer.add(cardBox, 0, counter);
                counter++;
            }
        }
        else{
            Label error = new Label("No books available in this series :(");
            error.setPadding(new Insets(10, 0, 0, 0));
            sameSeriesBooksContainer.add(error, 0, 1);
        }
    }

    private boolean isAlreadyReserved(ArrayList<BookInstance> bookInstances){
        for (BookInstance bookInstance : bookInstances){
            if (bookInstance.getStatus().equals("RESERVED") && bookInstance.getUser_id() == user_object.getId()){
                return true;
            }
        }
        return false;
    }

    private boolean isAlreadyBorrowed(ArrayList<BookInstance> bookInstances){
        for (BookInstance bookInstance : bookInstances){
            if (bookInstance.getStatus().equals("LENT") && bookInstance.getUser_id() == user_object.getId()){
                return true;
            }
        }
        return false;
    }

    private int getNumOfAvailableInstances(ArrayList<BookInstance> bookInstances){
        int counter = 0;
        for (BookInstance bookInstance : bookInstances){
            if (bookInstance.getStatus().equals("AVAILABLE")){
                counter++;
            }
        }
        return counter;
    }

    private void clearButtonStyles(){
        bookBorrowButton.getStyleClass().clear();
        // bookBorrowButton.setStyle(null);
        bookBorrowButton.getStyleClass().add("button");
        bookBorrowButton.getStyleClass().add("borrow-btn");
        bookBorrowButton.getStyleClass().add("btn");
    }

    private void setAvailable(){
        clearButtonStyles();
        bookIsAvailableIcon.setImage(new Image(getClass().getResourceAsStream("/z11/libraryapp/img/icons/available-icon.png")));
        bookIsAvailableLabel.setText("Available");
        bookIsAvailableLabel.getStyleClass().add("available");
        bookBorrowButton.setText("Reserve");
        bookBorrowButton.getStyleClass().add("available");
        bookBorrowButton.setDisable(false);
    }

    private void setUnAvailable(){
        clearButtonStyles();
        bookIsAvailableIcon.setImage(new Image(getClass().getResourceAsStream("/z11/libraryapp/img/icons/not-available-icon.png")));
        bookIsAvailableLabel.setText("Not available");
        bookIsAvailableLabel.getStyleClass().add("not-available");
        bookBorrowButton.setText("Reserve");
        bookBorrowButton.getStyleClass().add("not-available");
        bookBorrowButton.setDisable(true);
    }

    private void setReserved(){
        clearButtonStyles();
        bookIsAvailableIcon.setImage(new Image(getClass().getResourceAsStream("/z11/libraryapp/img/icons/reserved-icon.png")));
        bookIsAvailableLabel.setText("Reserved");
        bookIsAvailableLabel.getStyleClass().add("available");
        bookBorrowButton.setText("Cancel Reservation");
        bookBorrowButton.getStyleClass().add("not-available");
        bookBorrowButton.setDisable(false);
    }

    private void setBorrowed(){
        clearButtonStyles();
        bookIsAvailableIcon.setImage(new Image(getClass().getResourceAsStream("/z11/libraryapp/img/icons/not-available-icon.png")));
        bookIsAvailableLabel.setText("Already Borrowed");
        bookIsAvailableLabel.getStyleClass().add("not-available");
        bookBorrowButton.setText("Reserve");
        bookBorrowButton.getStyleClass().add("not-available");
        bookBorrowButton.setDisable(true);
    }

    private void setAvailabilityStatus(ArrayList<BookInstance> bookInstances){

        if (isAlreadyReserved(bookInstances)){
            setReserved();
        }
        else if (isAlreadyBorrowed(bookInstances)){
            setBorrowed();
        }
        else if (getNumOfAvailableInstances(bookInstances) == 0){
            setUnAvailable();
        }
        // Available and not reserved or borrowed by the user
        else {
            setAvailable();
        }
    }

    private void setPublicationYear(Book book){
        if (book.getPublicationYear() != 0)
            bookPublicationYear.setText(Integer.toString(book.getPublicationYear()));
    }

    private void setCover(Book book){
        //TODO: Set to default image if image source is null
        Image image = new Image(getClass().getResourceAsStream("/z11/libraryapp/img/covers/" + book.getCoverSrc()));
        bookImage.setImage(image);
    }

    private void setCountry(Book book){
        //TODO: Set to undefined if country is null
        bookCountry.setText(book.getCountry());

    }

    public void setData(Book book, User user) throws UnavailableDB, IOException{
        user_object = user;
        usernameLabel.setText(user_object.getLogin());

        bookTitle.setText(book.getTitle());
        setCover(book);
        setAuthors(book);
        setCountry(book);
        bookSeries.setText(book.getSeries());
        setPublicationYear(book);
        setSummary(book);
        setGenres(book);

        DbHandler dbManager;
        dbManager = new DbHandler();

        ArrayList<Book> sameSeriesBooks = getBooksInSameSeries(book.getId(), dbManager);
        setSameSeriesBooks(sameSeriesBooks, book);

        ArrayList<BookInstance> bookInstances = dbManager.getBookInstances(book.getId());
        setAvailabilityStatus(bookInstances);

    }

    private void search(Object event, String query) throws IOException, UnavailableDB{
        FXMLLoader fxmlLoader = MainWindowController.changeScene(event, "/z11/libraryapp/fxml/SearchView.fxml");
        SearchViewController searchViewController = fxmlLoader.getController();
        searchViewController.setData(user_object, query);
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
    void onBorrowButtonPressed(ActionEvent event) throws UnavailableDB, DmlQueryError {
        DbHandler dbManager = new DbHandler();
        ArrayList<BookInstance> bookInstances = dbManager.getBookInstances(book_object.getId());
        if (isAlreadyReserved(bookInstances)){
            for (BookInstance bookInstance : bookInstances){
                if (bookInstance.getStatus().equals("RESERVED") && bookInstance.getUser_id() == user_object.getId()){
                    dbManager.returnBook(bookInstance.getId());
                    bookInstances = dbManager.getBookInstances(book_object.getId());
                    if (getNumOfAvailableInstances(bookInstances) > 0){
                        setAvailable();
                    }
                    else {
                        setUnAvailable();
                    }
                }
            }
        }
        else{
            if (bookInstances.size() > 0){
                int randomNum = ThreadLocalRandom.current().nextInt(0, bookInstances.size());
                dbManager.reserveBook(bookInstances.get(randomNum).getId(), user_object.getId());
                setReserved();
            }
        }
    }

    @FXML
    void logOutButtonOnAction(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = MainWindowController.changeScene(event, "/z11/libraryapp/fxml/SignIn.fxml");
        SignInController signInController = fxmlLoader.getController();
        signInController.setData(user_object);
    }

}
