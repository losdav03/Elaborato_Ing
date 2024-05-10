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
    public ImageView macchinaPreventivo;
    @FXML
    private ListView listaPreventivi;
    private final Model model = new Model();
    private Marca marca;
    private String modello;


    public void initialize() {
        listaPreventivi.getItems().addAll(model.inizializzaPreventivo());


        listaPreventivi.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Qui puoi fare qualcosa con il valore selezionato, ad esempio stamparlo
            System.out.println(observable.getValue().toString());
            String[] utili = observable.getValue().toString().split("\n");
            String path = "/com/example/elaborato_ing/images/";
            for (String riga : utili) {
                if (riga.startsWith("Marca")) {
                    String marca = riga.split(":")[1].trim().toLowerCase();
                    path += marca;
                    System.out.println(marca);
                }
                if (riga.startsWith("Modello")) {
                    String ollare = riga.split(":")[1].trim().toLowerCase();
                    path += ollare;
                    System.out.println(ollare);
                }
                if (riga.startsWith("Colore")) {
                    String colore = riga.split(":")[1].trim().toLowerCase();
                    path += colore;
                    System.out.println(colore);
                }
            }
            path += "1.png";
            Image image = new Image(getClass().getResourceAsStream(path));
            macchinaPreventivo.setImage(image);
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

