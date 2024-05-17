package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;

public class RiepilogoController {
    @FXML
    public ImageView macchinaPreventivo;
    @FXML
    private ListView listaPreventivi;
    @FXML
    private Button pagaBtn;
    private final Model model = new Model();
    private static String statoPreventivo = "";
    private static String idPreventivo = "";


    public void initialize() {
        listaPreventivi.getItems().addAll(model.vediPreventivi());

        listaPreventivi.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) { // Verifica se il nuovo valore selezionato non è nullo
                String[] utili = newValue.toString().split("\n");
                String marca = "";
                String modello = "";
                String colore = "";
                for (String riga : utili) {
                    if (riga.startsWith("Marca")) {
                         marca = riga.split(":")[1].trim();
                    }
                    if (riga.startsWith("Modello")) {
                         modello = riga.split(":")[1].trim();
                    }
                    if (riga.startsWith("Colore")) {
                         colore = riga.split(":")[1].trim().toLowerCase();
                    }
                    if (riga.startsWith("Stato Preventivo")) {
                        statoPreventivo = riga.split(":")[1].trim().toLowerCase();
                    }
                    if (riga.startsWith("Id Preventivo")) {
                        idPreventivo = riga.split(":")[1].trim();
                    }
                }
                String path = model.getImmagineAuto(Marca.valueOf(marca),modello,colore,1);
                InputStream imageStream = getClass().getResourceAsStream(path);
                if (imageStream != null) {
                    Image image = new Image(imageStream);
                    macchinaPreventivo.setImage(image);
                }
            }
        });
    }

    public void paga(ActionEvent actionEvent) {
        try {
            model.aggiungiPagamento(idPreventivo);
            listaPreventivi.getItems().clear();
            listaPreventivi.getItems().addAll(model.vediPreventivi());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}