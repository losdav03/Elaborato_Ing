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

import java.io.*;
import java.util.*;

public class InitController {
    @FXML
    private Label altezza, lunghezza, larghezza, peso, volume, alimentazione, motore, prezzo;

    @FXML
    private ComboBox<Marca> marca;

    @FXML
    private ComboBox<Modello> modello;

    @FXML
    private ComboBox<String> colori;

    @FXML
    private CheckBox infot, sensori, fari, sedili, scorta, vetri, interni, ruote, cruise;

    @FXML
    private Button acquistabtn, vendibtn;

    @FXML
    private ImageView img;

    private Map<Marca, List<Auto>> map;
    private Catalogo catalogo = new Catalogo();

    public void initialize() {
        String filePath = "src\\main\\java\\com\\example\\elaborato_ing\\Catalogo.txt";
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
        modello.setOnAction(e -> aggiornaImg());
    }

    private Map<Marca, List<Auto>> caricaFile(String file) {
        Map<Marca, List<Auto>> dati = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 12) {
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

                    Auto auto = new Auto(marca, modello, lunghezza, altezza, larghezza, peso, volume, motore);
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

    private void aggiornaImg() {
        Marca marcaSelezionata = marca.getValue();
        Modello modelloSelezionato = modello.getValue();

        if (marcaSelezionata != null && modelloSelezionato != null) {
            String imagePath = "/com/example/elaborato_ing/images/" + marcaSelezionata + "_" + modelloSelezionato + ".png";
            InputStream imageStream = getClass().getResourceAsStream(imagePath);

            if (imageStream != null) {
                Image image = new Image(imageStream);
                img.setImage(image);
            } else {
                System.err.println("Immagine non trovata: " + imagePath);
            }
        }
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

    public void goToUsatoForm(ActionEvent event) {
        loadScene("Usato.fxml", event);
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
}
