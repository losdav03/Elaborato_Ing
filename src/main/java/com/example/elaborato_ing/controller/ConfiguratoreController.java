package com.example.elaborato_ing.controller;

import com.example.elaborato_ing.model.Model;
import com.example.elaborato_ing.utils.*;
import com.itextpdf.text.DocumentException;
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

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ConfiguratoreController {
    @FXML
    private Label altezza, lunghezza, larghezza, peso, volume, alimentazione, prezzo, prezzoScontato, labelEuro, labelScontato;
    @FXML
    private ComboBox<Marca> marca;
    @FXML
    private ComboBox<Sede> sede;
    @FXML
    private ComboBox<String> modello, colori, motore;
    @FXML
    private Button btnPDF, btnSx, btnDx, acquistaBtn, vendiBtn, preventiviBtn, logOutBtn;
    @FXML
    private ImageView img;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox vBox;
    private Model model = Model.getInstance();
    private int vista = 1;
    private AutoNuova autoPDF = null;


    public void initialize() {
        String filePath = "src/main/resources/com/example/elaborato_ing/TXT/Catalogo.txt";
        File file = new File(filePath);

        if (!file.exists()) {
            System.err.println("File non trovato: " + filePath);
            return;
        }

        model.caricaDaFile(filePath, model.getCatalogo());
        model.caricaMappaAutoUsate();
        model.setMarca(marca);
        marca.setOnAction(_ -> aggiornaModello());
        modello.setOnAction(_ -> aggiornaColori());
        motore.setOnAction(_ -> aggiornaAlimentazione());
        vista = 1;
        colori.setOnAction(_ -> {
            try {
                aggiornaImg();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        modello.setDisable(true);
        colori.setDisable(true);
        motore.setDisable(true);
        sede.setDisable(true);
        colori.getItems().clear();
        vendiBtn.setDisable(true);
        btnPDF.setVisible(false);
        btnSx.setDisable(true);
        btnDx.setDisable(true);
        preventiviBtn.setDisable(true);
        logOutBtn.setDisable(true);
        prezzoScontato.setVisible(false);
        labelScontato.setVisible(false);
        labelEuro.setVisible(false);


        // mi serve per riaggiornare il catalogo dopo eliminazione optional nell'amministrazione
        model.caricaOptionalDaFile();
    }

    private void aggiornaAlimentazione() {
        if (marca.getValue() != null && modello.getValue() != null) {
            alimentazione.setText("");
            alimentazione.setText(model.aggiornaAlix(Marca.valueOf(String.valueOf(marca.getValue())), modello.getValue(), motore.getValue()));
        }
    }

    private void aggiornaModello() {
        model.aggiornaFileCatalogo();
        model.caricaDaFile("src/main/resources/com/example/elaborato_ing/TXT/Catalogo.txt", model.getCatalogo());
        img.setImage(null);
        modello.getItems().clear();
        colori.getItems().clear();
        motore.getItems().clear();
        vBox.getChildren().clear();
        lunghezza.setText("");
        altezza.setText("");
        larghezza.setText("");
        peso.setText("");
        volume.setText("");
        alimentazione.setText("");
        prezzo.setText("");
        prezzoScontato.setText("");
        prezzoScontato.setVisible(false);
        labelScontato.setVisible(false);
        labelEuro.setVisible(false);
        btnPDF.setVisible(false);

        sede.getItems().clear();
        colori.setDisable(true);
        motore.setDisable(true);
        sede.setDisable(true);
        modello.setDisable(true);
        btnSx.setDisable(true);
        btnDx.setDisable(true);

        List<AutoNuova> listaAuto = model.getMapAutoNuova().getOrDefault(marca.getValue(), Collections.emptyList());
        List<String> listaModelli = listaAuto.stream().map(Auto::getModello).distinct().toList();

        if (listaModelli.isEmpty()) {
            modello.getItems().clear();
            colori.getItems().clear();
            colori.setDisable(true);
            sede.setDisable(true);
            modello.setDisable(true);
            motore.setDisable(true);
        } else {
            modello.getItems().setAll(listaModelli);
            modello.setDisable(false);
            btnPDF.setVisible(false);
        }
    }

    private void aggiornaColori() {
        if (marca.getValue() != null && modello.getValue() != null) {
            AutoNuova auto = model.getMapAutoNuova().values().stream().flatMap(List::stream).filter(a -> a.getModello().equals(modello.getValue())).findFirst().orElse(null);
            colori.setDisable(false);
            sede.setDisable(false);
            motore.setDisable(false);
            btnPDF.setVisible(false);
            if (auto != null) {
                model.generaCheckBoxOptionalConfiguratore(auto, scrollPane, vBox, auto.getOptionalScelti(), prezzo, prezzoScontato);


                lunghezza.setText(auto.getLunghezza() + " cm");
                altezza.setText(auto.getAltezza() + " cm");
                larghezza.setText(auto.getLarghezza() + " cm");
                peso.setText(auto.getPeso() + " kg");
                volume.setText(auto.getVolumeBagagliaio() + " L");
                prezzo.setText(String.valueOf(auto.getPrezzo()));
                colori.getItems().clear();
                colori.getItems().addAll(auto.getColori());
                colori.setValue(colori.getItems().getFirst());
                motore.getItems().clear();
                List<String> nomiMotori = new ArrayList<>();
                for (Motore m : auto.getMotori()) {
                    nomiMotori.add(m.getNome());
                }
                motore.getItems().addAll(nomiMotori);
                motore.setValue(motore.getItems().getFirst());
                sede.getItems().setAll(Sede.values());
                sede.setValue(sede.getItems().getFirst());
                if (Integer.parseInt(prezzo.getText()) != auto.calcolaPrezzoScontato()) {
                    prezzoScontato.setVisible(true);
                    labelScontato.setVisible(true);
                    labelEuro.setVisible(true);
                    prezzoScontato.setText(String.valueOf(auto.calcolaPrezzoScontato()));
                } else {
                    prezzoScontato.setVisible(false);
                    labelScontato.setVisible(false);
                    labelEuro.setVisible(false);
                }

            }

        }
    }

    private void aggiornaImg() throws FileNotFoundException {

        if (marca.getValue() != null && modello.getValue() != null && colori.getValue() != null) {
            btnSx.setDisable(false);
            btnDx.setDisable(false);

            String pathImg = model.getImmagineAuto(Marca.valueOf(String.valueOf(marca.getValue())), modello.getValue(), colori.getValue(), 1, 0, "");
            File file = new File(pathImg);
            FileInputStream stream = new FileInputStream(file);
            Image image = new Image(stream);
            img.setImage(image);

        }
    }

    public void btnSx() throws FileNotFoundException {
        String pathSx = "";
        switch (vista) {
            case 1 -> {
                vista = 3;
                pathSx = model.getImmagineAuto(Marca.valueOf(String.valueOf(marca.getValue())), modello.getValue(), colori.getValue(), vista, 0, "");
            }
            case 2 -> {
                vista = 1;
                pathSx = model.getImmagineAuto(Marca.valueOf(String.valueOf(marca.getValue())), modello.getValue(), colori.getValue(), vista, 0, "");
            }
            case 3 -> {
                vista = 2;
                pathSx = model.getImmagineAuto(Marca.valueOf(String.valueOf(marca.getValue())), modello.getValue(), colori.getValue(), vista, 0, "");
            }
        }


        File file = new File(pathSx);
        FileInputStream stream = new FileInputStream(file);
        Image image = new Image(stream);
        img.setImage(image);
    }

    public void btnDx() throws FileNotFoundException {
        String pathDx = "";
        switch (vista) {
            case 1 -> {
                vista = 2;
                pathDx = model.getImmagineAuto(Marca.valueOf(String.valueOf(marca.getValue())), modello.getValue(), colori.getValue(), vista, 0, "");
            }
            case 2 -> {
                vista = 3;
                pathDx = model.getImmagineAuto(Marca.valueOf(String.valueOf(marca.getValue())), modello.getValue(), colori.getValue(), vista, 0, "");
            }
            case 3 -> {
                vista = 1;
                pathDx = model.getImmagineAuto(Marca.valueOf(String.valueOf(marca.getValue())), modello.getValue(), colori.getValue(), vista, 0, "");
            }
        }

        File file = new File(pathDx);
        FileInputStream stream = new FileInputStream(file);
        Image image = new Image(stream);
        img.setImage(image);
    }

    @FXML
    public void acquistaFunction() throws IOException {
        if (acquistaBtn.getText().equals("Log in")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/elaborato_ing/viewUtente/Login.fxml"));
            Parent root = loader.load();

            LoginController loginController = loader.getController();
            loginController.setInitController(this);

            Stage loginStage = new Stage();
            loginStage.initModality(Modality.APPLICATION_MODAL);

            loginStage.setScene(new Scene(root));
            loginStage.setResizable(false);

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
                AutoNuova autoConfigurata = model.getMarcaModelloAutoNuova(marca.getValue(), modello.getValue(), model.getMapAutoNuova());
                if (autoConfigurata != null) {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confermi l'inoltro del preventivo?", ButtonType.YES, ButtonType.NO);
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.YES) {
                            try {
                                autoConfigurata.soloUnMotore(motore.getValue());
                                if (!prezzoScontato.getText().isEmpty()) {
                                    model.inoltraPreventivo(autoConfigurata, colori.getValue(), Integer.parseInt(prezzoScontato.getText()), sede.getValue());
                                } else {
                                    model.inoltraPreventivo(autoConfigurata, colori.getValue(), Integer.parseInt(prezzo.getText()), sede.getValue());
                                }
                                // Abilita il bottone PDF
                                autoPDF = autoConfigurata;
                                btnPDF.setVisible(true);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }

                    });
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Attenzione");
                alert.setHeaderText("Campi mancanti!!!");
                alert.setContentText("Assicurati di aver completato tutti i campi selezionabili");
                alert.showAndWait();
            }
        }

    }


    @FXML
    public void goToUsatoForm(ActionEvent event) {
        model.openFXML("/com/example/elaborato_ing/viewUtente/Usato.fxml", event);
    }

    @FXML
    public void generaPDF() throws DocumentException, FileNotFoundException {
        model.generaPDF(autoPDF, colori.getValue(), Integer.parseInt(prezzo.getText()), prezzoScontato.getText().isEmpty() ? 0 : Integer.parseInt(prezzoScontato.getText()));
    }

    @FXML
    public void vediPreventivi(ActionEvent event) {
        model.caricaMappaAutoUsate();
        model.openFXML("/com/example/elaborato_ing/viewUtente/Riepilogo.fxml", event);
    }

    @FXML
    public void logOut(ActionEvent event) throws IOException {
        model.eliminaCliente();
        model.openCloseFXML("/com/example/elaborato_ing/viewUtente/Configuratore.fxml", event);
    }
}
