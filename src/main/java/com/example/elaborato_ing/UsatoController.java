package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private Button loadImagesButton;

    @FXML
    private HBox imageCarousel;

    @FXML
    private ScrollPane scrollPane;

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

    public void caricaImmagini(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Immagini", "*.png"));
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(loadImagesButton.getScene().getWindow());

        if (selectedFiles != null) {
            for (File file : selectedFiles) {
                try {
                    Image image = new Image(file.toURI().toString());
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(100); // Imposta l'altezza per ridimensionare le miniature
                    imageView.setPreserveRatio(true); // Mantieni le proporzioni
                    imageCarousel.getChildren().add(imageView); // Aggiungi al carosello
                } catch (Exception e) { // Gestisci eventuali errori
                    System.err.println("Errore durante il caricamento dell'immagine: " + file.getName() + ", motivo: " + e.getMessage());
                }
            }
        }
        else {
            System.out.println("Nessun file selezionato.");
        }
    }
}
