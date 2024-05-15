package com.example.elaborato_ing;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Carica il file FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/Configuratore.fxml"));
        Parent root = loader.load();

        // Ottieni il controller
        InitController controller = loader.getController();

        // Passa lo stage al controller
        controller.setStage(primaryStage);


        // Imposta il titolo e la scena e mostra lo stage
        primaryStage.setTitle("JavaFX App with FXML and CSS");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
