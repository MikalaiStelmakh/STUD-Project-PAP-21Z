package z11.libraryapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import z11.libraryapp.DbHandler;
import z11.libraryapp.errors.UnavailableDB;
import z11.libraryapp.model.Author;

public class AddAuthorPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField authorNameTextField;

    @FXML
    private Button addGenreButton;

    @FXML
    private Label informationField;

    @FXML
    private TextField authorSurnameTextField1;

    @FXML
    private TextField authorDeathTextField2;

    @FXML
    private TextField authorBirthTextField3;

    @FXML
    private TextField authorBioTextField4;

    @FXML
    private TextField authorSrcTextField5;

    @FXML
    void initialize() {
        addGenreButton.setOnAction(actionEvent -> {
            DbHandler dbManager = null;
            try {
                dbManager = new DbHandler();
            } catch (UnavailableDB e) {
                e.printStackTrace();
            }
            try {
                String authorName = authorNameTextField.getText();
                String authorSurname = authorSurnameTextField1.getText();
                Integer authorBirth = Integer.parseInt(authorBirthTextField3.getText());
                Integer authorDeath = Integer.parseInt(authorDeathTextField2.getText());
                String  authorBio = authorBioTextField4.getText();
                String  src = authorSrcTextField5.getText();
                if (authorName == "" || authorSurname == "" || authorBio == "" || src == "" ) {
                    informationField.setText("Fill in all the fields");
                } else {
                    try {
                        Author author = new Author(authorName, authorSurname, authorBirth, authorDeath, authorBio, src);
                        dbManager.addAuthor(author);
                    } catch (UnavailableDB e) {
                        e.printStackTrace();
                    }
                    try {
                        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/z11/libraryapp/fxml/AdminAuthors.fxml")));
                        Stage stage = (Stage) addGenreButton.getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }catch (NumberFormatException e) {
                informationField.setText("Write the number of birht or death you want to add");
                System.err.println("Wrong string format!");
            }
        });
    }
}