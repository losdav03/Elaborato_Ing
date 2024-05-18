package com.example.elaborato_ing;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
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
    private Button btnPDF, btnSx, btnDx, acquistaBtn, vendiBtn,preventiviBtn,logOutBtn;
    @FXML
    private ImageView img;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox vBox;
    private final Model model = new Model();
    private int vista = 1;


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
        sede.setDisable(true);
        colori.getItems().clear();
        vendiBtn.setDisable(true);
        btnPDF.setVisible(false);
        btnSx.setDisable(true);
        btnDx.setDisable(true);
        preventiviBtn.setDisable(true);
        logOutBtn.setDisable(true);

        // mi serve per riaggiornare il catalogo dopo eliminazione optional nell'amministrazione
        model.caricaOptionalDaFile();
    }

    private void aggiornaModello() {

        img.setImage(null);
        modello.getItems().clear();
        colori.getItems().clear();
        colori.setDisable(true);
        sede.setDisable(true);
        modello.setDisable(true);
        btnSx.setDisable(true);
        btnDx.setDisable(true);


        List<AutoNuova> listaAuto = model.getMap().getOrDefault(marca.getValue(), Collections.emptyList());
        List<String> listaModelli = listaAuto.stream().map(Auto::getModello).distinct().toList();

        if (listaModelli.isEmpty()) {
            modello.getItems().clear();
            colori.getItems().clear();
            colori.setDisable(true);
            sede.setDisable(true);
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
            sede.setDisable(false);
            if (auto != null) {
                model.generaCheckBoxOptionalConfiguratore(auto, scrollPane, vBox, auto.getOptionalScelti(), prezzo);


                lunghezza.setText(auto.getLunghezza()+" cm");
                altezza.setText(auto.getAltezza()+" cm");
                larghezza.setText(auto.getLarghezza()+" cm");
                peso.setText(auto.getPeso()+" kg");
                volume.setText(auto.getVolumeBagagliaio()+" L");
                alimentazione.setText(String.valueOf(auto.getMotore().getAlimentazione()));
                motore.setText(auto.getMotore().getNome());
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

    @FXML
    public void acquistaFunction() throws IOException {
        if (acquistaBtn.getText().equals("Log in")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/Login.fxml"));
            Parent root = loader.load();

            LoginController loginController = loader.getController();
            loginController.setInitController(this);

            Stage loginStage = new Stage();
            loginStage.initModality(Modality.APPLICATION_MODAL);

            loginStage.setScene(new Scene(root));
            loginStage.show();

            // Aggiungi il listener per il cambio del nome del bottone quando lo stage del login viene chiuso
            loginStage.setOnHiding((WindowEvent _) -> {
                // Esegui il controllo qui, ad esempio controlla se l'utente è loggato
                if (model.getCliente() != null && model.getCliente().getEmail() != null) {
                    acquistaBtn.setText("Inoltra Preventivo");
                    vendiBtn.setDisable(false);
                    preventiviBtn.setDisable(false);
                    logOutBtn.setDisable(false);
                }
            });

        } else {
            // Controlli per vedere se il preventivo è fattibile
            if (colori.getValue() != null && sede.getValue() != null) {
                AutoNuova autoConfigurata = model.getMarcaModello(marca.getValue(), modello.getValue(), model.getMap());
                prezzo.setText(""+autoConfigurata.calcolaPrezzoScontato());
                model.inoltraPreventivo(autoConfigurata, colori.getValue(), Integer.parseInt(prezzo.getText()), sede.getValue());
                // Abilita il bottone PDF
                btnPDF.setVisible(true);
            }
        }

    }


    @FXML
    public void goToUsatoForm(ActionEvent event) {
        model.openFXML("FXML/Usato.fxml", event);
    }

    @FXML
    public void generaPDF() {

    }
    @FXML
    public void vediPreventivi(ActionEvent event) {
        model.openFXML("FXML/Riepilogo.fxml", event);
    }

    @FXML
    public void logOut(ActionEvent event) throws IOException {
        model.eliminaCliente();
        model.OpenCloseFXML("FXML/Configuratore.fxml", event);
    }


}






