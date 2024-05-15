package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
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
    private Hyperlink registerHL;
    @FXML
    private Hyperlink loginHL;
    @FXML
    private Button accedi;


    private Model model;

    public void initialize() {
        model = new Model();

    }


    @FXML
    private void goToRegistration() {
        model.OpenCloseFXML("FXML/Registration.fxml", registerHL);
    }

    @FXML
    private void goToLogin() {
        model.OpenCloseFXML("FXML/Login.fxml", loginHL);
    }

    @FXML
    public void accedi() throws IOException {
        String username = emailField.getText();
        String password = passwordField.getText();

        if (model.autenticato(username, password) == 0) {
            System.out.println("Cliente Loggato!");
            System.out.println(model.getClienteLoggato());
            ((Stage) accedi.getScene().getWindow()).close(); // Chiude la scena iniziale

        } else if (model.autenticato(username, password) == 1) {
            System.out.println("Dipendente Loggato!");
            model.OpenCloseFXML("FXML/Dipendente.fxml", accedi);
        } else if(model.autenticato(username, password) == 2) {
            System.out.println("Amministrazione Loggata!");
            model.OpenCloseFXML("FXML/Amministrazione.fxml", accedi);
        }else
            System.out.println("Credenziali non valide.");


    }


    public void registrati() {
        model.Registrazione(emailField.getText(), passwordField.getText(), nameField.getText(), surnameField.getText());
    }


}
