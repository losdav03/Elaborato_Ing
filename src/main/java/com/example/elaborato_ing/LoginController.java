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


    private Model model;

    public void initialize() {
         model = new Model();

    }


    @FXML
    private void goToRegistration() {
        model.OpenCloseFXML("FXML/Registration.fxml",registerHL);
    }

    @FXML
    private void goToLogin() {
        model.OpenCloseFXML("FXML/Login.fxml",loginHL);
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
