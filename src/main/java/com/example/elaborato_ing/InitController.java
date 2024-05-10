package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.util.*;
import java.util.List;


public class InitController {
    @FXML
    private Label altezza, lunghezza, larghezza, peso, volume, alimentazione, motore, prezzo;

    @FXML
    private ComboBox<Marca> marca;

    @FXML
    private ComboBox<String> modello;

    @FXML
    private ComboBox<String> colori;

    @FXML
    private CheckBox infot, sensori, fari, sedili, scorta, vetri, interni, ruote, cruise;

    @FXML
    private Button acquistabtn, btnPDF, btnSx, btnDx;

    @FXML
    private ImageView img;
    private Map<Marca, List<AutoNuova>> map;
    private final Catalogo catalogo = new Catalogo();
    private Model model;
    private int vista = 1;


    public void initialize() {

        model = new Model();

        String filePath = "src/main/resources/com/example/elaborato_ing/TXT/Catalogo.txt";
        File file = new File(filePath);

        if (!file.exists()) {
            System.err.println("File non trovato: " + filePath);
            return;
        }

        map = model.caricaDaFile(filePath, catalogo);
        marca.getItems().addAll(map.keySet());
        marca.setOnAction(_ -> aggiornaModello());
        modello.setOnAction(_ -> aggiornaColori());
        vista = 1;
        colori.setOnAction(_ -> aggiornaImg());

        modello.setDisable(true);
        colori.setDisable(true);
        colori.getItems().clear();
        abilitaOption(true);
        btnPDF.setVisible(false);
        btnSx.setDisable(true);
        btnDx.setDisable(true);
    }


    private void aggiornaModello() {

        img.setImage(null);
        modello.getItems().clear();
        colori.getItems().clear();
        colori.setDisable(true);
        modello.setDisable(true);
        abilitaOption(true);
        btnSx.setDisable(true);
        btnDx.setDisable(true);


        List<AutoNuova> listaAuto = map.getOrDefault(marca.getValue(), Collections.emptyList());
        List<String> listaModelli = listaAuto.stream().map(Auto::getModello).distinct().toList();

        if (listaModelli.isEmpty()) {
            modello.getItems().clear();
            colori.getItems().clear();
            colori.setDisable(true);
            modello.setDisable(true);
        } else {
            modello.getItems().setAll(listaModelli);
            modello.setDisable(false);
        }
    }

    private void aggiornaColori() {
        if (marca.getValue() != null && modello.getValue() != null) {
            AutoNuova auto = map.values().stream().flatMap(List::stream).filter(a -> a.getModello().equals(modello.getValue())).findFirst().orElse(null);

            colori.setDisable(false);
            abilitaOption(false);

            if (auto != null) {
                lunghezza.setText(String.valueOf(auto.getLunghezza()));
                altezza.setText(String.valueOf(auto.getAltezza()));
                larghezza.setText(String.valueOf(auto.getLarghezza()));
                peso.setText(String.valueOf(auto.getPeso()));
                volume.setText(String.valueOf(auto.getVolumeBagagliaio()));
                alimentazione.setText(String.valueOf(auto.getMotore().getAlimentazione()));
                motore.setText(String.valueOf(auto.getMotore()));
                prezzo.setText(String.valueOf(auto.getPrezzo()));

                colori.getItems().clear();
                colori.getItems().addAll(auto.getColori());
                colori.setValue(colori.getItems().getFirst());
            }
        }
    }

    private void aggiornaImg() {

        if (marca.getValue() != null && modello.getValue() != null && colori.getValue() != null) {
            btnSx.setDisable(false);
            btnDx.setDisable(false);

            String path = catalogo.getAuto(marca.getValue(), modello.getValue()).getImmagine(colori.getValue(), vista);
            Image image = new Image(path);
            img.setImage(image);
        }
    }

    private void abilitaOption(boolean abilita) {
        infot.setDisable(abilita);
        sensori.setDisable(abilita);
        fari.setDisable(abilita);
        sedili.setDisable(abilita);
        scorta.setDisable(abilita);
        vetri.setDisable(abilita);
        interni.setDisable(abilita);
        ruote.setDisable(abilita);
        cruise.setDisable(abilita);
    }

    public void goToUsatoForm() {
        model.openFXML("FXML/Usato.fxml");
    }


    public void addOption() {
        String modelloSelezionato = modello.getValue();
        AutoNuova auto = map.values().stream().flatMap(List::stream).filter(a -> a.getModello().equals(modelloSelezionato)).findFirst().orElse(null);

        if (auto != null) {
            int costoAggiuntivo = auto.getPrezzo() / 300;
            int costoCheckBox = 0;

            if (infot.isSelected()) {
                costoCheckBox += costoAggiuntivo;
            }
            if (sensori.isSelected()) {
                costoCheckBox += costoAggiuntivo;
            }
            if (fari.isSelected()) {
                costoCheckBox += costoAggiuntivo;
            }
            if (sedili.isSelected()) {
                costoCheckBox += costoAggiuntivo;
            }
            if (scorta.isSelected()) {
                costoCheckBox += costoAggiuntivo;
            }
            if (vetri.isSelected()) {
                costoCheckBox += costoAggiuntivo;
            }
            if (interni.isSelected()) {
                costoCheckBox += costoAggiuntivo;
            }
            if (ruote.isSelected()) {
                costoCheckBox += costoAggiuntivo;
            }
            if (cruise.isSelected()) {
                costoCheckBox += costoAggiuntivo;
            }

            int costo = auto.getPrezzo() + costoCheckBox;
            prezzo.setText(String.valueOf(costo));
        }
    }

    public void btnSx() {
        String pathSx = "";
        switch (vista) {
            case 1 -> {
                vista = 3;
                pathSx = catalogo.getAuto(marca.getValue(), modello.getValue()).getImmagine(colori.getValue(), vista);
            }
            case 2 -> {
                vista = 1;
                pathSx = catalogo.getAuto(marca.getValue(), modello.getValue()).getImmagine(colori.getValue(), vista);
            }
            case 3 -> {
                vista = 2;
                pathSx = catalogo.getAuto(marca.getValue(), modello.getValue()).getImmagine(colori.getValue(), vista);
            }
        }


        Image image = new Image(pathSx);
        img.setImage(image);

    }

    public void btnDx() {
        String pathDx = "";
        switch (vista) {
            case 1 -> {
                vista = 2;
                pathDx = catalogo.getAuto(marca.getValue(), modello.getValue()).getImmagine(colori.getValue(), vista);
            }
            case 2 -> {
                vista = 3;
                pathDx = catalogo.getAuto(marca.getValue(), modello.getValue()).getImmagine(colori.getValue(), vista);
            }
            case 3 -> {
                vista = 1;
                pathDx = catalogo.getAuto(marca.getValue(), modello.getValue()).getImmagine(colori.getValue(), vista);
            }
        }

        Image image = new Image(pathDx);
        img.setImage(image);

    }

    public void acquistaFunction(ActionEvent event) throws IOException {
        if (acquistabtn.getText().equals("Login")) {
            model.openFXML("FXML/Login.fxml");
            acquistabtn.setText("Inoltra Preventivo");
        } else {
            // manca codice per esportare e aggiungere il preventivo in un file txt e creare l'oggetto Preventivo
            AutoNuova autoConfigurata = model.getMarcaModello(marca.getValue(), modello.getValue(), map);
            autoConfigurata.setOptional(infot.isSelected(), sensori.isSelected(), fari.isSelected(), sedili.isSelected(), scorta.isSelected(), vetri.isSelected(), interni.isSelected(), ruote.isSelected(), cruise.isSelected());
            model.inoltraPreventivo(autoConfigurata, colori.getValue(), Integer.parseInt(prezzo.getText()));
            // abilito il  bottone PDF
            btnPDF.setVisible(true);
        }


    }

    public void generaPDF() {

    }


    public void vediPreventivi(ActionEvent actionEvent) {
        model.openFXML("FXML/Riepilogo.fxml");
    }

    public void logOut() {
        //  model.OpenCloseFXML("FXML/Configuratore.fxml", null);
    }
}






