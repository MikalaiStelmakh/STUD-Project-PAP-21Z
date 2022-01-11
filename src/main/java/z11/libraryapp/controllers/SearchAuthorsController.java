package z11.libraryapp.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import z11.libraryapp.model.Author;
import z11.libraryapp.model.User;

public class SearchAuthorsController {

    private User userObject;

    @FXML
    private VBox searchAuthorsBox;

    @FXML
    private GridPane searchAuthorsContainer;

    public void setData(ArrayList<Author> authors, User user) throws IOException{
        userObject = user;
        int column = 0;
        for (Author author : authors){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(AuthorController.class.getResource("/z11/libraryapp/fxml/Author.fxml"));
            VBox authorBox = fxmlLoader.load();
            AuthorController authorController = fxmlLoader.getController();
            authorController.setData(author, userObject);
            searchAuthorsContainer.add(authorBox, column, 0);
            GridPane.setMargin(authorBox, new Insets(10));
            column++;
        }
    }

}
