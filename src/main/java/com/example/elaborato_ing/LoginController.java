package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
    private void goToRegistration(ActionEvent event) {
        model.loadScene("FXML/Registration.fxml", event);
    }

    @FXML
    private void goToLogin(ActionEvent event) {
        model.loadScene("FXML/Login.fxml", event);
    }


    @FXML
    public void accedi() throws IOException {
        String username = emailField.getText();
        String password = passwordField.getText();


        if (model.autenticato(username, password)) {
            System.out.println("Login successful!");

        } else {
            System.out.println("Credenziali non valide.");
        }

    }


    public void registrati() {
        model.Registrazione(emailField.getText(), passwordField.getText(), nameField.getText(), surnameField.getText());
    }


}
