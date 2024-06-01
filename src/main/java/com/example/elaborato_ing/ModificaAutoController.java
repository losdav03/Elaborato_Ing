package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModificaAutoController {
    @FXML
    private TextField prezzo, coloreNuovo, potenza, motoreNuovo, cilindrata, consumi;
    @FXML
    private CheckBox gennaio, febbraio, marzo, aprile, maggio, giugno, luglio, agosto, settembre, ottobre, novembre, dicembre;
    @FXML
    private ComboBox<Alimentazione> alimentazione;
    @FXML
    private ComboBox<Marca> marca;
    @FXML
    private ComboBox<String> modello, colore, motore;
    @FXML
    private ImageView imageView1, imageView2, imageView3;
    @FXML
    private Button modifica;

    Model model = new Model();

    public void initialize() {
        model.setMarca(marca);
        modello.setDisable(true);
        marca.setOnAction(_ -> aggiornaModello());
        modello.setOnAction(_ -> inizializzaCheckboxeColore());
        alimentazione.getItems().setAll(Alimentazione.values());
        model.numeric(prezzo);
        model.numeric(cilindrata);
        model.numeric(potenza);
        model.isDouble(consumi);
        model.checkColore(coloreNuovo);
        model.aggiornaFileCatalogo();
        model.caricaDaFile("src/main/resources/com/example/elaborato_ing/TXT/Catalogo.txt", model.getCatalogo());
        model.ciccioGamerFXML("FXML/Segreteria.fxml", marca);
    }

    private void inizializzaCheckboxeColore() {
        AutoNuova auto = model.getMarcaModelloAutoNuova(marca.getValue(), modello.getValue(), model.getMapAutoNuova());
        // Reset dei checkbox
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

        // Imposta i checkbox in base agli sconti
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

        // Aggiorna i colorie i motori disponibili
        colore.getItems().clear();
        motore.getItems().clear();
        if (!auto.getColori().isEmpty()) {
            colore.getItems().addAll(auto.getColori());
            if (colore.getItems().size() == 1) colore.setDisable(true);
            else colore.setDisable(false);
        } else {
            colore.setDisable(true);
        }
        if (!auto.getMotori().isEmpty()) {
            motore.getItems().addAll(auto.getNomiMotori());
            if (motore.getItems().size() == 1) motore.setDisable(true);
            else motore.setDisable(false);
        } else {
            motore.setDisable(true);
        }
    }

    private void aggiornaModello() {
        model.aggiornaFileCatalogo();
        model.caricaDaFile("src/main/resources/com/example/elaborato_ing/TXT/Catalogo.txt", model.getCatalogo());
        List<AutoNuova> listaAuto = model.getMapAutoNuova().getOrDefault(marca.getValue(), Collections.emptyList());
        List<String> listaModelli = listaAuto.stream().map(Auto::getModello).distinct().toList();

        if (listaModelli.isEmpty()) {
            modello.getItems().clear();
            modello.setDisable(true);
            motore.setDisable(true);
            motore.getItems().clear();
        } else {
            modello.getItems().setAll(listaModelli);
            modello.setDisable(false);
        }
    }

    public void modificaAuto(ActionEvent event) throws IOException {

        if (marca.getValue() != null && modello.getValue() != null) {
            AutoNuova auto = model.getMarcaModelloAutoNuova(Marca.valueOf(String.valueOf(marca.getValue())), String.valueOf(modello.getValue()), model.getMapAutoNuova());
            if (auto != null) {

                if (!prezzo.getText().isEmpty()) {
                    auto.setPrezzo(Integer.parseInt(prezzo.getText()));
                    prezzo.setText("");
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
                    List<String> coloriModificabili = new ArrayList<>(auto.getColori());
                    coloriModificabili.remove(String.valueOf(colore.getValue()));
                    auto.setColori(coloriModificabili);
                    colore.getItems().clear();
                    colore.getItems().setAll(coloriModificabili);
                    if (coloriModificabili.size() == 1) colore.setDisable(true);
                }


                if (!coloreNuovo.getText().isEmpty()) {
                    if (imageView1.getImage() != null && imageView2.getImage() != null && imageView3.getImage() != null) {
                        String coloreText = coloreNuovo.getText().toUpperCase();

                        List<String> coloriModificabili = new ArrayList<>(auto.getColori());
                        if (!coloriModificabili.contains(coloreText)) {
                            coloriModificabili.add(coloreText);
                            auto.setColori(coloriModificabili);
                            colore.getItems().clear();
                            colore.getItems().setAll(coloriModificabili);
                            colore.setDisable(false);
                            model.salvaImageViewImage(model.getFileScelto1(), imageView1, 1, Enum.valueOf(Marca.class, String.valueOf(marca.getValue())), modello.getValue(), coloreNuovo.getText(), 0);
                            model.salvaImageViewImage(model.getFileScelto2(), imageView2, 2, Enum.valueOf(Marca.class, String.valueOf(marca.getValue())), modello.getValue(), coloreNuovo.getText(), 0);
                            model.salvaImageViewImage(model.getFileScelto3(), imageView3, 3, Enum.valueOf(Marca.class, String.valueOf(marca.getValue())), modello.getValue(), coloreNuovo.getText(), 0);
                        } else {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Attenzione");
                            alert.setHeaderText("Colore già presente");
                            alert.showAndWait();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Attenzione");
                        alert.setHeaderText("Inserisci 3 immagini");
                        alert.showAndWait();
                    }
                }
                if (motore.getValue() != null) {
                    List<Motore> motoriModificabili = new ArrayList<>(auto.getMotori());
                    motoriModificabili.remove(auto.trovaMotore(motore.getValue()));
                    auto.setMotori(motoriModificabili);
                    motore.getItems().clear();
                    for (Motore m : motoriModificabili) {
                        motore.getItems().add(m.getNome());
                    }
                    if (motoriModificabili.size() == 1) motore.setDisable(true);
                }
                if (!motoreNuovo.getText().isEmpty()) {
                    if (!cilindrata.getText().isEmpty() && !consumi.getText().isEmpty() && !potenza.getText().isEmpty() && alimentazione.getValue() != null) {
                        if (!auto.containsMotore(motoreNuovo.getText())) {
                            Motore mot = new Motore(motoreNuovo.getText(), alimentazione.getValue(), Integer.parseInt(cilindrata.getText()), Integer.parseInt(potenza.getText()), Double.parseDouble(consumi.getText()));
                            motore.getItems().clear();
                            List<Motore> temp = auto.getMotori();
                            temp.add(mot);
                            auto.setMotori(temp);
                            motore.getItems().setAll(auto.getNomiMotori());
                            motore.setDisable(false);
                        } else {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Attenzione");
                            alert.setHeaderText("Motore già presente");
                            alert.showAndWait();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Attenzione");
                        alert.setHeaderText("Inserisci tutti i campi del motore");
                        alert.showAndWait();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Attenzione");
                alert.setHeaderText("Non puoi eliminare ed allo stesso tempo aggiungere lo stesso motore");
                alert.showAndWait();
            }
            model.sostituisciAuto(auto);
            model.aggiornaFileCatalogo();
            model.caricaDaFile("src/main/resources/com/example/elaborato_ing/TXT/Catalogo.txt", model.getCatalogo());
        }

        model.openCloseFXML("FXML/ModificaAuto.fxml", event);
    }

    public void caricaImgs() {
        model.caricaImmaginiImageView(imageView1, imageView2, imageView3);
    }

    public void rimuoviImgs() {
        model.rimuoviImgs(imageView1, imageView2, imageView3);
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
/*
AGGIUNGI AUTO
pilure i checkbox selezionati dopo add auto
pulire i campi post modifica auto
se ce un solo colore disabilitare il checkbox
 */