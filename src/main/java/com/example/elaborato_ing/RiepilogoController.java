package com.example.elaborato_ing;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;

public class RiepilogoController {
    @FXML
    private ListView listaPreventivi;
    private final Model model = new Model();

    @FXML
    private ImageView img;

    private Marca marca;
    private String modello;


    public void initialize() {
        listaPreventivi.getItems().addAll(model.inizializzaPreventivo());


        listaPreventivi.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Qui puoi fare qualcosa con il valore selezionato, ad esempio stamparlo
            String[] utili = observable.getValue().toString().split(System.lineSeparator());
            String path = "src/main/resources/com/example/elaborato_ing/images/";
            for (String riga : utili) {
                if (riga.startsWith("Marca")) {
                    String marca = riga.split(":")[1].trim().toLowerCase();
                    path += marca;
                }
                else if (riga.startsWith("Modello")) {
                    String modello = riga.split(":")[1].trim().toLowerCase();
                    path += modello;
                }
                else if (riga.startsWith("Colore")) {
                    String colore = riga.split(":")[1].trim().toLowerCase();
                    path += colore;
                }
            }
            path += "1.png";
            Image image = new Image(path);
            img.setImage(image);
        });
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

