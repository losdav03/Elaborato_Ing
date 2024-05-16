package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class UsatoController {

    @FXML
    private ComboBox marca,alimentazione,sede;

    @FXML
    private TextField modello, altezza, lunghezza, larghezza, peso, volume, motore, colori, cilindrata, potenza, consumi;

    @FXML
    private ImageView imageView1, imageView2, imageView3;

    private final Model model = new Model();
    AutoUsata auto;

    public void initialize() {
        marca.getItems().setAll(Marca.values());
        alimentazione.getItems().setAll(Alimentazione.values());
        sede.getItems().setAll(Sede.values());
        Doouble(altezza);
        Doouble(lunghezza);
        Doouble(larghezza);
        Doouble(peso);
        Doouble(volume);
        Numeric(cilindrata);
        Numeric(potenza);
        Doouble(consumi);
    }

    private void Doouble(TextField txt) {
        txt.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String character = event.getCharacter();

            // Consenti solo numeri, punto decimale, e segno meno
            if (!character.matches("[\\d\\.-]")) {
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


    public void caricaImgs() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleziona un'immagine");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Immagini", "*.png"));

        File Immagine = fileChooser.showOpenDialog(imageView1.getScene().getWindow());
        if (Immagine != null) {
            Image image = new Image(Immagine.toURI().toString());
            if (imageView1.getImage() == null) {
                imageView1.setImage(image);
            } else if (imageView2.getImage() == null) {
                imageView2.setImage(image);
            } else {
                imageView3.setImage(image);
            }
        }

        String destinationFolder = "src/main/resources/com/example/elaborato_ing/images/";
        try {
            Files.copy(Immagine.toPath(), new File(destinationFolder + Immagine.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Immagine salvata con successo in: " + destinationFolder + Immagine.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void vendi(ActionEvent actionEvent) throws IOException {
        if (imageView1.getImage() != null && imageView2.getImage() != null && imageView3.getImage() != null && !String.valueOf(marca.getValue()).isEmpty() && !modello.getText().isEmpty() && !altezza.getText().isEmpty() && !lunghezza.getText().isEmpty() && !larghezza.getText().isEmpty() && !peso.getText().isEmpty() && !volume.getText().isEmpty() && !colori.getText().isEmpty() && !motore.getText().isEmpty() && !String.valueOf(alimentazione.getValue()).isEmpty() && !cilindrata.getText().isEmpty() && !potenza.getText().isEmpty() && !consumi.getText().isEmpty() && !String.valueOf(sede.getValue()).isEmpty()) {
            String path = "src/main/resources/com/example/elaborato_ing/images/" + marca.getValue().toString().toLowerCase() + modello.getText().toLowerCase() + colori.getText();

            auto = new AutoUsata(Enum.valueOf(Marca.class, String.valueOf(marca.getValue())), modello.getText(), Double.parseDouble(altezza.getText()), Double.parseDouble(lunghezza.getText()), Double.parseDouble(larghezza.getText()), Double.parseDouble(peso.getText()), Double.parseDouble(volume.getText()), new Motore(motore.getText(), Enum.valueOf(Alimentazione.class, String.valueOf(alimentazione.getValue())), Integer.parseInt(cilindrata.getText()), Integer.parseInt(potenza.getText()), Double.parseDouble(consumi.getText())), colori.getText().toLowerCase(), Enum.valueOf(Sede.class, String.valueOf(sede.getValue())));
            auto.addImgs(path + "1.png", path + "2.png", path + "3.png");
            //model.inoltraPreventivoUsato(auto, colori.getText().toLowerCase(), 0, null);
        }
    }
}