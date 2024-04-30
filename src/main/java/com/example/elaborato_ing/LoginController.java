package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private void goToRegistration(ActionEvent event) throws IOException {
        // Carica il file FXML della pagina di registrazione
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Registration.fxml"));
        Parent root = loader.load();

        // Crea la scena
        Scene scene = new Scene(root);

        // Ottieni il palcoscenico corrente
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        // Imposta la nuova scena
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void goToLogin(ActionEvent event) throws IOException {
        // Carica il file FXML della pagina di registrazione
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();

        // Crea la scena
        Scene scene = new Scene(root);

        // Ottieni il palcoscenico corrente
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        // Imposta la nuova scena
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;



    @FXML
    public void accedi(ActionEvent event) throws IOException {

        String username = usernameField.getText();
        String password = passwordField.getText();

        boolean authenticated = autenticato(username, password);
        if (authenticated) {
            System.out.println("Login successful!");
        } else
            System.out.println("Credenziali non valide.");


    // Carica il file FXML della pagina di registrazione
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();

        // Crea la scena
        Scene scene = new Scene(root);

        // Ottieni il palcoscenico corrente
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        // Imposta la nuova scena
        stage.setScene(scene);
        stage.show();
    }

    private boolean autenticato(String username, String password) {

    }

    // Altri metodi del controller
    // ...
}

