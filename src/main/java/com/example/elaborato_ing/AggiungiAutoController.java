package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class AggiungiAutoController {

    @FXML
    private ComboBox marca, alimentazione, sede;

    @FXML
    private TextField modello, altezza, lunghezza, larghezza, peso, volume, motore, colore, cilindrata, potenza, consumi, prezzo, sconto;

    @FXML
    private ImageView imageView1, imageView2, imageView3;

    @FXML
    private VBox checkBoxContainer;
    private final Model model = new Model();
    private File image1, image2, image3;

    Auto auto;
    private List<Optionals> listaOp = new ArrayList<>();

    public void initialize() throws IOException {
        marca.getItems().setAll(Marca.values());
        //marcaCB.setOnAction(_ -> aggiornaModello());
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
        sede.getItems().setAll(Sede.values());
        model.caricaOpzionalDaFile("src/main/resources/com/example/elaborato_ing/TXT/Optionals.txt", listaOp, checkBoxContainer);
    }

    /*private void aggiornaModello() {
        modelloCB.getItems().clear();
        List<AutoNuova> listaAuto = model.getMap().getOrDefault(marcaCB.getValue(), Collections.emptyList());
        List<String> listaModelli = listaAuto.stream().map(Auto::getModello).distinct().toList();

        if (listaModelli.isEmpty()) {
            modelloCB.getItems().clear();
            modelloCB.setDisable(true);
        } else {
            modelloCB.getItems().setAll(listaModelli);
            modelloCB.setDisable(false);
        }
    }*/

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


    public void caricaImgs(ImageView imageView1, ImageView imageView2, ImageView imageView3) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleziona un'immagine");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Immagini", "*.png"));

        File Immagine = fileChooser.showOpenDialog(imageView1.getScene().getWindow());
        if (Immagine != null) {
            Image image = new Image(Immagine.toURI().toString());
            if (imageView1.getImage() == null) {
                image1 = Immagine;
                imageView1.setImage(image);
            } else if (imageView2.getImage() == null) {
                image2 = Immagine;
                imageView2.setImage(image);
            } else {
                image3 = Immagine;
                imageView3.setImage(image);
            }

        }
    }

    private void caricaImgCartella(File immagine, int vista) throws IOException {
        String newFileName = marca.getValue().toString().toLowerCase().trim() + modello.getText().trim().toLowerCase() + colore.getText().trim().toLowerCase() + vista + ".png";

        if (immagine != null) {
            // Percorso relativo della cartella delle immagini
            File outputDir = new File("src/main/resources/com/example/elaborato_ing/images");
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }
            File outputFile = new File(outputDir, newFileName);
            Files.copy(immagine.toPath(), outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
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

    public void aggiungi(ActionEvent actionEvent) throws IOException {
        if (/*imageView1.getImage() != null && imageView2.getImage() != null && imageView3.getImage() != null && */!String.valueOf(marca.getValue()).isEmpty() && !modello.getText().isEmpty() && !altezza.getText().isEmpty() && !lunghezza.getText().isEmpty() && !larghezza.getText().isEmpty() && !peso.getText().isEmpty() && !volume.getText().isEmpty() && !colore.getText().isEmpty() && !motore.getText().isEmpty() && !cilindrata.getText().isEmpty() && !potenza.getText().isEmpty() && !consumi.getText().isEmpty() && !String.valueOf(sede.getValue()).isEmpty()) {
            List<String> colori = new ArrayList<>();
            colori.add(colore.getText().toUpperCase());
            caricaImgCartella(image1, 1);
            caricaImgCartella(image2, 2);
            caricaImgCartella(image3, 3);
            auto = new AutoNuova(Enum.valueOf(Marca.class, String.valueOf(marca.getValue())), modello.getText(), Double.parseDouble(altezza.getText()), Double.parseDouble(lunghezza.getText()), Double.parseDouble(larghezza.getText()), Double.parseDouble(peso.getText()), Double.parseDouble(volume.getText()), new Motore(motore.getText(), Enum.valueOf(Alimentazione.class, String.valueOf(alimentazione.getValue())), Integer.parseInt(cilindrata.getText()), Integer.parseInt(potenza.getText()), Double.parseDouble(consumi.getText())), Integer.parseInt(prezzo.getText()), colori, sconto.getText(), listaOp);
            model.getCatalogo().add(auto);
            model.aggiornaFileCatalogo();
        }
    }
}







