package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AggiungiAutoController {

    @FXML
    private ComboBox<Marca> marca;
    @FXML
    private ComboBox<Alimentazione> alimentazione;
    @FXML
    private TextField modello, altezza, lunghezza, larghezza, peso, volume, motore, colore, cilindrata, potenza, consumi, prezzo, sconto;
    @FXML
    private ImageView imageView1, imageView2, imageView3;
    @FXML
    private VBox checkBoxContainer;

    private final Model model = new Model();

    Auto auto;

    private final List<Optionals> listaOp = new ArrayList<>();


    public void initialize() throws IOException {
        marca.getItems().setAll(Marca.values());
        model.isDouble(altezza);
        model.isDouble(lunghezza);
        model.isDouble(larghezza);
        model.isDouble(peso);
        model.isDouble(volume);
        model.numeric(cilindrata);
        model.numeric(prezzo);
        model.numeric(potenza);
        model.isDouble(consumi);
        alimentazione.getItems().setAll(Alimentazione.values());
        model.caricaOpzionalDaFile("src/main/resources/com/example/elaborato_ing/TXT/Optionals.txt", listaOp, checkBoxContainer);

/*
        imageView2.setOnMouseClicked(event -> caricaImgs());
        stackPane.getChildren().add(imageView2);

 */
    }

    @FXML
    public void caricaImgs() {
        model.caricaImmaginiImageView(imageView1, imageView2, imageView3);
    }

    @FXML
    public void rimuoviImgs() {
        model.rimuoviImgs(imageView1, imageView2, imageView3);
    }

    public void aggiungiAuto() throws IOException {
        if (!String.valueOf(marca.getValue()).isEmpty() && !modello.getText().isEmpty() && !altezza.getText().isEmpty() && !lunghezza.getText().isEmpty() && !larghezza.getText().isEmpty() && !peso.getText().isEmpty() && !volume.getText().isEmpty() && !colore.getText().isEmpty() && !motore.getText().isEmpty() && !cilindrata.getText().isEmpty() && !potenza.getText().isEmpty() && !consumi.getText().isEmpty()) {
            List<String> colori = new ArrayList<>();
            colori.add(colore.getText().toUpperCase());
            model.salvaImageViewImage(imageView1, 1, Enum.valueOf(Marca.class, String.valueOf(marca.getValue())), modello.getText(), colore.getText());
            model.salvaImageViewImage(imageView2, 2, Enum.valueOf(Marca.class, String.valueOf(marca.getValue())), modello.getText(), colore.getText());
            model.salvaImageViewImage(imageView3, 3, Enum.valueOf(Marca.class, String.valueOf(marca.getValue())), modello.getText(), colore.getText());
            auto = new AutoNuova(Enum.valueOf(Marca.class, String.valueOf(marca.getValue())), modello.getText(), Double.parseDouble(altezza.getText()), Double.parseDouble(lunghezza.getText()), Double.parseDouble(larghezza.getText()), Double.parseDouble(peso.getText()), Double.parseDouble(volume.getText()), new Motore(motore.getText(), Enum.valueOf(Alimentazione.class, String.valueOf(alimentazione.getValue())), Integer.parseInt(cilindrata.getText()), Integer.parseInt(potenza.getText()), Double.parseDouble(consumi.getText())), Integer.parseInt(prezzo.getText()), colori, sconto.getText(), listaOp);
            model.getCatalogo().add((AutoNuova) auto);
            model.aggiornaFileCatalogo();
        }
    }


}







