package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class ValutaController {
    @FXML
    private TextField prezzo;
    @FXML
    private ListView listaPreventivi;
    @FXML
    private Button valuta;
    @FXML
    private ImageView vista1,vista2,vista3;
    Model model = new Model();
    private static String statoPreventivo = "";
    private static String idPreventivo = "";
    public void initialize() {
        listaPreventivi.getItems().addAll(model.PreventiviDaValutare());

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
                }
                model.riempiVista(Marca.valueOf(marca),modello,colore,vista1,1);
                model.riempiVista(Marca.valueOf(marca),modello,colore,vista2,2);
                model.riempiVista(Marca.valueOf(marca),modello,colore,vista3,3);
            }
        });
    }

    public void Valuta(ActionEvent actionEvent) {
        try {
            model.aggiungiValutazione(idPreventivo, statoPreventivo, Integer.parseInt(prezzo.getText()));
            listaPreventivi.getItems().clear();
            listaPreventivi.getItems().addAll(model.PreventiviDaValutare());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        model.valuta(prezzo.getText());
    }
}
