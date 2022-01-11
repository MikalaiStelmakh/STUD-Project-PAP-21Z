package z11.libraryapp.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import z11.libraryapp.DbHandler;
import z11.libraryapp.errors.UnavailableDB;
import z11.libraryapp.model.Author;
import z11.libraryapp.model.Genre;
import z11.libraryapp.model.User;

public class AuthorController {

    private Author authorObject;

    private User userObject;

    @FXML
    private HBox authorGenres;

    @FXML
    private ImageView authorImage;

    @FXML
    private Label authorName;

    public void setData(Author author, User user){
        authorObject = author;
        userObject = user;
        Image image = new Image(getClass().getResourceAsStream("/z11/libraryapp/img/photos/" + author.getPhotoSrc()));
        authorImage.setImage(image);
        authorName.setText(author.getName());
        DbHandler dbManager;
        try {
            dbManager = new DbHandler();
            ArrayList<Genre> genres = dbManager.getAuthorGenres(author.getId());
            for (int i = 0; i< genres.size(); i++){
                Genre genre = genres.get(i);
                Label genre_label = new Label();
                if (i < genres.size()-1){
                    genre_label.setText(genre.getName() + ", ");;
                }
                else {
                    genre_label.setText(genre.getName());;
                }
                genre_label.setWrapText(true);
                genre_label.setFont(Font.font("Berlin Sans FB", 12));
                authorGenres.getChildren().add(genre_label);
            }
        } catch (UnavailableDB e) {
            e.printStackTrace();
        }
    }

    @FXML
    void authorOnMouseClicked(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = MainWindowController.changeScene(event, "/z11/libraryapp/fxml/AuthorView.fxml");
            AuthorViewController authorViewController = fxmlLoader.getController();
            authorViewController.setData(authorObject, userObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
