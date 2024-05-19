package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ValutaController {
    @FXML
    private TextField prezzo;
    @FXML
    private ListView<String> listaPreventivi;
    @FXML
    private Button valutaBtn;
    Model model = new Model();
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
                    if (riga.startsWith("Id Preventivo")) {
                        idPreventivo = riga.split(":")[1].trim();
                    }
                }
                //   model.riempiVista(Marca.valueOf(marca),modello,colore,vista1,1);
                //  model.riempiVista(Marca.valueOf(marca),modello,colore,vista2,2);
                //  model.riempiVista(Marca.valueOf(marca),modello,colore,vista3,3);
            }
        });
        model.Numeric(prezzo);
        prezzo.textProperty().addListener((observable, oldValue, newValue) -> {
            valutaBtn.setDisable(newValue.trim().isEmpty());
        });
    }

    public void Valuta(ActionEvent actionEvent) {
        try {
            model.aggiungiValutazione(idPreventivo, Integer.parseInt(prezzo.getText()));
            listaPreventivi.getItems().clear();
            listaPreventivi.getItems().addAll(model.vediPreventivi());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void Cancella(ActionEvent event) throws IOException {
        model.cancella(idPreventivo);
        listaPreventivi.getItems().clear();
        listaPreventivi.getItems().addAll(model.vediPreventivi());
    }

    public void backBtn(ActionEvent event) throws IOException {
        model.OpenCloseFXML("FXML/Dipendente.fxml", event);
    }
}
