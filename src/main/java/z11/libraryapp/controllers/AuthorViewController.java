package z11.libraryapp.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import z11.libraryapp.DbHandler;
import z11.libraryapp.errors.UnavailableDB;
import z11.libraryapp.model.Author;
import z11.libraryapp.model.Book;
import z11.libraryapp.model.User;
import java.util.ArrayList;


public class AuthorViewController {

    private User user_object;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField searchField;

    @FXML
    private Label authorBirthYear;

    @FXML
    private Label authorDeathYear;

    @FXML
    private Label authorName;

    @FXML
    private Label authorName1;

    @FXML
    private Label authorBiography;

    @FXML
    private ImageView authorImage;

    @FXML
    private GridPane authorBooks;

    @FXML
    private Button authorsButton;

    @FXML
    private Button genresButton;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button historyButton;

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
    void historyButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = MainWindowController.changeScene(event, "/z11/libraryapp/fxml/History.fxml");
        HistoryController controller = fxmlLoader.getController();
        controller.setData(user_object);
    }

    @FXML
    void readingButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = MainWindowController.changeScene(event, "/z11/libraryapp/fxml/Reading.fxml");
        ReadingController controller = fxmlLoader.getController();
        controller.setData(user_object);
    }

    private void setDeathYear(Author author){
        int author_death_year = author.getDeathYear();
        if (author_death_year == 0){
            authorDeathYear.setText("now");
        }
        else {
            authorDeathYear.setText(Integer.toString(author_death_year));
        }
    }

    private void setBiography(Author author){
        if (author.getbiography().equals("")){
            authorBiography.setText("No biogaphy available.");
        }
        else{
            authorBiography.setText(author.getbiography());
            authorBiography.setWrapText(true);
        }
    }

    private void setPhoto(Author author){
        Image image = new Image(getClass().getResourceAsStream("/z11/libraryapp/img/photos/" + author.getPhotoSrc()));
        authorImage.setImage(image);
    }

    private void setBooks(Author author){
        try {
            DbHandler dbManager = new DbHandler();
            ArrayList<Book> books = dbManager.getAuthorBooks(author.getId());
            int col = 1;
            int row = 1;
            if (books.size() > 0){
                for(Book book : books){
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(BookController.class.getResource("/z11/libraryapp/fxml/Book.fxml"));
                    VBox bookBox = fxmlLoader.load();
                    BookController bookController = fxmlLoader.getController();
                    bookController.setData(book, user_object);


                    if (col==5){
                        col = 1;
                        ++row;
                    }
                    authorBooks.add(bookBox, col++, row);
                    GridPane.setMargin(bookBox, new Insets(10));
                }
            }
            else {
                Label message = new Label("There are no books by this author at the moment");
                message.setPadding(new Insets(0, 0, 0, 30));
                message.setMinWidth(400);
                message.setWrapText(true);
                message.setFont(Font.font("Sans Berlin FB", 12));
                authorBooks.add(message, 0, 0);
            }
        } catch (UnavailableDB | IOException e) {
            e.printStackTrace();
        }
    }

    public void setData(Author author, User user){
        user_object = user;
        usernameLabel.setText(user_object.getLogin());
        authorName.setText(author.getName());
        authorName1.setText(author.getName());
        authorBirthYear.setText(Integer.toString(author.getBirthYear()));
        setDeathYear(author);
        setBiography(author);
        setPhoto(author);
        setBooks(author);
    }

    @FXML
    void logOutButtonOnAction(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = MainWindowController.changeScene(event, "/z11/libraryapp/fxml/SignIn.fxml");
        SignInController signInController = fxmlLoader.getController();
        signInController.setData(user_object);
    }

}
