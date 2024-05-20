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
    private ListView<String> listaPreventivi;

    private final Model model = new Model();
    private static String idPreventivo = "";


    public void initialize() {
        listaPreventivi.getItems().addAll(model.vediPreventivi(model.getCliente().getEmail()));

        listaPreventivi.getSelectionModel().selectedItemProperty().addListener((_, oldValue, newValue) -> {
            if (newValue != null) { // Verifica se il nuovo valore selezionato non è nullo
                String[] utili = newValue.split("\n");
                String marca = "";
                String modello = "";
                String colore = "";
                String statoPreventivo = "";
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
                    if (riga.startsWith("Id Preventivo")) {
                        idPreventivo = riga.split(":")[1].trim();
                    }
                    if (riga.startsWith("Stato Preventivo")) {
                        statoPreventivo = riga.split(":")[1].trim();
                    }
                }
                String path;
                if (statoPreventivo.equals(String.valueOf(Stato.DA_VALUTARE)) || statoPreventivo.equals(String.valueOf(Stato.VALUTATA)))
                    path = model.getImmagineAuto(Marca.valueOf(marca), modello, colore, 1, 1,"");
                else
                    path = model.getImmagineAuto(Marca.valueOf(marca), modello, colore, 1, 0,"");

                System.out.println(path);
                InputStream imageStream = getClass().getResourceAsStream(path);
                if (imageStream != null) {
                    Image image = new Image(imageStream);
                    macchinaPreventivo.setImage(image);
                }
            }
        });
    }

    public void paga() {
        try {
            model.aggiungiPagamento(idPreventivo);
            listaPreventivi.getItems().clear();
            listaPreventivi.getItems().addAll(model.vediPreventivi(model.getCliente().getEmail()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}