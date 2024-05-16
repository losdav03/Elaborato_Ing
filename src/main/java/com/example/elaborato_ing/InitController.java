package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;
import java.util.List;


public class InitController {
    @FXML
    private Label altezza;
    @FXML
    private Label lunghezza;
    @FXML
    private Label larghezza;
    @FXML
    private Label peso;
    @FXML
    private Label volume;
    @FXML
    private Label alimentazione;
    @FXML
    private Label motore;
    @FXML
    private Label prezzo;
    @FXML
    private ComboBox<Marca> marca;
    @FXML
    private ComboBox<Sede> sede;
    @FXML
    private ComboBox<String> modello;
    @FXML
    private ComboBox<String> colori;
    @FXML
    private Button acquistabtn, btnPDF, btnSx, btnDx,vendibtn;
    @FXML
    private ImageView img;
    @FXML
    private MenuButton menuProfilo;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox vBox;
    private final Model model = new Model();
    private int vista = 1;
    private Stage stage;


    public void initialize() {
        String filePath = "src/main/resources/com/example/elaborato_ing/TXT/Catalogo.txt";
        File file = new File(filePath);

        if (!file.exists()) {
            System.err.println("File non trovato: " + filePath);
            return;
        }
        sede.getItems().setAll(Sede.values());
        model.caricaDaFile(filePath, model.getCatalogo());
        model.setMarca(marca);
        marca.setOnAction(_ -> aggiornaModello());
        modello.setOnAction(_ -> aggiornaColori());
        vista = 1;
        colori.setOnAction(_ -> aggiornaImg());
        modello.setDisable(true);
        colori.setDisable(true);
        colori.getItems().clear();
        btnPDF.setVisible(false);
        btnSx.setDisable(true);
        btnDx.setDisable(true);
        menuProfilo.setDisable(true);



    }


    private void aggiornaModello() {

        img.setImage(null);
        modello.getItems().clear();
        colori.getItems().clear();
        colori.setDisable(true);
        modello.setDisable(true);
        btnSx.setDisable(true);
        btnDx.setDisable(true);


        List<AutoNuova> listaAuto = model.getMap().getOrDefault(marca.getValue(), Collections.emptyList());
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
            AutoNuova auto = model.getMap().values().stream().flatMap(List::stream).filter(a -> a.getModello().equals(modello.getValue())).findFirst().orElse(null);
            colori.setDisable(false);
            if (auto != null) {
                model.generaCheckBoxOptionalConfiguratore(auto, scrollPane, vBox,auto.getOptionalScelti(), prezzo);


                lunghezza.setText(String.valueOf(auto.getLunghezza()));
                altezza.setText(String.valueOf(auto.getAltezza()));
                larghezza.setText(String.valueOf(auto.getLarghezza()));
                peso.setText(String.valueOf(auto.getPeso()));
                volume.setText(String.valueOf(auto.getVolumeBagagliaio()));
                alimentazione.setText(String.valueOf(auto.getMotore().getAlimentazione()));
                motore.setText(String.valueOf(auto.getMotore().getNome()));
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

            InputStream imageStream = getClass().getResourceAsStream(model.getImmagineAuto(Marca.valueOf(String.valueOf(marca.getValue())), modello.getValue(), colori.getValue(), 1));
            if (imageStream != null) {
                Image image = new Image(imageStream);
                img.setImage(image);
            }
        }
    }


    public void goToUsatoForm() {
        model.openFXML("FXML/Usato.fxml");
    }


    public void btnSx() {
        String pathSx = "";
        switch (vista) {
            case 1 -> {
                vista = 3;
                pathSx = model.getImmagineAuto(Marca.valueOf(String.valueOf(marca.getValue())), modello.getValue(), colori.getValue(), vista);
            }
            case 2 -> {
                vista = 1;
                pathSx = model.getImmagineAuto(Marca.valueOf(String.valueOf(marca.getValue())), modello.getValue(), colori.getValue(), vista);
            }
            case 3 -> {
                vista = 2;
                pathSx = model.getImmagineAuto(Marca.valueOf(String.valueOf(marca.getValue())), modello.getValue(), colori.getValue(), vista);
            }
        }


        InputStream imageStream = getClass().getResourceAsStream(pathSx);
        if (imageStream != null) {
            Image image = new Image(imageStream);
            img.setImage(image);
        }
    }

    public void btnDx() {
        String pathDx = "";
        switch (vista) {
            case 1 -> {
                vista = 2;
                pathDx = model.getImmagineAuto(Marca.valueOf(String.valueOf(marca.getValue())), modello.getValue(), colori.getValue(), vista);
            }
            case 2 -> {
                vista = 3;
                pathDx = model.getImmagineAuto(Marca.valueOf(String.valueOf(marca.getValue())), modello.getValue(), colori.getValue(), vista);
            }
            case 3 -> {
                vista = 1;
                pathDx = model.getImmagineAuto(Marca.valueOf(String.valueOf(marca.getValue())), modello.getValue(), colori.getValue(), vista);
            }
        }

        InputStream imageStream = getClass().getResourceAsStream(pathDx);
        if (imageStream != null) {
            Image image = new Image(imageStream);
            img.setImage(image);

        }
    }

    public void acquistaFunction(ActionEvent event) throws IOException {
        if (acquistabtn.getText().equals("Login")) {
            model.openFXML("FXML/Login.fxml");
            if (model.getClienteLoggato() != null) {
                menuProfilo.setDisable(false);
                vendibtn.setDisable(false);
                acquistabtn.setText("Inoltra Preventivo");
            }

        } else {
            AutoNuova autoConfigurata = (AutoNuova) model.getMarcaModello(marca.getValue(), modello.getValue(), model.getMap());

            model.inoltraPreventivo(autoConfigurata, colori.getValue(), Integer.parseInt(prezzo.getText()), sede.getValue());
            // abilito il  bottone PDF
            btnPDF.setVisible(true);
        }


    }

    public void generaPDF() {

    }


    public void vediPreventivi(ActionEvent actionEvent) {
        model.openFXML("FXML/Riepilogo.fxml");
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void logOut() {
        model.eliminaCliente();
        stage.close();
        model.openFXML("FXML/Configuratore.fxml");
    }
}






