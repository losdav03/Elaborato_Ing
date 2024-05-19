package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;

public class AvvisaController {
    @FXML
    private ListView<String> listaPreventivi;

    private static String idPreventivo = "";

    Model model = new Model();

    public void initialize() {
        listaPreventivi.getItems().addAll(model.vediPreventivi());
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

    public void Avvisa() {
        model.avvisa(idPreventivo);
    }

    public void backBtn(ActionEvent event) throws IOException {
        model.OpenCloseFXML("FXML/Dipendente.fxml", event);
    }
}
