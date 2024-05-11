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
    @FXML
    private Button pagaBtn;
    private final Model model = new Model();
    private Marca marca;
    private String modello;
    private static String statoPreventivo = "";
    private static String idPreventivo = "";


    public void initialize() {
        listaPreventivi.getItems().addAll(model.inizializzaPreventivo());

        listaPreventivi.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) { // Verifica se il nuovo valore selezionato non è nullo
                // Qui puoi fare qualcosa con il valore selezionato, ad esempio stamparlo
                String[] utili = newValue.toString().split("\n");
                String path = "/com/example/elaborato_ing/images/";
                for (String riga : utili) {
                    if (riga.startsWith("Marca")) {
                        String marca = riga.split(":")[1].trim().toLowerCase();
                        path += marca;
                        System.out.println(marca);
                    }
                    if (riga.startsWith("Modello")) {
                        String modello = riga.split(":")[1].trim().toLowerCase();
                        path += modello;
                        System.out.println(modello);
                    }
                    if (riga.startsWith("Colore")) {
                        String colore = riga.split(":")[1].trim().toLowerCase();
                        path += colore;
                        System.out.println(colore);
                    }
                    if (riga.startsWith("Stato Preventivo")) {
                        statoPreventivo = riga.split(":")[1].trim().toLowerCase();
                    }
                    if (riga.startsWith("Id Preventivo")) {
                        idPreventivo = riga.split(":")[1].trim();
                    }
                }
                path += "1.png";
                Image image = new Image(getClass().getResourceAsStream(path));
                macchinaPreventivo.setImage(image);
            }
        });
    }

    public void paga(ActionEvent actionEvent) {
        try {
            model.aggiungiPagamento(idPreventivo, statoPreventivo);
            listaPreventivi.getItems().clear();
            listaPreventivi.getItems().addAll(model.inizializzaPreventivo());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}