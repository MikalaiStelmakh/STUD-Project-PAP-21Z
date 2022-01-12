package z11.libraryapp.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import z11.libraryapp.DbHandler;
import z11.libraryapp.errors.DdlQueryError;
import z11.libraryapp.errors.UnavailableDB;
import z11.libraryapp.model.Book;
import z11.libraryapp.model.BookInstance;
import z11.libraryapp.model.HistoryNode;
import z11.libraryapp.model.User;

public class UserBooksController {

    private User userObject;

    int n_nodes;

    private int books_in_row = 6;

    private float label_height = 31;

    private float row_height = 350;

    private boolean isHidden = false;

    @FXML
    private VBox booksBox;

    @FXML
    private GridPane booksContainer;

    @FXML
    private ImageView expandIcon;

    @FXML
    private Pane booksPane;

    @FXML
    private Label label;

    private void setPaneHeight(int num_of_rows){
        float paneHeight = row_height * num_of_rows;
        booksPane.setMinHeight(paneHeight);
        booksPane.setMaxHeight(paneHeight);

    }

    private void setBoxHeight(int num_of_rows){
        float boxHeight = row_height * num_of_rows + label_height;
        booksBox.setMinHeight(boxHeight);
        booksBox.setMaxHeight(boxHeight);

    }

    public void setData(ArrayList<HistoryNode> historyNodes, User user, String text, boolean isHidden){
        userObject = user;
        label.setText(text);
        n_nodes = historyNodes.size();
        if (!isHidden){
            expand();
        }
        else {
            hide();
        }

        int col = 0;
        int row = 1;
        try {
            if (n_nodes > 0){
                for(HistoryNode historyNode : historyNodes){
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/z11/libraryapp/fxml/UserBook.fxml"));
                    VBox userBookBox = fxmlLoader.load();
                    UserBookController userBookController = fxmlLoader.getController();
                    userBookController.setData(historyNode, userObject);

                    if (col==books_in_row){
                        col = 0;
                        ++row;
                    }
                    booksContainer.add(userBookBox, col++, row);
                    GridPane.setMargin(userBookBox, new Insets(10));
                }
            }
            else {
                Label message = new Label("No books in " + text);
                message.setFont(Font.font("Arial", 18));
                message.setPadding(new Insets(0, 0, 0, 20));
                booksContainer.add(message, 0, 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onExpandIconClicked(MouseEvent event) {
        if (!isHidden){
            hide();
        }
        else {
            expand();
        }
    }

    public void hide(){
        Image image = new Image(getClass().getResourceAsStream("/z11/libraryapp/img/icons/expand-icon.png"));
        expandIcon.setImage(image);
        int num_of_rows = 0;
        setPaneHeight(num_of_rows);
        setBoxHeight(num_of_rows);
        booksPane.setVisible(false);
        isHidden = true;
    }

    public void expand(){
        Image image = new Image(getClass().getResourceAsStream("/z11/libraryapp/img/icons/hide-icon.png"));
        expandIcon.setImage(image);
        int num_of_rows = (int) Math.ceil((double)n_nodes/(double)books_in_row);

        setPaneHeight(num_of_rows);
        setBoxHeight(num_of_rows);
        booksPane.setVisible(true);
        isHidden = false;
    }

}
