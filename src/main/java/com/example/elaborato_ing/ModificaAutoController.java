package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.util.Collections;
import java.util.List;

public class ModificaAutoController {
    @FXML
    private TextField prezzo;

    @FXML
    private CheckBox gennaio, febbraio, marzo, aprile, maggio, giugno, luglio, agosto, settembre, ottobre, novembre, dicembre;

    @FXML
    private ComboBox<Marca> marca;
    @FXML
    private ComboBox<String> modello, colore;

    @FXML
    private ImageView imageView1, imageView2, imageView3;

    @FXML
    private TextField coloreNuovo;

    Model model = new Model();

    public void initialize() {
        model.setMarca(marca);
        marca.setOnAction(_ -> aggiornaModello());
        modello.setOnAction(_ -> inizializzaCheckboxeColore());
    }

    private void inizializzaCheckboxeColore() {
        AutoNuova auto = model.getMarcaModelloAutoNuova(Marca.valueOf(String.valueOf(marca.getValue())), String.valueOf(modello.getValue()), model.getMapAutoNuova());
        if (auto.getSconto().contains("A")) gennaio.setSelected(true);
        if (auto.getSconto().contains("B")) febbraio.setSelected(true);
        if (auto.getSconto().contains("C")) marzo.setSelected(true);
        if (auto.getSconto().contains("D")) aprile.setSelected(true);
        if (auto.getSconto().contains("E")) maggio.setSelected(true);
        if (auto.getSconto().contains("F")) giugno.setSelected(true);
        if (auto.getSconto().contains("G")) luglio.setSelected(true);
        if (auto.getSconto().contains("H")) agosto.setSelected(true);
        if (auto.getSconto().contains("I")) settembre.setSelected(true);
        if (auto.getSconto().contains("J")) ottobre.setSelected(true);
        if (auto.getSconto().contains("K")) novembre.setSelected(true);
        if (auto.getSconto().contains("L")) dicembre.setSelected(true);
        colore.getItems().clear();
        if(auto.getColori().size() != 1) {
            colore.getItems().addAll(auto.getColori());
            colore.setValue(colore.getItems().getFirst());
        }else
            colore.setDisable(true);
    }

    private void aggiornaModello() {
        List<AutoNuova> listaAuto = model.getMapAutoNuova().getOrDefault(marca.getValue(), Collections.emptyList());
        List<String> listaModelli = listaAuto.stream().map(Auto::getModello).distinct().toList();

        modello.getItems().clear();
        modello.getItems().setAll(listaModelli);
        modello.setDisable(false);
    }

    public void modificaAuto() {
        AutoNuova auto = model.getMarcaModelloAutoNuova(Marca.valueOf(String.valueOf(marca.getValue())), String.valueOf(modello.getValue()), model.getMapAutoNuova());
        if (prezzo.getText() != null) {
            auto.setPrezzo(Integer.parseInt(prezzo.getText()));
        }
        String scontoAgg = "";
        if (gennaio.isSelected()) scontoAgg += "A";
        if (febbraio.isSelected()) scontoAgg += "B";
        if (marzo.isSelected()) scontoAgg += "C";
        if (aprile.isSelected()) scontoAgg += "D";
        if (maggio.isSelected()) scontoAgg += "E";
        if (giugno.isSelected()) scontoAgg += "F";
        if (luglio.isSelected()) scontoAgg += "G";
        if (agosto.isSelected()) scontoAgg += "H";
        if (settembre.isSelected()) scontoAgg += "I";
        if (ottobre.isSelected()) scontoAgg += "J";
        if (novembre.isSelected()) scontoAgg += "K";
        if (dicembre.isSelected()) scontoAgg += "L";
        if (!auto.getSconto().equals(scontoAgg)) {
            auto.setSconto(scontoAgg);
        }
        if (colore.getValue() != null) {
            auto.getColori().remove(colore.getValue());
        }
        if (coloreNuovo.getText() != null && imageView1.getImage() != null && imageView2.getImage() != null && imageView3.getImage() != null) {
            auto.getColori().add(coloreNuovo.getText());
        } else if (imageView1.getImage() != null || imageView2.getImage() != null || imageView3.getImage() != null) {
            System.out.println("inserisci 3 immagini");
        } else {
            System.out.println("inserisci nome colore");
        }
    }

    public void caricaImgs() {
        model.caricaImmaginiImageView(imageView1, imageView2, imageView3);

    }

    public void rimuoviImgs() {
        model.rimuoviImgs(imageView1, imageView2, imageView3);
    }
}
