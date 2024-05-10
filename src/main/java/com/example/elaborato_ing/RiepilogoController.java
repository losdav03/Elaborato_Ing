package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;

public class RiepilogoController {
    @FXML
    private ListView<String> listView;
    private Model model = new Model();

    public void InizializzaPreventivi(){
        model.inizializzaPreventivo(listView);
    }
    public void paga(ActionEvent actionEvent) {
        String preventivoSelezionato = listView.getSelectionModel().getSelectedItem();
        if (preventivoSelezionato != null) {
            // Aggiungi "pagato" alla fine della riga selezionata
            model.aggiungiPagamento(preventivoSelezionato);
            // Aggiorna la ListView
            model.inizializzaPreventivo(listView);
        }
    }
}
