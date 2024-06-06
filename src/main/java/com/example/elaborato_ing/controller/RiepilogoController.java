package com.example.elaborato_ing.controller;

import com.example.elaborato_ing.model.Model;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.io.*;

public class RiepilogoController {
    @FXML
    public ImageView macchinaPreventivo;
    @FXML
    private ListView<String> listaPreventivi;
    private Model model = Model.getInstance();
    private static String idPreventivo = "";


    public void initialize() {
        listaPreventivi.getItems().addAll(model.vediPreventivi(model.getCliente().getEmail()));

        listaPreventivi.getSelectionModel().selectedItemProperty().addListener((_, oldValue, newValue) -> {
            if (newValue != null) { // Verifica se il nuovo valore selezionato non è nullo
                String[] utili = newValue.split("\n");
                for (String riga : utili) {
                    if (riga.startsWith("Id Preventivo")) {
                        idPreventivo = riga.split(":")[1].trim();
                    }
                }
                model.setImageViewPreventivi(idPreventivo, macchinaPreventivo, 1);
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