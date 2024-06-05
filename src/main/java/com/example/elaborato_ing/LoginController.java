package com.example.elaborato_ing;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

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
    private Model model = Model.getInstance();
    @FXML
    private void goToRegistration(ActionEvent event) throws IOException {
        model.openCloseFXML("FXML/Registration.fxml", event);
    }

    @FXML
    private void goToLogin(ActionEvent event) throws IOException {
        model.openCloseFXML("FXML/Login.fxml", event);
    }

    @FXML
    private void accedi(ActionEvent event) throws IOException {
        String username = emailField.getText();
        String password = passwordField.getText();

        if (model.autenticato(username, password) == 2) {
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.close();
        } else if (model.autenticato(username, password) == 3) {
            model.openCloseFXML("FXML/Dipendente.fxml", event);
        } else if (model.autenticato(username, password) == 1) {
            model.openCloseFXML("FXML/Segreteria.fxml", event);
            Platform.runLater(this::closeAllWindows);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attenzione");
            alert.setHeaderText("Credenizali errate");
            alert.showAndWait();
        }
    }

    public void closeAllWindows() {
        for (Window window : Stage.getWindows()) {
            if (window instanceof Stage) {
                ((Stage) window).close();
            }
        }
    }

    @FXML
    public void registrati(ActionEvent event) {
        model.registrazione(emailField.getText(), nameField.getText(), surnameField.getText(), passwordField.getText(), event);
    }

    public void setInitController(ConfiguratoreController configuratoreController) {
    }
}
