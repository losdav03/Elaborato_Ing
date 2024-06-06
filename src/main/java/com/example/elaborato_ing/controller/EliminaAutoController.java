package com.example.elaborato_ing.controller;

import com.example.elaborato_ing.utils.Auto;
import com.example.elaborato_ing.utils.AutoNuova;
import com.example.elaborato_ing.utils.Marca;
import com.example.elaborato_ing.model.Model;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class EliminaAutoController {
    @FXML
    private ComboBox<Marca> marca;
    @FXML
    private Button elimina;
    @FXML
    private ComboBox<String> modello;
    @FXML
    private ImageView autoImg;

    private Model model = Model.getInstance();
    public void initialize() {
        model.setMarca(marca);
        marca.setOnAction(_ -> aggiornaModello());
        modello.setOnAction(_ -> {
            try {
                aggiornaImg();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        model.caricaDaFile("src/main/resources/com/example/elaborato_ing/TXT/Catalogo.txt", model.getCatalogo());
        model.aggiornaFileCatalogo();
        elimina.setDisable(true);
        model.onCloseFXML("/com/example/elaborato_ing/viewSegreteria/Segreteria.fxml", marca);
    }

    private void aggiornaImg() throws FileNotFoundException {
        if (marca.getValue() != null && modello.getValue() != null) {
            String imagePath = model.getImmagineAuto(marca.getValue(), String.valueOf(modello.getValue()), (model.getMarcaModelloAutoNuova(marca.getValue(), String.valueOf(modello.getValue()), model.getMapAutoNuova())).getColori().getFirst(), 1, 0, "");
            File file = new File(imagePath);
            FileInputStream stream = new FileInputStream(file);
            Image image = new Image(stream);
            autoImg.setImage(image);
            elimina.setDisable(false);
        }
    }

    private void aggiornaModello() {
        model.aggiornaFileCatalogo();
        model.caricaDaFile("src/main/resources/com/example/elaborato_ing/TXT/Catalogo.txt", model.getCatalogo());
        List<AutoNuova> listaAuto = model.getMapAutoNuova().getOrDefault(marca.getValue(), Collections.emptyList());
        List<String> listaModelli = listaAuto.stream().map(Auto::getModello).distinct().toList();

        if (listaModelli.isEmpty()) {
            modello.getItems().clear();
            modello.setDisable(true);
            elimina.setDisable(true);
        } else {
            modello.getItems().setAll(listaModelli);
            modello.setDisable(false);
            elimina.setDisable(true);
            autoImg.setImage(null);
        }
    }

    public void elimina() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confermi la rimozione dell'auto?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                model.getCatalogo().remove(marca.getValue(), String.valueOf(modello.getValue()));
                model.aggiornaFileCatalogo();
                model.caricaDaFile("src/main/resources/com/example/elaborato_ing/TXT/Catalogo.txt", model.getCatalogo());
                model.setMarca(marca);
                autoImg.setImage(null);
            }
        });

    }
}
