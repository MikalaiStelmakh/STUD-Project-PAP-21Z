package z11.libraryapp.controllers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import z11.libraryapp.DbHandler;
import z11.libraryapp.SendEmail;
import z11.libraryapp.errors.UnavailableDB;
import z11.libraryapp.model.EmailAdmin;

public class SendMailController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField emailTextField;

    @FXML
    private Button senMailButton;

    @FXML
    private Label informationField;

    @FXML
    private MenuButton button;

    @FXML
    private MenuItem button1;

    @FXML
    private MenuItem button2;


    public String EmailAdmin (String mail) {

        DbHandler dbManager = null;
        try {
            dbManager = new DbHandler();
        } catch (UnavailableDB e) {
            e.printStackTrace();
        }
        ArrayList<EmailAdmin> emailAdmin1 = null;
        try {
            emailAdmin1 = dbManager.getEmailAdmin();
        } catch (UnavailableDB e) {
            e.printStackTrace();
        }
        for (int i = 0; i< emailAdmin1.size(); i++){
            EmailAdmin emailAdmin = emailAdmin1.get(i);
            if (!Objects.equals(mail, emailAdmin.getEmail())) {
                informationField.setText("error email");
            }
            if (Objects.equals(mail, emailAdmin.getEmail())) {
                try {
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/z11/libraryapp/fxml/ConfirmationPage.fxml")));
                    Stage stage = (Stage) button.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } return mail;
        }
        if (mail == "") {
            informationField.setText("Fill in the fields");
        } return null;

    }

    @FXML
    void initialize() throws UnavailableDB {
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String email = emailTextField.getText();
                SendEmail sendEmail = new SendEmail();
                int number = sendEmail.SendMail(EmailAdmin(email));
                try (PrintStream out = new PrintStream(new FileOutputStream("filename.txt"))) {
                    out.print(number);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Integer id = Integer.parseInt(emailTextField.getText());
                    try {
                        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/z11/libraryapp/fxml/ConfirmationPage.fxml")));
                        Stage stage = (Stage) button.getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(id);
                } catch (NumberFormatException e) {
                    informationField.setText("Write your id (number)");
                    System.err.println("Wrong string format!");
                }
            }
        });
    }
}


