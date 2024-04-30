package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

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
    private void goToLogin(ActionEvent event) throws IOException {
        // Carica il file FXML della pagina di login
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
    public void accedi(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            if (autenticato(username, password)) {
                System.out.println("Login successful!");
            } else {
                System.out.println("Credenziali non valide.");
            }
        } catch (IOException e) {

            System.err.println("Errore durante la lettura del file di login: " + e.getMessage());
        }
    }
@FXML
    private boolean autenticato(String username, String password) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("LoginFile.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String storedUsername = parts[0];
                    String storedPassword = parts[3];
                    if (storedUsername.equals(username) && storedPassword.equals(password)) {
                        return true;
                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }
}
