package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsatoController {

    @FXML
    private ImageView imageView1, imageView2, imageView3;
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
    @FXML
    public void caricaImgs() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleziona un'immagine");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Immagini", "*.png"));

        File Immagine = fileChooser.showOpenDialog(imageView1.getScene().getWindow());
        if (Immagine != null) {
            Image image = new Image(Immagine.toURI().toString());
            if (imageView1.getImage() == null) {
                imageView1.setImage(image);
            } else if (imageView2.getImage() == null) {
                imageView2.setImage(image);
            } else {
                imageView3.setImage(image);
            }
        }
    }

    @FXML
    public void RimuoviImgs() {
        // Trova il primo ImageView con un'immagine e rimuovila
        if (imageView3.getImage() != null) {
            imageView3.setImage(null);
        } else if (imageView2.getImage() != null) {
            imageView2.setImage(null);
        } else if (imageView1.getImage() != null) {
            imageView1.setImage(null);
        } else {
            System.out.println("Tutte le ImageView sono già vuote.");
        }
    }
}
    /*
    public void caricaImmagini(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Immagini", "*.png"));
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);

        if (selectedFiles != null) {
            for (File file : selectedFiles) {
                try {
                    InputStream imageStream = new FileInputStream(file);
                    if (imageStream != null) {
                        Image image = new Image(imageStream);
                        imageList.add(image);
                    }
                } catch (Exception e) {
                    System.err.println("Errore durante il caricamento dell'immagine: " + file.getName() + ", motivo: " + e.getMessage());
                }
            }
        } else {
            System.out.println("Nessun file selezionato.");
        }
    }

    @FXML
    public void mostraPrimaImmagine(ActionEvent actionEvent) {
        if (!imageList.isEmpty()) {
            imageView.setImage(imageList.get(0));
        }
    }

    @FXML
    public void mostraImmagineSuccessiva(ActionEvent actionEvent) {
        if (!imageList.isEmpty()) {
            int currentIndex = imageList.indexOf(imageView.getImage());
            int nextIndex = (currentIndex + 1) % imageList.size();
            imageView.setImage(imageList.get(nextIndex));
        }
    }

    @FXML
    public void mostraImmaginePrecedente(ActionEvent actionEvent) {
        if (!imageList.isEmpty()) {
            int currentIndex = imageList.indexOf(imageView.getImage());
            int prevIndex = (currentIndex - 1 + imageList.size()) % imageList.size();
            imageView.setImage(imageList.get(prevIndex));
        }
    }

     */

