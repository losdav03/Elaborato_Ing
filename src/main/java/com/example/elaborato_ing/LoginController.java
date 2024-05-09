package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;

public class LoginController {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;

    private final Model model = new Model();


    @FXML
    private void goToRegistration(ActionEvent event) throws IOException {
        model.loadScene("FXML/Registration.fxml", event);
    }

    @FXML
    private void goToLogin(ActionEvent event) throws IOException {
        model.loadScene("FXML/Login.fxml", event);
    }


    @FXML
    public void accedi(ActionEvent event) {
        String username = emailField.getText();
        String password = passwordField.getText();

        try {
            if (autenticato(username, password)) {
                System.out.println("Login successful!");
            } else {
                System.out.println("Credenziali non valide.");
            }
        } catch (
                IOException e) {
            System.err.println("Errore durante la lettura del file di login: " + e.getMessage());
        }
    }


    private boolean autenticato(String username, String password) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("src\\main\\resources\\com\\example\\elaborato_ing\\TXT\\LoginFile.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parti = line.split(",");
                if (parti.length == 4 && parti[0].equals(username) && parti[3].equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void registrati(ActionEvent actionEvent) {

        Cliente cliente = new Cliente(emailField.getText(), nameField.getText(), surnameField.getText(), passwordField.getText(), 1);


        if (!cliente.getEmail().isEmpty() && !cliente.getNome().isEmpty() && !cliente.getCognome().isEmpty() && !cliente.getPassword().isEmpty()) {
            // Apertura del file in modalità append
            try (FileWriter writer = new FileWriter("src\\main\\resources\\com\\example\\elaborato_ing\\TXT\\LoginFile.txt", true)) {

                // controllo se l'utente è già inserito nel file login, si controlla solo l'email, quella è la chiave e deve essere unica
                if (utenteEsiste(cliente.getEmail())) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Attenzione");
                    alert.setHeaderText("L'utente sembra già essere registrato");
                    alert.setContentText("Questa email esiste già");
                    alert.showAndWait();
                } else {
                    // Scrivi i dati dell'utente nel file, separati da virgole
                    writer.write(cliente.getEmail() + "," + cliente.getNome() + "," + cliente.getCognome() + "," + cliente.getPassword() + "\n");

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Successo");
                    alert.setHeaderText("Registrazione avvenuta con successo");
                    alert.setContentText("Messaggio dettagliato sull'errore.");

                    // Gestione dell'azione del bottone OK
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            model.loadScene("FXML/Login.fxml", null);
                        }
                    });

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Devi completare tutti i campi prima di registrarti");
            alert.setContentText("Messaggio dettagliato sull'errore.");

            // Aggiunta di un pulsante "OK" al box di errore
            alert.getButtonTypes().setAll(ButtonType.OK);

            // Visualizzazione del box di errore
            alert.showAndWait();
        }


    }

    // se trovo l'email allora return TRUE se no FALSE
    private boolean utenteEsiste(String email) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("src\\main\\resources\\com\\example\\elaborato_ing\\TXT\\LoginFile.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parti = line.split(",");
                if (parti[0].equals(email)) {
                    return true;
                }
            }
        }
        return false;
    }
}
