package com.example.elaborato_ing.controller;

import com.example.elaborato_ing.model.Model;
import com.example.elaborato_ing.utils.Stato;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;

public class AvvisaController {
    @FXML
    private ListView<String> listaPreventivi;

    private static String idPreventivo = "";

    private Model model = Model.getInstance();

    public void initialize() {
        listaPreventivi.getItems().addAll(model.vediPreventivi(String.valueOf(Stato.PAGATO)));
        listaPreventivi.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) { // Verifica se il nuovo valore selezionato non è nullo
                String[] utili = newValue.split("\n");
                for (String riga : utili) {
                    if (riga.startsWith("Id Preventivo")) {
                        idPreventivo = riga.split(":")[1].trim();
                    }
                }
            }
        });
    }

    @FXML
    public void avvisa() {
        model.avvisa(idPreventivo);
        listaPreventivi.getItems().clear();
        listaPreventivi.getItems().addAll(model.vediPreventivi(String.valueOf(Stato.PAGATO)));
    }

    @FXML
    public void backBtn(ActionEvent event) throws IOException {
        model.openCloseFXML("/com/example/elaborato_ing/viewDipendente/Dipendente.fxml", event);
    }
}
