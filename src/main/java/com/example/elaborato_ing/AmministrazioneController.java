package com.example.elaborato_ing;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Collections;
import java.util.List;

public class AmministrazioneController {

    @FXML private ComboBox<Marca> marca;
    @FXML private ComboBox<String> modello;
    @FXML private CheckBox infot, sensori, fari, sedili, scorta, vetri, interni, ruote, cruise;
    @FXML private Button modificaOption,visualizzaPreventivi,visualizzaMarca,visualizzaSede,aggiungi;
    @FXML private ListView preventiviListView;
    Model model = new Model();
    private List<Preventivo> preventivi;

    public void initialize() {
        model.setMarca(marca);
        marca.setOnAction(_ -> aggiornaModello());
    }

    private void aggiornaModello() {
        modello.getItems().clear();
        List<AutoNuova> listaAuto = model.getMap().getOrDefault(marca.getValue(), Collections.emptyList());
        List<String> listaModelli = listaAuto.stream().map(Auto::getModello).distinct().toList();

        if (listaModelli.isEmpty()) {
            modello.getItems().clear();
            modello.setDisable(true);
        } else {
            modello.getItems().setAll(listaModelli);
            modello.setDisable(false);
        }
    }

    public void xcliente(ActionEvent actionEvent) {
        Collections.sort(preventivi, (p1, p2) -> p1.getCliente().getNome().compareTo(p2.getCliente().getNome()));

        // Aggiorna la ListView con la lista di preventivi ordinata
        preventiviListView.setItems(FXCollections.observableArrayList(preventivi));
    }

    public void xmarca(ActionEvent actionEvent) {
        Collections.sort(preventivi, (p1, p2) -> p1.getAuto().getMarca().compareTo(p2.getAuto().getMarca()));

        // Aggiorna la ListView con la lista di preventivi ordinata
        preventiviListView.setItems(FXCollections.observableArrayList(preventivi));
    }

    public void xsede(ActionEvent actionEvent) {
        Collections.sort(preventivi, (p1, p2) -> p1.getSede().compareTo(p2.getSede()));

        // Aggiorna la ListView con la lista di preventivi ordinata
        preventiviListView.setItems(FXCollections.observableArrayList(preventivi));
    }
    public void  modificaOptionals(ActionEvent actionEvent) {
        Marca marcaSelezionata = marca.getValue();
    }

    public void aggiungiAuto(ActionEvent actionEvent) {
        model.OpenCloseFXML("FXML/AggiungiAuto.fxml",aggiungi);
    }
}