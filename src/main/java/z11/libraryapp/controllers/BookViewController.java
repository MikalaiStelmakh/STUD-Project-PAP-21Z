package z11.libraryapp.controllers;

import java.io.IOException;
import java.util.ArrayList;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import z11.libraryapp.DbHandler;
import z11.libraryapp.errors.UnavailableDB;
import z11.libraryapp.model.Author;
import z11.libraryapp.model.Book;
import z11.libraryapp.model.Genre;

public class BookViewController {

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
    private Button categoriesButton;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button historyButton;

    @FXML
    private Button readingButton;

    @FXML
    void authorsButtonOnAction(ActionEvent event) {
        MainWindowController.changeScene(event, "/z11/libraryapp/fxml/Authors.fxml");
    }

    @FXML
    void categoriesButtonOnAction(ActionEvent event) {
        MainWindowController.changeScene(event, "/z11/libraryapp/fxml/Categories.fxml");
    }

    @FXML
    void dashboardButtonOnAction(ActionEvent event) {
        MainWindowController.changeScene(event, "/z11/libraryapp/fxml/MainWindow.fxml");
    }

    @FXML
    void historyButtonOnAction(ActionEvent event) {
        MainWindowController.changeScene(event, "/z11/libraryapp/fxml/History.fxml");
    }

    @FXML
    void readingButtonOnAction(ActionEvent event) {
        MainWindowController.changeScene(event, "/z11/libraryapp/fxml/Reading.fxml");
    }

    private ArrayList<Book> getBooksInSameSeries(int book_id, DbHandler dbHandler) throws UnavailableDB{
        ArrayList<Book> booksInSameSeries = dbHandler.getBooksInSameSeries(book_id);
        return booksInSameSeries;
    }

    private int getNumOfAvailableInstances(int book_id, DbHandler dbHandler) throws UnavailableDB{
        return dbHandler.getNumOfAvailableInstances(book_id);
    }

    private void setAuthors(ArrayList<Author> authors){
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
                        FXMLLoader fxmlLoader = new FXMLLoader(BookViewController.class.getResource("/z11/libraryapp/fxml/AuthorView.fxml"));
                        Scene scene;
                        try {
                            scene = new Scene(fxmlLoader.load());
                            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                            AuthorViewController authorViewController = fxmlLoader.getController();
                            authorViewController.setData(author);
                            stage.setScene(scene);
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

    private void setGenres(ArrayList<Genre> genres){
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
                        System.out.println(genre.getName() + " clicked");
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

    private void setSummary(String summary){
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
                    bookController.setData(book_obj, counter, true);
                }
                else {
                    bookController.setData(book_obj, counter, false);
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

    private void setAvailabilityStatus(int nAvailableInstances){
        Image iconImage;
        if (nAvailableInstances > 0){
            iconImage = new Image(getClass().getResourceAsStream("/z11/libraryapp/img/icons/available-icon.png"));
            bookIsAvailableLabel.setText("Available");
            bookIsAvailableLabel.getStyleClass().add("available");
            bookBorrowButton.getStyleClass().add("available");

        }
        else{
            iconImage = new Image(getClass().getResourceAsStream("/z11/libraryapp/img/icons/not-available-icon.png"));
            bookIsAvailableLabel.setText("Not available");
            bookIsAvailableLabel.getStyleClass().add("not-available");
            bookBorrowButton.getStyleClass().add("not-available");
            bookBorrowButton.setDisable(true);
        }
        bookIsAvailableIcon.setImage(iconImage);
    }

    public void setData(Book book) throws UnavailableDB, IOException{
        Image image = new Image(getClass().getResourceAsStream("/z11/libraryapp/img/covers/" + book.getCoverSrc()));
        bookImage.setImage(image);
        bookTitle.setText(book.getTitle());

        ArrayList<Author> authors = book.getAuthors();
        setAuthors(authors);

        bookCountry.setText(book.getCountry());
        bookPublicationYear.setText(Integer.toString(book.getPublicationYear()));
        bookSeries.setText(book.getSeries());

        String summary = book.getSummary();
        setSummary(summary);

        ArrayList<Genre> genres = book.getGenres();
        setGenres(genres);

        DbHandler dbManager;
        dbManager = new DbHandler();

        ArrayList<Book> sameSeriesBooks = getBooksInSameSeries(book.getId(), dbManager);
        setSameSeriesBooks(sameSeriesBooks, book);

        int nAvailableInstances = getNumOfAvailableInstances(book.getId(), dbManager);
        setAvailabilityStatus(nAvailableInstances);

    }

}
