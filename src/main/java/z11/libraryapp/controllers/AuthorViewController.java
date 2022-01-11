package z11.libraryapp.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import z11.libraryapp.model.Author;
import z11.libraryapp.model.User;

public class AuthorViewController {

    private User user_object;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label authorBirthYear;

    @FXML
    private Label authorDeathYear;

    @FXML
    private Label authorName;

    @FXML
    private Label authorName1;

    @FXML
    private ImageView authorImage;

    @FXML
    private GridPane booksByAuthorContainer;

    @FXML
    private Button authorsButton;

    @FXML
    private Button categoriesButton;

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
    void categoriesButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = MainWindowController.changeScene(event, "/z11/libraryapp/fxml/Categories.fxml");
        CategoriesController controller = fxmlLoader.getController();
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

    public void setData(Author author, User user){
        user_object = user;
        usernameLabel.setText(user_object.getLogin());
        authorName.setText(author.getName());
        authorName1.setText(author.getName());
        authorBirthYear.setText(Integer.toString(author.getBirthYear()));
        int author_death_year = author.getDeathYear();
        if (author_death_year == 0){
            authorDeathYear.setText("now");
        }
        else {
            authorDeathYear.setText(Integer.toString(author_death_year));
        }
        Image image = new Image(getClass().getResourceAsStream("/z11/libraryapp/img/photos/" + author.getPhotoSrc()));
        authorImage.setImage(image);
    }

    @FXML
    void logOutButtonOnAction(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = MainWindowController.changeScene(event, "/z11/libraryapp/fxml/SignIn.fxml");
        SignInController signInController = fxmlLoader.getController();
        signInController.setData(user_object);
    }

}
