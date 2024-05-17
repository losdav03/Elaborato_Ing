package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
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
    @FXML
    private Hyperlink registerHL;
    @FXML
    private Hyperlink loginHL;
    @FXML
    private Button accedi, registrati;

    private Model model;
    private InitController initController;

    public void initialize() {
        model = new Model();


    }

    // SERVE NON ELIMINARE
    public void setMainWindow(Stage mainWindow) {
    }

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
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.close();
        } else {
            System.out.println("Errore durante la autenticazione");
        }



        /*
        String username = emailField.getText();
        String password = passwordField.getText();

        model.autenticato(username, password);
        if (model.getCliente().getEmail() != null) {
            System.out.println("Cliente Loggato!");
            Stage loginWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
            loginWindow.close();
        } else if (model.getDipendente().getEmail() != null) {
            System.out.println("Dipendente Loggato!");
            model.OpenCloseFXML("FXML/Dipendente.fxml", event);
        } else if (model.getAmministrazione().getEmail() != null) {
            System.out.println("Amministrazione Loggata!");
            model.OpenCloseFXML("FXML/Amministrazione.fxml", event);
        } else
            System.out.println("Credenziali non valide.");
            */
    }
    public void setInitController(InitController initController) {
        this.initController = initController;
    }

    @FXML
    public void registrati(ActionEvent event) {
        model.Registrazione(emailField.getText(), passwordField.getText(), nameField.getText(), surnameField.getText(), event);
    }

}
