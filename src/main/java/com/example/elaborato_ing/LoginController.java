package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

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
        model.OpenCloseFXML("FXML/Registration.fxml", event);
    }

    @FXML
    private void goToLogin(ActionEvent event) throws IOException {
        model.OpenCloseFXML("FXML/Login.fxml", event);
    }

    @FXML
    private void accedi(ActionEvent event) throws IOException {
        String username = emailField.getText();
        String password = passwordField.getText();

        if (model.autenticato(username, password) == 2) {
            System.out.println("Cliente Loggato!");
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.close();
        } else if (model.autenticato(username, password) == 3) {
            System.out.println("Dipendente Loggato!");
            model.OpenCloseFXML("FXML/Dipendente.fxml", event);
        } else if (model.autenticato(username, password) == 1) {
            System.out.println("Amministrazione Loggata!");
            model.OpenCloseFXML("FXML/Segreteria.fxml", event);
        } else
            System.out.println("Credenziali non valide.");

    }

    @FXML
    public void registrati(ActionEvent event) {
        model.registrazione(emailField.getText(), passwordField.getText(), nameField.getText(), surnameField.getText(), event);
    }

    public void setInitController(ConfiguratoreController configuratoreController) {
    }
}
