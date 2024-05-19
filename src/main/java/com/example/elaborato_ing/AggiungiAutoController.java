package com.example.elaborato_ing;

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
        IsDouble(altezza);
        IsDouble(lunghezza);
        IsDouble(larghezza);
        IsDouble(peso);
        IsDouble(volume);
        Numeric(cilindrata);
        Numeric(prezzo);
        Numeric(potenza);
        IsDouble(consumi);
        alimentazione.getItems().setAll(Alimentazione.values());
        model.caricaOpzionalDaFile("src/main/resources/com/example/elaborato_ing/TXT/Optionals.txt", listaOp, checkBoxContainer);

/*
        imageView2.setOnMouseClicked(event -> caricaImgs());
        stackPane.getChildren().add(imageView2);

 */
    }

    private void IsDouble(TextField txt) {
        txt.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String character = event.getCharacter();

            // Consenti solo numeri, punto decimale, e segno meno
            if (!character.matches("[\\d.-]")) {
                event.consume(); // Blocca l'evento se non è un numero, punto o segno meno
                return;
            }

            // Assicurati che ci sia solo un punto decimale
            if (character.equals(".") && txt.getText().contains(".")) {
                event.consume(); // Blocca l'evento se c'è già un punto decimale
                return;
            }

            // Assicurati che il segno meno sia solo all'inizio
            if (character.equals("-")) {
                if (txt.getText().contains("-")) {
                    event.consume(); // Blocca se c'è già un segno meno
                } else if (txt.getCaretPosition() > 0) {
                    event.consume(); // Blocca se il segno meno non è all'inizio
                }
            }
        });
    }

    private void Numeric(TextField txt) {
        txt.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String character = event.getCharacter();
            // Consenti solo numeri (0-9) e impedisci input di altri caratteri
            if (!character.matches("\\d")) {
                event.consume(); // Blocca l'evento se non è un numero
            }
        });
    }

    @FXML
    public void caricaImgs() {
        model.caricaImmaginiImageView(imageView1, imageView2, imageView3);
    }

    public void RimuoviImgs() {
        // Trova il primo ImageView con un'immagine e rimuovila
        if (imageView3.getImage() != null) {
            imageView3.setImage(null);
        } else if (imageView2.getImage() != null) {
            imageView2.setImage(null);
        } else if (imageView1.getImage() != null) {
            imageView1.setImage(null);
        } else {
            System.out.println("Tutte le ImageView sono già vuote.");
        }
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







