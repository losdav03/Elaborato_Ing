package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;

public class AvvisaController {
    @FXML
    private ListView listaPreventivi;
    @FXML
    private Button Avvisa;
    private static String idPreventivo = "";
    Model model = new Model();

    public void initialize() {
        listaPreventivi.getItems().addAll(model.vediPreventivi());
        listaPreventivi.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) { // Verifica se il nuovo valore selezionato non è nullo
                String[] utili = newValue.toString().split("\n");
                for (String riga : utili) {
                    if (riga.startsWith("Id Preventivo")) {
                        idPreventivo = riga.split(":")[1].trim();
                    }
                }
            }
        });
    }

    public void Avvisa(ActionEvent actionEvent) {
        model.avvisa(idPreventivo);
    }

    public void back(ActionEvent event) throws IOException {
        model.OpenCloseFXML("src/main/resources/com/example/elaborato_ing/FXML/Dipendente.fxml", event);
    }
}
