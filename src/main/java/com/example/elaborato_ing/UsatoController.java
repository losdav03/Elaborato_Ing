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

public class UsatoController {

    @FXML
    private ComboBox marca;

    @FXML
    private TextField modello, altezza, lunghezza, larghezza, peso, volume, motore, colori, alimentazione, cilindrata, potenza, consumi;

    @FXML
    private ImageView imageView1, imageView2, imageView3;

    @FXML
    private CheckBox infot, sensori, fari, sedili, scorta, vetri, interni, ruote, cruise;
    private final Model model = new Model();
    AutoUsata auto;

    public void initialize() {
        marca.getItems().setAll(Marca.values());
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
        if (imageView1.getImage() != null && imageView2.getImage() != null && imageView3.getImage() != null && !marca.getText().isEmpty() && !modello.getText().isEmpty() &&!altezza.getText().isEmpty() &&!lunghezza.getText().isEmpty() &&!larghezza.getText().isEmpty() &&!peso.getText().isEmpty() &&!volume.getText().isEmpty() &&!colori.getText().isEmpty() &&!motore.getText().isEmpty() &&!alimentazione.getText().isEmpty() &&!cilindrata.getText().isEmpty()&&!potenza.getText().isEmpty() &&!consumi.getText().isEmpty()) {
            auto = new AutoUsata(Enum.valueOf(Marca.class, marca.getText()), modello.getText(),Double.parseDouble(altezza.getText()),Double.parseDouble(lunghezza.getText()), Double.parseDouble(larghezza.getText()), Double.parseDouble(peso.getText()), Double.parseDouble(volume.getText()),new Motore(motore.getText(), Enum.valueOf(Alimentazione.class, alimentazione.getText()), Integer.parseInt(cilindrata.getText()), Integer.parseInt(potenza.getText()), Double.parseDouble(consumi.getText())), colori.getText());
            auto.addImgs(imageView1,imageView2,imageView3);
            auto.aggiungiOptional(infot.isSelected(), sensori.isSelected(), fari.isSelected(), sedili.isSelected(), scorta.isSelected(), vetri.isSelected(), interni.isSelected(), ruote.isSelected(), cruise.isSelected());
            model.inoltraPreventivo(auto,colori.getText(), 0, null);
        }
    }
}
    /*
    public void caricaImmagini(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Immagini", "*.png"));
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);

        if (selectedFiles != null) {
            for (File file : selectedFiles) {
                try {
                    InputStream imageStream = new FileInputStream(file);
                    if (imageStream != null) {
                        Image image = new Image(imageStream);
                        imageList.add(image);
                    }
                } catch (Exception e) {
                    System.err.println("Errore durante il caricamento dell'immagine: " + file.getName() + ", motivo: " + e.getMessage());
                }
            }
        } else {
            System.out.println("Nessun file selezionato.");
        }
    }

    @FXML
    public void mostraPrimaImmagine(ActionEvent actionEvent) {
        if (!imageList.isEmpty()) {
            imageView.setImage(imageList.get(0));
        }
    }

    @FXML
    public void mostraImmagineSuccessiva(ActionEvent actionEvent) {
        if (!imageList.isEmpty()) {
            int currentIndex = imageList.indexOf(imageView.getImage());
            int nextIndex = (currentIndex + 1) % imageList.size();
            imageView.setImage(imageList.get(nextIndex));
        }
    }

    @FXML
    public void mostraImmaginePrecedente(ActionEvent actionEvent) {
        if (!imageList.isEmpty()) {
            int currentIndex = imageList.indexOf(imageView.getImage());
            int prevIndex = (currentIndex - 1 + imageList.size()) % imageList.size();
            imageView.setImage(imageList.get(prevIndex));
        }
    }

     */

