package com.example.elaborato_ing;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Initcontroller {
    @FXML
    private Label altezza, lunghezza, larghezza, peso, volume, alimentazione, motore, prezzo;

    @FXML
    private ComboBox<String> marca, modello, colori;

    @FXML
    private CheckBox infot, sensori, fari,sedili, scorta, vetri, interni, ruote, cruise;

    @FXML
    private Button acquistabtn, vendibtn;

    @FXML
    private ImageView img;
    public void initialize() {
        String vuota = "";
        marca.getItems().addAll("DODGE", "FERRARI", "LAMBORGHINI", "TESLA", "AUDI", "JEEP");
        colori.getItems().addAll("BIANCO", "NERO", "ROSSO");
        altezza.setText(vuota);
        lunghezza.setText(vuota);
        larghezza.setText(vuota);
        peso.setText(vuota);
        volume.setText(vuota);
        alimentazione.setText(vuota);
        motore.setText(vuota);
        prezzo.setText(vuota);
        
        marca.setOnAction(e->aggiornaModello());
        modello.setOnAction(e->aggiornaImg());

    }

    private void aggiornaImg() {
        String marcaS = marca.getValue();
        String modelloS = modello.getValue();

        if(marcaS != null && modelloS !=null){
            String path = "src/main/resources/com/example/elaborato_ing/sfondoLogin.jpeg";
            Image nuova = new Image(path,true);
            img.setImage(nuova);
        }
    }

    private void aggiornaModello() {
        String marcaS = (String)marca.getValue();
        if(marcaS!=null){
            modello.setDisable(false);
            modello.getItems().clear();
            if("DODGE".equals(marcaS)){
                modello.getItems().addAll("CHARGER", "CHALLENGER", "DURANGO");
            } else if ("FERRARI".equals(marcaS)) {
                modello.getItems().addAll("SF90", "PORTOFINO", "GTS296");
            } else if ("LAMBORGHINI".equals(marcaS)) {
                modello.getItems().addAll("URUS", "REVUELTO", "HURUCAN");
            } else if ("TESLA".equals(marcaS)) {
                modello.getItems().addAll("MODELX", "MODELY", "CYBERTRUCK");
            }
            aggiornaImg();
        }
        else {
            modello.setDisable(true);
        }
    }
}
