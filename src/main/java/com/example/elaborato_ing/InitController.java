package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class InitController {
    @FXML
    private Label altezza, lunghezza, larghezza, peso, volume, alimentazione, motore, prezzo;

    @FXML
    private ComboBox<String> marca, modello, colori;

    @FXML
    private CheckBox infot, sensori, fari, sedili, scorta, vetri, interni, ruote, cruise;

    @FXML
    private Button acquistabtn, vendibtn;

    @FXML
    private ImageView img;

    public void initialize() {
        String vuota = "";
        marca.getItems().addAll("DODGE", "FERRARI", "LAMBORGHINI", "TESLA", "AUDI", "JEEP");
        colori.getItems().addAll("BIANCO", "NERO", "ROSSO");
        altezza.setText(vuota);
        lunghezza.setText(vuota);
        larghezza.setText(vuota);
        peso.setText(vuota);
        volume.setText(vuota);
        alimentazione.setText(vuota);
        motore.setText(vuota);
        prezzo.setText(vuota);
        marca.setOnAction(e -> aggiornaModello());
        modello.setOnAction(e -> aggiornaImg());
    }

    private void aggiornaImg() {
        String marcaS = marca.getValue();
        String modelloS = modello.getValue();

        if (marcaS != null && modelloS != null) {
            String imagePath = "/com/example/elaborato_ing/images/" + marcaS + modelloS + ".png";
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            img.setImage(image);
        }
    }

    private void aggiornaModello() {
        String marcaS = marca.getValue();
        if (marcaS != null) {
            modello.setDisable(false);
            modello.getItems().clear();
            switch (marcaS) {
                case "DODGE":
                    modello.getItems().addAll("CHARGER", "CHALLENGER", "DURANGO");
                    break;
                case "FERRARI":
                    modello.getItems().addAll("SF90", "PORTOFINO", "GTS296");
                    break;
                case "LAMBORGHINI":
                    modello.getItems().addAll("URUS", "REVUELTO", "HURUCAN");
                    break;
                case "TESLA":
                    modello.getItems().addAll("MODELX", "MODELY", "CYBERTRUCK");
                    break;
                case "AUDI":
                    modello.getItems().addAll("Q8etron", "A8", "RS3");
                    break;
                case "JEEP":
                    modello.getItems().addAll("RENEGADE", "COMPASS", "GLADIETOR");
                    break;
            }
        } else {
            modello.setDisable(true);
        }
    }

    public void goToUsatoForm(ActionEvent event) {
        loadScene("Usato.fxml", event);
    }


    public void loadScene(String fxmlFile, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            if (event != null && event.getSource() instanceof Node) {
                // Se l'evento non è null e proviene da un Node
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                primaryStage.close(); // Chiudi la finestra precedente se necessario
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            }
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
