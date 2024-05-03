package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class InitController {
    @FXML
    private Label altezza, lunghezza, larghezza, peso, volume, alimentazione, motore, prezzo;

    private int costo = 0;

    @FXML
    private ComboBox<Marca> marca;

    @FXML
    private ComboBox<Modello> modello;

    @FXML
    private ComboBox<String> colori;

    @FXML
    private CheckBox infot, sensori, fari, sedili, scorta, vetri, interni, ruote, cruise;

    @FXML
    private Button acquistabtn, vendibtn, btnSx, btnDx;

    @FXML
    private ImageView img;


    private Map<Marca, List<Auto>> map;
    private Catalogo catalogo = new Catalogo();

    public void initialize() {
        String filePath = "src\\main\\resources\\com\\example\\elaborato_ing\\TXT\\Catalogo.txt";
        File file = new File(filePath);

        if (!file.exists()) {
            System.err.println("File non trovato: " + filePath);
            return;
        }

        map = caricaFile(filePath);
        System.out.println("Chiavi nella mappa: " + map.keySet());
        System.out.println("Valori in marca ComboBox: " + marca.getItems());
        marca.getItems().addAll(map.keySet());
        marca.setOnAction(e -> aggiornaModello());
        modello.setOnAction(e -> aggiornaConfiguratore());

        colori.getItems().setAll("BIANCO", "NERO", "ROSSO");
        colori.setDisable(true);
        infot.setDisable(true);
        sensori.setDisable(true);
        fari.setDisable(true);
        sedili.setDisable(true);
        scorta.setDisable(true);
        vetri.setDisable(true);
        interni.setDisable(true);
        ruote.setDisable(true);
        cruise.setDisable(true);
    }

    private Map<Marca, List<Auto>> caricaFile(String file) {
        Map<Marca, List<Auto>> dati = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 14) {
                    Marca marca = Marca.valueOf(parts[0].trim());
                    Modello modello = Modello.valueOf(parts[1].trim());
                    double lunghezza = Double.parseDouble(parts[2]);
                    double altezza = Double.parseDouble(parts[3]);
                    double larghezza = Double.parseDouble(parts[4]);
                    double peso = Double.parseDouble(parts[5]);
                    double volume = Double.parseDouble(parts[6]);
                    String nomeMotore = parts[7];
                    Alimentazione alimentazione = Alimentazione.valueOf(parts[8].trim());
                    int cilindrata = Integer.parseInt(parts[9].trim());
                    int potenza = Integer.parseInt(parts[10].trim());
                    double consumi = Double.parseDouble(parts[11]);
                    Motore motore = new Motore(nomeMotore, alimentazione, cilindrata, potenza, consumi);
                    costo = Integer.parseInt(parts[12]);
                    String sconto = parts[13];
                    Auto auto = new Auto(marca, modello, lunghezza, altezza, larghezza, peso, volume, motore, costo, sconto);
                    catalogo.add(auto);
                    dati.computeIfAbsent(marca, k -> new ArrayList<>()).add(auto);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File non trovato: " + file);
        } catch (IOException e) {
            System.err.println("Errore nella lettura del file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Errore nei dati: " + e.getMessage());
        }
        return dati;
    }

    private void aggiornaModello() {
        Marca marcaSelezionata = marca.getValue();

        if (marcaSelezionata != null) {
            List<Auto> listaAuto = map.getOrDefault(marcaSelezionata, Collections.emptyList());
            List<Modello> listaModelli = listaAuto.stream().map(Auto::getModello).toList();

            modello.getItems().setAll(listaModelli);
            modello.setDisable(false);
        } else {
            modello.getItems().clear();
            modello.setDisable(true);
        }
    }


    private void aggiornaConfiguratore() {
        Marca marcaSelezionata = marca.getValue();
        Modello modelloSelezionato = modello.getValue();

        if (marcaSelezionata != null && modelloSelezionato != null) {
            String imagePath = "/com/example/elaborato_ing/images/" + marcaSelezionata.toString().toLowerCase() + modelloSelezionato.toString().toLowerCase() + "bianco1" + ".png";
            InputStream imageStream = getClass().getResourceAsStream(imagePath);

            if (imageStream != null) {
                Image image = new Image(imageStream);
                img.setImage(image);
                img.setUserData(imagePath);
            } else {
                System.err.println("Immagine non trovata: " + imagePath);
            }
            // aggiorno le informazioni della macchina (peso larghezza lunghezza ...)
            Auto auto = map.values().stream().flatMap(List::stream).filter(a -> a.getModello().equals(modelloSelezionato)).findFirst().orElse(null);

            colori.setDisable(modelloSelezionato.toString().equals("CYBERTRUCK"));

            infot.setDisable(false);
            sensori.setDisable(false);
            fari.setDisable(false);
            sedili.setDisable(false);
            scorta.setDisable(false);
            vetri.setDisable(false);
            interni.setDisable(false);
            ruote.setDisable(false);
            cruise.setDisable(false);

            if (auto != null) {
                lunghezza.setText(String.valueOf(auto.getLunghezza()));
                altezza.setText(String.valueOf(auto.getAltezza()));
                larghezza.setText(String.valueOf(auto.getLarghezza()));
                peso.setText(String.valueOf(auto.getPeso()));
                volume.setText(String.valueOf(auto.getVolumeBagagliaio()));
                alimentazione.setText(String.valueOf(auto.getAlimentazione()));
                motore.setText(String.valueOf(auto.getMotore()));
                prezzo.setText(String.valueOf(auto.getCosto()));
            }
        }
    }

    public void goToUsatoForm(ActionEvent event) {
        loadScene("FXML/Usato.fxml", event);
    }

    public void loadScene(String fxmlFile, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            if (event != null && event.getSource() instanceof Node) {
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(primaryStage);
                primaryStage.close();
            }
        } catch (IOException e) {
            System.err.println("Errore nel caricamento della scena: " + e.getMessage());
        }
    }

    public void addOption(ActionEvent actionEvent) {
        Modello modelloSelezionato = modello.getValue();
        Auto auto = map.values().stream()
                .flatMap(List::stream)
                .filter(a -> a.getModello().equals(modelloSelezionato))
                .findFirst()
                .orElse(null);

        if (auto != null) {
            int costoAggiuntivo = auto.getCosto() / 300;
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

            costo = auto.getCosto() + costoCheckBox;
            prezzo.setText(String.valueOf(costo));
        }
    }

    public void vistaAutoPosteriore(ActionEvent actionEvent) {
        String marcaSelezionata = String.valueOf(marca.getValue()).toLowerCase();
        String modelloSelezionato = String.valueOf(modello.getValue()).toLowerCase();


        String imgPathCorrente = (String) img.getUserData();
        String basePath = "/com/example/elaborato_ing/images/";

        String pathPosteriore = basePath + marcaSelezionata + modelloSelezionato + "bianco3.png";
        String pathLaterale = basePath + marcaSelezionata + modelloSelezionato + "bianco2.png";
        String pathAnteriore = basePath + marcaSelezionata + modelloSelezionato + "bianco1.png";

        String nuovoPath;

        if (pathPosteriore.equals(imgPathCorrente)) {
            nuovoPath = pathLaterale;
        } else if (pathLaterale.equals(imgPathCorrente)) {
            nuovoPath = pathAnteriore;
        } else if (pathAnteriore.equals(imgPathCorrente)) {
            nuovoPath = pathPosteriore;
        } else {
            System.err.println("Percorso dell'immagine non riconosciuto: " + imgPathCorrente);
            return;
        }

        InputStream imageStream = getClass().getResourceAsStream(nuovoPath);
        if (imageStream != null) {
            Image nuovaImmagine = new Image(imageStream);
            img.setImage(nuovaImmagine);
            img.setUserData(nuovoPath);
        } else {
            System.err.println("Immagine non trovata: " + nuovoPath);
        }
    }

    public void vistaAutoLaterale(ActionEvent actionEvent) {
        String marcaSelezionata = String.valueOf(marca.getValue()).toLowerCase();
        String modelloSelezionato = String.valueOf(modello.getValue()).toLowerCase();


        String imgPathCorrente = (String) img.getUserData();
        String basePath = "/com/example/elaborato_ing/images/";

        String pathPosteriore = basePath + marcaSelezionata + modelloSelezionato + "bianco3.png";
        String pathLaterale = basePath + marcaSelezionata + modelloSelezionato + "bianco2.png";
        String pathAnteriore = basePath + marcaSelezionata + modelloSelezionato + "bianco1.png";

        // Controlla se il percorso dell'immagine corrente corrisponde a uno dei percorsi noti
        String nuovoPath;

        if (pathPosteriore.equals(imgPathCorrente)) {
            nuovoPath = pathAnteriore;
        } else if (pathLaterale.equals(imgPathCorrente)) {
            nuovoPath = pathPosteriore;
        } else if (pathAnteriore.equals(imgPathCorrente)) {
            nuovoPath = pathLaterale;
        } else {
            System.err.println("Percorso dell'immagine non riconosciuto: " + imgPathCorrente);
            return;
        }

        // Carica e imposta la nuova immagine
        InputStream imageStream = getClass().getResourceAsStream(nuovoPath);
        if (imageStream != null) {
            Image nuovaImmagine = new Image(imageStream);
            img.setImage(nuovaImmagine);
            img.setUserData(nuovoPath);
        } else {
            System.err.println("Immagine non trovata: " + nuovoPath);
        }
    }

}







