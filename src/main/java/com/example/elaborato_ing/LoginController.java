package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

    @FXML
    private void goToRegistration(ActionEvent event) throws IOException {
        loadScene("Registration.fxml", event);
    }

    @FXML
    private void goToLogin(ActionEvent event) throws IOException {
        loadScene("Login.fxml", event);
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
        } catch (IOException e) {
            System.err.println("Errore durante la lettura del file di login: " + e.getMessage());
        }
    }

    private void loadScene(String fxmlFile, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private boolean autenticato(String username, String password) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\dlosc\\IdeaProjects\\Elaborato_Ing\\src\\main\\resources\\com\\example\\elaborato_ing\\LoginFile"))) {
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


        if (cliente.getEmail().length() > 0 && cliente.getPassword().length() > 0 && cliente.getEmail().length() > 0 && cliente.getPassword().length() > 0) {
            // Apertura del file in modalità append
            try (FileWriter writer = new FileWriter("C:\\Users\\dlosc\\IdeaProjects\\Elaborato_Ing\\src\\main\\resources\\com\\example\\elaborato_ing\\LoginFile", true)) {
                // Scrivi i dati dell'utente nel file, separati da virgole
                writer.write(cliente.getEmail() + "," + cliente.getNome() + "," + cliente.getCognome() + "," + cliente.getPassword() + "\n");

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Successo");
                alert.setHeaderText("Registrazione avvenuta con successo");
                alert.setContentText("Messaggio dettagliato sull'errore.");
                alert.showAndWait();
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
}
