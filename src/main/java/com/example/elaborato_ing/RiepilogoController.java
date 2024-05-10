package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;

public class RiepilogoController {
    @FXML
    private ListView listaPreventivi;
    private final Model model = new Model();

    public void initialize() {
       listaPreventivi.getItems().addAll(model.inizializzaPreventivo());
    }

    public void paga(ActionEvent actionEvent) {
       /* String preventivoSelezionato = listaPreventivi.getSelectionModel().getSelectedItem();
        if (preventivoSelezionato != null) {
            // Aggiungi "pagato" alla fine della riga selezionata
            model.aggiungiPagamento(preventivoSelezionato);
            // Aggiorna la ListView
            model.inizializzaPreventivo();
            */

        }
    }

