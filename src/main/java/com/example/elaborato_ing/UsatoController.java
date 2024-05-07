package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsatoController {
    @FXML
    private TextField marca; // Marca dell'auto

    @FXML
    private TextField modello; // Modello dell'auto

    @FXML
    private TextField altezza; // Altezza dell'auto

    @FXML
    private TextField lunghezza; // Lunghezza dell'auto

    @FXML
    private TextField larghezza; // Larghezza dell'auto

    @FXML
    private TextField peso; // Peso dell'auto

    @FXML
    private TextField volume; // Volume del bagagliaio

    @FXML
    private TextField alimentazione; // Tipo di alimentazione

    @FXML
    private TextField colori; // Colori disponibili

    @FXML
    private Label motore; // Motore

    @FXML
    private CheckBox infot, sensori, fari, sedili, scorta, vetri, interni, ruote, cruise; // Checkbox per gli optional

    @FXML
    private Button caricaImmaginiBtn; // Bottone per caricare immagini
    @FXML
    private HBox imageCarousel;
    @FXML
    private Button loadImagesButton;
    @FXML
    private Button vendiAutoBtn; // Bottone per vendere auto

    private Map<String, List<Path>> immaginiCaricate = new HashMap<>();

    @FXML
    public void caricaImmagini() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Immagini", "*.png", "*.jpg", "*.jpeg"));
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(loadImagesButton.getScene().getWindow());

        if (selectedFiles != null) {
            for (File file : selectedFiles) {
                Image image = new Image(file.toURI().toString());
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(80); // Imposta altezza della miniatura
                imageView.setPreserveRatio(true); // Mantieni proporzioni
                imageCarousel.getChildren().add(imageView);
            }
        }
    }

    @FXML
    public void vendiAuto() {
        String marcaVal = marca.getText().trim();
        String modelloVal = modello.getText().trim();
        String coloreVal = colori.getText().trim();

        String key = marcaVal + modelloVal + coloreVal;
        if (immaginiCaricate.containsKey(key) && immaginiCaricate.get(key).size() >= 3) {
            // Crea l'oggetto Auto usando le informazioni dai campi di testo
            // Esegui altre operazioni necessarie
            System.out.println("Auto pronta per la vendita: " + key);

        } else {
            System.err.println("Devi caricare almeno 3 immagini per vendere l'auto.");
        }
    }

    public void addInfot(ActionEvent actionEvent) {

    }
    public void addSensori(ActionEvent actionEvent) {

    }
    public void addFariFullLED(ActionEvent actionEvent) {

    }
    public void addSediliRiscaldati(ActionEvent actionEvent) {

    }
    public void addScorta(ActionEvent actionEvent) {

    }
    public void addVetri(ActionEvent actionEvent) {

    }
    public void addInterni(ActionEvent actionEvent) {

    }
    public void addRuote(ActionEvent actionEvent) {

    }
    public void addCruiseControl(ActionEvent actionEvent) {

    }

}
