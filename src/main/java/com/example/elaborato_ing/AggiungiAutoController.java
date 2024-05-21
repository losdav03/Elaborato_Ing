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
    private TextField modello, altezza, lunghezza, larghezza, peso, volume, motore, colore, cilindrata, potenza, consumi, prezzo;
    @FXML
    private CheckBox gennaio, febbraio, marzo, aprile, maggio, giugno, luglio, agosto, settembre, ottobre, novembre, dicembre;
    @FXML
    private ImageView imageView1, imageView2, imageView3;
    @FXML
    private VBox checkBoxContainer;
    @FXML
    private Button vendibtn;
    private final Model model = new Model();
    private final List<Optionals> listaOp = new ArrayList<>();
    private AutoNuova auto;

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
        model.checkColore(colore);
        model.caricaOpzionalDaFile("src/main/resources/com/example/elaborato_ing/TXT/Optionals.txt", listaOp, checkBoxContainer);
        vendibtn.setDisable(false);

    }

    @FXML
    public void caricaImgs() {
        model.caricaImmaginiImageView(imageView1, imageView2, imageView3);
    }

    @FXML
    public void rimuoviImgs() {
        model.rimuoviImgs(imageView1, imageView2, imageView3);
    }

    @FXML
    public void aggiungiAuto() throws IOException {
        if (imageView1.getImage() != null && imageView2.getImage() != null && imageView3.getImage() != null && marca.getValue() != null && !modello.getText().isEmpty() && !altezza.getText().isEmpty() &&
                !lunghezza.getText().isEmpty() && !larghezza.getText().isEmpty() && !peso.getText().isEmpty() &&
                !volume.getText().isEmpty() && !colore.getText().isEmpty() && alimentazione.getValue() != null && !motore.getText().isEmpty() &&
                !cilindrata.getText().isEmpty() && !potenza.getText().isEmpty() && !consumi.getText().isEmpty()) {
            if (model.getMarcaModelloAutoNuova(marca.getValue(), modello.getText(), model.getMapAutoNuova()) == null) {
                List<String> colori = new ArrayList<>();
                colori.add(colore.getText().toUpperCase());

                Marca selectedMarca = Enum.valueOf(Marca.class, String.valueOf(marca.getValue()));
                Alimentazione selectedAlimentazione = Enum.valueOf(Alimentazione.class, String.valueOf(alimentazione.getValue()));

                model.salvaImageViewImage(imageView1, 1, selectedMarca, modello.getText(), colore.getText(), 0);
                model.salvaImageViewImage(imageView2, 2, selectedMarca, modello.getText(), colore.getText(), 0);
                model.salvaImageViewImage(imageView3, 3, selectedMarca, modello.getText(), colore.getText(), 0);

                String scontoCheck = mesiChecked();
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
                        scontoCheck,
                        listaOp
                );

                model.getCatalogo().add(auto);
                model.aggiornaFileCatalogo();
                model.caricaDaFile("src/main/resources/com/example/elaborato_ing/TXT/Catalogo.txt",model.getCatalogo());

                marca.getItems().clear();
                marca.getItems().setAll(Marca.values());
                modello.setText("");
                lunghezza.setText("");
                altezza.setText("");
                larghezza.setText("");
                peso.setText("");
                volume.setText("");
                alimentazione.getItems().clear();
                alimentazione.getItems().setAll(Alimentazione.values());
                motore.setText("");
                cilindrata.setText("");
                potenza.setText("");
                consumi.setText("");
                prezzo.setText("");
                colore.setText("");
                mesiUnChecked();
                imageView1.setImage(null);
                imageView2.setImage(null);
                imageView3.setImage(null);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Attenzione");
                alert.setHeaderText("Auto già presente");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attenzione");
            alert.setHeaderText("Assicurati di aver completato tutti i campi, comprese le immagini (3 in totale)");
            alert.showAndWait();
        }
    }

    private String mesiChecked() {
        String res = "";
        if (gennaio.isSelected()) res += "A";
        if (febbraio.isSelected()) res += "B";
        if (marzo.isSelected()) res += "C";
        if (aprile.isSelected()) res += "D";
        if (maggio.isSelected()) res += "E";
        if (giugno.isSelected()) res += "F";
        if (luglio.isSelected()) res += "G";
        if (agosto.isSelected()) res += "H";
        if (settembre.isSelected()) res += "I";
        if (ottobre.isSelected()) res += "J";
        if (novembre.isSelected()) res += "K";
        if (dicembre.isSelected()) res += "L";

        return res;
    }

    private void mesiUnChecked() {
        gennaio.setSelected(false);
        febbraio.setSelected(false);
        marzo.setSelected(false);
        aprile.setSelected(false);
        maggio.setSelected(false);
        giugno.setSelected(false);
        luglio.setSelected(false);
        agosto.setSelected(false);
        settembre.setSelected(false);
        ottobre.setSelected(false);
        novembre.setSelected(false);
        dicembre.setSelected(false);
    }
}