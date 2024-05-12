package com.example.elaborato_ing;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class SegreteriaController {

    @FXML private ComboBox<Marca> marca;
    @FXML private ComboBox<String> modello;
    @FXML private CheckBox infot, sensori, fari, sedili, scorta, vetri, interni, ruote, cruise;
    @FXML private Button modificaOption,visualizzaPreventivi,visualizzaMarca,visualizzaSede;
    @FXML private ListView preventiviListView;
    Model model = new Model();
    private List<Preventivo> preventivi;

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

    }

}