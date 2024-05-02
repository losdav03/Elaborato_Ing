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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InitController {
    @FXML
    private Label altezza, lunghezza, larghezza, peso, volume, alimentazione, motore, prezzo;

    @FXML
    private ComboBox<String> marca, modello, colori;

    @FXML
    private CheckBox infot, sensori, fari, sedili, scorta, vetri, interni, ruote, cruise;

    @FXML
    private Button acquistabtn, vendibtn;

    @FXML
    private ImageView img;
    private Map<Marca, List<Auto>> map;
    Catalogo catalogo = new Catalogo();

    public void initialize() {
        map=caricaFile("Catalogo.txt");

        marca.getItems().addAll(String.valueOf(map.keySet()));
        marca.setOnAction(e->aggiornaModello());
        modello.setOnAction(e -> aggiornaImg());
    }

    private Map<Marca, List<Auto>> caricaFile(String file) {
        Map<Marca, List<Auto>> dati = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 13) {
                    Marca marca = Marca.valueOf(parts[0].trim());
                    Modello modello = Modello.valueOf(parts[1].trim());
                    Double lunghezza = Double.parseDouble(parts[2]);
                    Double altezza = Double.parseDouble(parts[3]);
                    Double larghezza = Double.parseDouble(parts[4]);
                    Double peso = Double.parseDouble(parts[5]);
                    Double volume = Double.parseDouble(parts[6]);
                    String nome = parts[7];
                    Alimentazione alimentazione = Alimentazione.valueOf(parts[8]);
                    int cilindrata = Integer.parseInt(parts[9]);
                    int potenza = Integer.parseInt(parts[10]);
                    double consumi = Double.parseDouble(parts[11]);;
                    Motore motore = new Motore(nome,alimentazione,cilindrata,potenza,consumi);

                    Auto auto = new Auto(marca, modello, lunghezza, altezza,larghezza,peso,volume,motore);
                    catalogo.add(auto);
                    dati.computeIfAbsent(marca, k -> new ArrayList<>()).add(auto);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dati;
    }

    private void aggiornaImg() {
        String marcaS = marca.getValue();
        String modelloS = modello.getValue();

        if (marcaS != null && modelloS != null) {
            String imagePath = "/com/example/elaborato_ing/images/" + marcaS + modelloS + ".png";
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            img.setImage(image);
        }
    }

    private void aggiornaModello() {
        String marcaS = marca.getValue();
        if (marcaS != null) {
            modello.setDisable(false);
            modello.getItems().clear();
            switch (marcaS) {
                case "DODGE":
                    modello.getItems().addAll("CHARGER", "CHALLENGER", "DURANGO");
                    break;
                case "FERRARI":
                    modello.getItems().addAll("SF90", "PORTOFINO", "GTS296");
                    break;
                case "LAMBORGHINI":
                    modello.getItems().addAll("URUS", "REVUELTO", "HURUCAN");
                    break;
                case "TESLA":
                    modello.getItems().addAll("MODELX", "MODELY", "CYBERTRUCK");
                    break;
                case "AUDI":
                    modello.getItems().addAll("Q8etron", "A8", "RS3");
                    break;
                case "JEEP":
                    modello.getItems().addAll("RENEGADE", "COMPASS", "GLADIETOR");
                    break;
            }
        } else {
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

            if (event != null && event.getSource() instanceof Node) {
                // Se l'evento non è null e proviene da un Node
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                primaryStage.close(); // Chiudi la finestra precedente se necessario
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            }
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
