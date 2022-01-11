package z11.libraryapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import z11.libraryapp.model.Author;

public class AuthorViewController {

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

    public void setData(Author author){
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

}
