package com.example.elaborato_ing;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

import java.io.*;
import java.util.ArrayList;

public class UsatoController {

    @FXML
    private ComboBox<Marca> marca;
    @FXML
    private ComboBox<Alimentazione> alimentazione;
    @FXML
    private ComboBox<Sede> sede;
    @FXML
    private TextField modello, altezza, lunghezza, larghezza, peso, volume, motore, colore, cilindrata, potenza, consumi;
    @FXML
    private ImageView imageView1, imageView2, imageView3;

    private final Model model = new Model();
    AutoUsata auto;

    public void initialize() {
        marca.getItems().setAll(Marca.values());
        alimentazione.getItems().setAll(Alimentazione.values());
        sede.getItems().setAll(Sede.values());
        model.isDouble(altezza);
        model.isDouble(lunghezza);
        model.isDouble(larghezza);
        model.isDouble(peso);
        model.isDouble(volume);
        model.numeric(cilindrata);
        model.numeric(potenza);
        model.isDouble(consumi);
        model.checkColore(colore);

    }

    public void caricaImgs() {
        model.caricaImmaginiImageView(imageView1, imageView2, imageView3);
    }

    @FXML
    public void rimuoviImgs() {
        model.rimuoviImgs(imageView1, imageView2, imageView3);
    }

    public void vendi(ActionEvent event) throws IOException {
        if (imageView1.getImage() != null && imageView2.getImage() != null && imageView3.getImage() != null && marca.getValue() != null && !modello.getText().isEmpty() && !altezza.getText().isEmpty() && !lunghezza.getText().isEmpty() && !larghezza.getText().isEmpty() && !peso.getText().isEmpty() && !volume.getText().isEmpty() && !colore.getText().isEmpty() && !motore.getText().isEmpty() && alimentazione.getValue() != null && !cilindrata.getText().isEmpty() && !potenza.getText().isEmpty() && !consumi.getText().isEmpty() && sede.getValue() != null) {
            auto = new AutoUsata(Enum.valueOf(Marca.class, String.valueOf(marca.getValue())), modello.getText(), Double.parseDouble(altezza.getText()), Double.parseDouble(lunghezza.getText()), Double.parseDouble(larghezza.getText()), Double.parseDouble(peso.getText()), Double.parseDouble(volume.getText()), new Motore(motore.getText(), Enum.valueOf(Alimentazione.class, String.valueOf(alimentazione.getValue())), Integer.parseInt(cilindrata.getText()), Integer.parseInt(potenza.getText()), Double.parseDouble(consumi.getText())), colore.getText().toLowerCase(), Enum.valueOf(Sede.class, String.valueOf(sede.getValue())));
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confermi l'inoltro del preventivo auto usata?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    try {
                        model.inoltraPreventivo(auto, colore.getText().toLowerCase(), 0, Enum.valueOf(Sede.class, String.valueOf(sede.getValue())));
                        model.salvaImageViewImage(model.getFileScelto1(), imageView1, 1, Enum.valueOf(Marca.class, String.valueOf(marca.getValue())), modello.getText(), colore.getText(), 1);
                        model.salvaImageViewImage(model.getFileScelto2(), imageView2, 2, Enum.valueOf(Marca.class, String.valueOf(marca.getValue())), modello.getText(), colore.getText(), 1);
                        model.salvaImageViewImage(model.getFileScelto3(), imageView3, 3, Enum.valueOf(Marca.class, String.valueOf(marca.getValue())), modello.getText(), colore.getText(), 1);
                        model.getMapAutoUsata().computeIfAbsent(marca.getValue(), k -> new ArrayList<>()).add(auto);
                        model.openCloseFXML("FXML/Usato.fxml", event);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attenzione");
            alert.setHeaderText("Assicurati di aver completato tutti i campi, comprese le immagini (3 in totale)");
            alert.showAndWait();
        }
    }
}