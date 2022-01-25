package z11.libraryapp.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import z11.libraryapp.errors.UnavailableDB;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class ConfirmationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField codeField;

    @FXML
    private Button confirmButton;

    @FXML
    private Label informationField;


    public boolean examination () throws IOException {
        Integer code = Integer.parseInt(codeField.getText());
        String content;
        content = new String(Files.readAllBytes(Paths.get("filename.txt")));
        Integer code2 = Integer.parseInt(content);

        if (code.equals(code2))
            return true;
        else
            return false;
    }

    public void codeReview () throws IOException {
        if (examination()) {

                try {
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/z11/libraryapp/fxml/AdminUserPage.fxml")));
                    Stage stage = (Stage) confirmButton.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        } else {
            informationField.setText("Wrong code");
            //codeReview();
        }
    }

    @FXML
    void initialize() throws UnavailableDB, IOException {
        confirmButton.setOnAction(actionEvent -> {
            try {
                codeReview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
