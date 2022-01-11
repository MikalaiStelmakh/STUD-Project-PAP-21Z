package z11.libraryapp.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import z11.libraryapp.model.Genre;
import z11.libraryapp.model.User;

public class SearchGenresController {

    private User userObject;

    @FXML
    private VBox searchGenresBox;

    @FXML
    private GridPane searchGenresContainer;

    public void setData(ArrayList<Genre> genres, User user) throws IOException{
        userObject = user;
        int column = 0;
        int row = 1;
        for (Genre genre : genres){
            if (column < 4){
                Hyperlink link = new Hyperlink(genre.getName());
                link.setFont(Font.font("Arial", 20));
                link.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        try {
                            FXMLLoader fxmlLoader = MainWindowController.changeScene(e, "/z11/libraryapp/fxml/GenreView.fxml");
                            GenreViewController genreViewController = fxmlLoader.getController();
                            genreViewController.setData(genre, user);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                    }
                });
                if (row == 4){
                    column++;
                    row = 1;
                }
                searchGenresContainer.add(link, column, row++);
            }
        }
    }
}
