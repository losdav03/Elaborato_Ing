package com.example.elaborato_ing;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class EliminaAutoController {
    @FXML
    private ComboBox<Marca> marca;
    @FXML
    private ComboBox<String> modello;
    @FXML
    private ImageView autoImg;

    Model model = new Model();

    public void initialize() {
        model.setMarca(marca);
        marca.setOnAction(_ -> aggiornaModello());
        modello.setOnAction(_ -> aggiornaImg());
        model.aggiornaFileCatalogo();
    }

    private void aggiornaImg() {
        if (marca.getValue() != null && modello.getValue() != null) {
            InputStream imageStream = getClass().getResourceAsStream(model.getImmagineAuto(Marca.valueOf(String.valueOf(marca.getValue())), String.valueOf(modello.getValue()), (model.getMarcaModelloAutoNuova(Marca.valueOf(String.valueOf(marca.getValue())), String.valueOf(modello.getValue()), model.getMapAutoNuova())).getColori().getFirst(), 1, 0,""));
            if (imageStream != null) {
                Image image = new Image(imageStream);
                autoImg.setImage(image);
            }
        }
    }

    private void aggiornaModello() {
        List<AutoNuova> listaAuto = model.getMapAutoNuova().getOrDefault(marca.getValue(), Collections.emptyList());
        List<String> listaModelli = listaAuto.stream().map(Auto::getModello).distinct().toList();

        modello.getItems().clear();
        modello.getItems().setAll(listaModelli);
        modello.setDisable(false);
    }

    public void elimina() {
        model.getCatalogo().remove(marca.getValue(), String.valueOf(modello.getValue()));
        model.aggiornaFileCatalogo();
    }
}
