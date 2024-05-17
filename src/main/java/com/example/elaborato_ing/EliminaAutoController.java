package com.example.elaborato_ing;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.event.ActionEvent;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class EliminaAutoController {
    @FXML
    private ComboBox marca,modello;
    @FXML
    private Button elimina;
    @FXML
    private ImageView autoImg;
    Model model = new Model();

    public void initialize() {
        model.setMarca(marca);
        marca.setOnAction(_ -> aggiornaModello());
        modello.setOnAction(_ -> aggiornaImg());
    }

    private void aggiornaImg() {
        if (marca.getValue() != null && modello.getValue() != null) {
            InputStream imageStream = getClass().getResourceAsStream(model.getImmagineAuto(Marca.valueOf(String.valueOf(marca.getValue())), String.valueOf(modello.getValue()), (model.getMarcaModello(Marca.valueOf(String.valueOf(marca.getValue())), String.valueOf(modello.getValue()), model.getMap())).getColori().getFirst(), 1));
            if (imageStream != null) {
                Image image = new Image(imageStream);
                autoImg.setImage(image);
            }
        }
    }

    private void aggiornaModello() {
        List<AutoNuova> listaAuto = model.getMap().getOrDefault(marca.getValue(), Collections.emptyList());
        List<String> listaModelli = listaAuto.stream().map(Auto::getModello).distinct().toList();

        modello.getItems().clear();
        modello.getItems().setAll(listaModelli);
        modello.setDisable(false);
    }

    public void elimina(javafx.event.ActionEvent event) {
        model.getCatalogo().remove((Marca) marca.getValue(), String.valueOf(modello.getValue()));
        model.aggiornaFileCatalogo();
    }
}