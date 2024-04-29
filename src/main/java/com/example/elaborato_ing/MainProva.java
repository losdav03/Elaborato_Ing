package com.example.elaborato_ing;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainProva extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carica il file FXML della finestra di login
        Parent root = FXMLLoader.load(getClass().getResource("com/example/elaborato_ing/Login.fxml"));

        // Crea la scena
        Scene scene = new Scene(root);

        // Imposta il titolo della finestra
        primaryStage.setTitle("Login");

        // Imposta la scena nella finestra
        primaryStage.setScene(scene);

        // Mostra la finestra
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

