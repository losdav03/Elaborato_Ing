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
    private Button loadImagesButton;

    @FXML
    private HBox imageCarousel;

    @FXML
    private ImageView imageView1, imageView2, imageView3;

    @FXML
    private ScrollPane scrollPane;


    private final List<Image> imageList = new ArrayList<>();


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

    public void caricaImmagine() throws FileNotFoundException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Immagini", "*.png"));

        File file = fileChooser.showOpenDialog(imageView1.getScene().getWindow());

        if (file != null) {
            InputStream imageStream = new FileInputStream(file); // Usiamo FileInputStream per caricare l'immagine correttamente
            Image image = new Image(imageStream);
            imageView1.setImage(image);
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

