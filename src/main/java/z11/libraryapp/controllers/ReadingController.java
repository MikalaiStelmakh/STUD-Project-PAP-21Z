package z11.libraryapp.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import z11.libraryapp.DbHandler;
import z11.libraryapp.errors.UnavailableDB;
import z11.libraryapp.model.Book;
import z11.libraryapp.model.BookInstance;
import z11.libraryapp.model.HistoryNode;
import z11.libraryapp.model.User;

public class ReadingController {

    private User user_object;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField searchField;

    @FXML
    private GridPane booksContainer;

    @FXML
    private Button authorsButton;

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
    }

    private void setReadingBooks(ArrayList<HistoryNode> historyNodes){
        try {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(AuthorController.class.getResource("/z11/libraryapp/fxml/UserBooks.fxml"));
            VBox userBooksBox = fxmlLoader.load();
            UserBooksController userBooksController = fxmlLoader.getController();
            userBooksController.setData(historyNodes, user_object, "Reading", false);
            booksContainer.add(userBooksBox, 0, 1);
            Line line = new Line(-100, 0, 1105, 0);
            line.setOpacity(0.12);
            booksContainer.add(line, 0, 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setReservedBooks(ArrayList<BookInstance> bookInstances){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(AuthorController.class.getResource("/z11/libraryapp/fxml/UserBooksReserved.fxml"));
            VBox userBooksBox = fxmlLoader.load();
            UserBooksReservedController userBooksController = fxmlLoader.getController();
            userBooksController.setData(bookInstances, user_object, true);
            booksContainer.add(userBooksBox, 0, 3);
            Line line = new Line(-100, 0, 1105, 0);
            line.setOpacity(0.12);
            booksContainer.add(line, 0, 4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setHistoryBooks(ArrayList<HistoryNode> historyNodes){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(AuthorController.class.getResource("/z11/libraryapp/fxml/UserBooks.fxml"));
            VBox userBooksBox = fxmlLoader.load();
            UserBooksController userBooksController = fxmlLoader.getController();
            userBooksController.setData(historyNodes, user_object, "History", true);
            booksContainer.add(userBooksBox, 0, 5);
            Line line = new Line(-100, 0, 1105, 0);
            line.setOpacity(0.12);
            booksContainer.add(line, 0, 6);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setData(User user){
        user_object = user;
        usernameLabel.setText(user_object.getLogin());
        try {
            DbHandler dbManager = new DbHandler();
            ArrayList<Book> allBooks = dbManager.getBooks();
            ArrayList<HistoryNode> borrowedBooks = new ArrayList<HistoryNode>();
            ArrayList<BookInstance> reservedBooks = new ArrayList<BookInstance>();
            ArrayList<HistoryNode> historyBooks = new ArrayList<HistoryNode>();
            ArrayList<HistoryNode> historyNodes = dbManager.getHistoryNodes();
            for (HistoryNode historyNode : historyNodes){
                if (historyNode.getUserId() == user.getId() && historyNode.getDateReturned() == null){
                    borrowedBooks.add(historyNode);
                }
                else if (historyNode.getUserId() == user.getId() && historyNode.getDateReturned() != null){
                    historyBooks.add(historyNode);
                }
            }
            for (Book book : allBooks){
                ArrayList<BookInstance> bookInstances = dbManager.getBookInstances(book.getId());
                for (BookInstance bookInstance : bookInstances){
                    if (bookInstance.getUser_id() == user.getId() && bookInstance.getStatus().equals("RESERVED")){
                        reservedBooks.add(bookInstance);
                    }
                }
            }

            setReadingBooks(borrowedBooks);
            setReservedBooks(reservedBooks);
            setHistoryBooks(historyBooks);
        } catch (UnavailableDB e) {
            e.printStackTrace();
        }
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
    void logOutButtonOnAction(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = MainWindowController.changeScene(event, "/z11/libraryapp/fxml/SignIn.fxml");
        SignInController signInController = fxmlLoader.getController();
        signInController.setData(user_object);
    }

}
