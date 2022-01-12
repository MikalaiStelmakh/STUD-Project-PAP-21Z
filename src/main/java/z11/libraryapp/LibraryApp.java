package z11.libraryapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LibraryApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        MqttSubscriber sub = new MqttSubscriber();
        sub.run();

        FXMLLoader fxmlLoader = new FXMLLoader(LibraryApp.class.getResource("fxml/SignIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 400);
        scene.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());
        stage.setTitle("LibraryApp");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            sub.stop();
            System.exit(0);
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}