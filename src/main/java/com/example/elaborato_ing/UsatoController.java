package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
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
    private Button vendiAutoBtn; // Bottone per vendere auto

    private Map<String, List<Path>> immaginiCaricate = new HashMap<>();

    @FXML
    public void caricaImmagini() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleziona le immagini");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Immagini", "*.png", "*.jpg"));

        List<File> files = fileChooser.showOpenMultipleDialog(caricaImmaginiBtn.getScene().getWindow());
        if (files != null && !files.isEmpty()) {
            for (File file : files) {
                String marcaVal = marca.getText().trim();
                String modelloVal = modello.getText().trim();
                String coloreVal = colori.getText().trim();

                if (marcaVal.isEmpty() || modelloVal.isEmpty() || coloreVal.isEmpty()) {
                    System.err.println("Marca, Modello, o Colore non possono essere vuoti.");
                    continue;
                }

                // Costruire il nome del file: marcamodellocolorevista.png
                int vista = immaginiCaricate.getOrDefault(marcaVal + modelloVal + coloreVal, new ArrayList<>()).size() + 1;
                String fileName = marcaVal + modelloVal + coloreVal + vista + ".png";

                Path targetPath = Path.of("src/main/resources/com/example/elaborato_ing/images/" + fileName);
                try {
                    Files.copy(file.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Immagine caricata: " + fileName);

                    // Aggiungi alla lista delle immagini caricate
                    immaginiCaricate.computeIfAbsent(marcaVal + modelloVal + coloreVal, k -> new ArrayList<>()).add(targetPath);

                } catch (Exception e) {
                    System.err.println("Errore nel copiare l'immagine: " + e.getMessage());
                }
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
