package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;
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
    @FXML
    private Button vendibtn;
    @FXML
    private Label errore;
    private final Model model = new Model();
    private final List<Optionals> listaOp = new ArrayList<>();
    private Auto auto;

    public void initialize() throws IOException {
        marca.getItems().setAll(Marca.values());
        alimentazione.getItems().setAll(Alimentazione.values());
        model.isDouble(altezza);
        model.isDouble(lunghezza);
        model.isDouble(larghezza);
        model.isDouble(peso);
        model.isDouble(volume);
        model.numeric(cilindrata);
        model.numeric(prezzo);
        model.numeric(potenza);
        model.isDouble(consumi);
        model.caricaOpzionalDaFile("src/main/resources/com/example/elaborato_ing/TXT/Optionals.txt", listaOp, checkBoxContainer);

        vendibtn.setDisable(true);

        // Add listeners to all fields to enable the button when all are filled
        addListeners();
    }

    @FXML
    public void caricaImgs() {
        model.caricaImmaginiImageView(imageView1, imageView2, imageView3);
        checkFields();
    }

    @FXML
    public void rimuoviImgs() {
        model.rimuoviImgs(imageView1, imageView2, imageView3);
        checkFields();
    }

    @FXML
    public void aggiungiAuto() {
        errore.setVisible(false);
        try {
            if (imageView1.getImage() != null && imageView2.getImage() != null && imageView3.getImage() != null) {
                if (marca.getValue() != null && !modello.getText().isEmpty() && !altezza.getText().isEmpty() &&
                        !lunghezza.getText().isEmpty() && !larghezza.getText().isEmpty() && !peso.getText().isEmpty() &&
                        !volume.getText().isEmpty() && !colore.getText().isEmpty() && !motore.getText().isEmpty() &&
                        !cilindrata.getText().isEmpty() && !potenza.getText().isEmpty() && !consumi.getText().isEmpty()) {

                    List<String> colori = new ArrayList<>();
                    colori.add(colore.getText().toUpperCase());

                    Marca selectedMarca = Enum.valueOf(Marca.class, String.valueOf(marca.getValue()));
                    Alimentazione selectedAlimentazione = Enum.valueOf(Alimentazione.class, String.valueOf(alimentazione.getValue()));

                    model.salvaImageViewImage(imageView1, 1, selectedMarca, modello.getText(), colore.getText(), 0);
                    model.salvaImageViewImage(imageView2, 2, selectedMarca, modello.getText(), colore.getText(), 0);
                    model.salvaImageViewImage(imageView3, 3, selectedMarca, modello.getText(), colore.getText(), 0);

                    auto = new AutoNuova(
                            selectedMarca,
                            modello.getText(),
                            Double.parseDouble(altezza.getText()),
                            Double.parseDouble(lunghezza.getText()),
                            Double.parseDouble(larghezza.getText()),
                            Double.parseDouble(peso.getText()),
                            Double.parseDouble(volume.getText()),
                            new Motore(motore.getText(), selectedAlimentazione, Integer.parseInt(cilindrata.getText()), Integer.parseInt(potenza.getText()), Double.parseDouble(consumi.getText())),
                            Integer.parseInt(prezzo.getText()),
                            colori,
                            sconto.getText(),
                            listaOp
                    );

                    if (model.getCatalogo().getListaAuto().contains(auto)) {
                        errore.setText("Auto già presente");
                        errore.setVisible(true);
                    } else {
                        model.getCatalogo().add((AutoNuova) auto);
                        model.aggiornaFileCatalogo();
                        errore.setVisible(false);
                    }
                } else {
                    errore.setText("Inserisci tutti i campi");
                    errore.setVisible(true);
                }
            } else {
                errore.setText("Inserisci 3 immagini");
                errore.setVisible(true);
            }
        } catch (NumberFormatException e) {
            errore.setText("Errore nei campi numerici");
            errore.setVisible(true);
        } catch (IllegalArgumentException e) {
            errore.setText("Errore nei campi di selezione");
            errore.setVisible(true);
        } catch (IOException e) {
            errore.setText("Errore durante il salvataggio");
            errore.setVisible(true);
        }
    }

    private void addListeners() {
        // Add listeners to ImageViews
        imageView1.imageProperty().addListener((obs, oldImage, newImage) -> checkFields());
        imageView2.imageProperty().addListener((obs, oldImage, newImage) -> checkFields());
        imageView3.imageProperty().addListener((obs, oldImage, newImage) -> checkFields());

        // Add listeners to ComboBoxes
        marca.valueProperty().addListener((obs, oldVal, newVal) -> checkFields());
        alimentazione.valueProperty().addListener((obs, oldVal, newVal) -> checkFields());

        // Add listeners to TextFields
        addTextFieldListener(modello);
        addTextFieldListener(altezza);
        addTextFieldListener(lunghezza);
        addTextFieldListener(larghezza);
        addTextFieldListener(peso);
        addTextFieldListener(volume);
        addTextFieldListener(motore);
        addTextFieldListener(colore);
        addTextFieldListener(cilindrata);
        addTextFieldListener(potenza);
        addTextFieldListener(consumi);
        addTextFieldListener(prezzo);
        addTextFieldListener(sconto);
    }

    private void addTextFieldListener(TextField textField) {
        textField.textProperty().addListener((obs, oldText, newText) -> checkFields());
    }

    private void checkFields() {
        boolean allFieldsFilled =
                marca.getValue() != null &&
                        alimentazione.getValue() != null &&
                        !modello.getText().isEmpty() &&
                        !altezza.getText().isEmpty() &&
                        !lunghezza.getText().isEmpty() &&
                        !larghezza.getText().isEmpty() &&
                        !peso.getText().isEmpty() &&
                        !volume.getText().isEmpty() &&
                        !motore.getText().isEmpty() &&
                        !colore.getText().isEmpty() &&
                        !cilindrata.getText().isEmpty() &&
                        !potenza.getText().isEmpty() &&
                        !consumi.getText().isEmpty() &&
                        !prezzo.getText().isEmpty() &&
                        imageView1.getImage() != null &&
                        imageView2.getImage() != null &&
                        imageView3.getImage() != null;

        vendibtn.setDisable(!allFieldsFilled);
    }
}