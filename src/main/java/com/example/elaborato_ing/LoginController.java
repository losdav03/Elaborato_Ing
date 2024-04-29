package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
public class LoginController {

    @FXML
    private void goToRegistration(ActionEvent event) throws IOException {
        // Carica il file FXML della pagina di registrazione
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Registrazione.fxml"));
        Parent root = loader.load();

        // Crea la scena
        Scene scene = new Scene(root);

        // Ottieni il palcoscenico corrente
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        // Imposta la nuova scena
        stage.setScene(scene);
        stage.show();
    }

    // Altri metodi del controller
    // ...
}

