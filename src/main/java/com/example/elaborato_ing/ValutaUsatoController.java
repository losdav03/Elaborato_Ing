package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class ValutaUsatoController {
    @FXML
    private TextField prezzo;
    @FXML
    private ListView<String> listaPreventivi;
    @FXML
    private Button valutaBtn;
    @FXML
    private ImageView vista1, vista2, vista3;
    Model model = new Model();
    private static String idPreventivo = "";


    public void initialize() {
        listaPreventivi.getItems().addAll(model.vediPreventivi(String.valueOf(Stato.DA_VALUTARE)));

        listaPreventivi.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) { // Verifica se il nuovo valore selezionato non è nullo
                String[] utili = newValue.split("\n");
                String marca = "";
                String modello = "";
                String colore = "";
                String nomeUtente = "";
                for (String riga : utili) {
                    if (riga.startsWith("Marca")) {
                        marca = riga.split(":")[1].trim();
                    }
                    if (riga.startsWith("Modello")) {
                        modello = riga.split(":")[1].trim();
                    }
                    if (riga.startsWith("Colore")) {
                        colore = riga.split(":")[1].trim().toLowerCase();
                    }
                    if (riga.startsWith("Id Preventivo")) {
                        idPreventivo = riga.split(":")[1].trim();
                    }
                    if (riga.startsWith("Utente")) {
                        nomeUtente = riga.split(":")[1].trim();
                    }
                }



                String path1, path2, path3;
                path1 = model.getImmagineAuto(Marca.valueOf(marca), modello, colore, 1, 1,nomeUtente);
                path2 = model.getImmagineAuto(Marca.valueOf(marca), modello, colore, 2, 1,nomeUtente);
                path3 = model.getImmagineAuto(Marca.valueOf(marca), modello, colore, 3, 1,nomeUtente);


                InputStream imageStream = getClass().getResourceAsStream(path1);

                if (imageStream != null) {
                    Image image = new Image(imageStream);
                    vista1.setImage(image);
                }
                imageStream = getClass().getResourceAsStream(path2);

                if (imageStream != null) {
                    Image image = new Image(imageStream);
                    vista2.setImage(image);
                }
                imageStream = getClass().getResourceAsStream(path3);

                if (imageStream != null) {
                    Image image = new Image(imageStream);
                    vista3.setImage(image);
                }
            }
        });
        model.numeric(prezzo);
        prezzo.textProperty().addListener((observable, oldValue, newValue) -> {
            valutaBtn.setDisable(newValue.trim().isEmpty());
        });
    }

    public void Valuta(ActionEvent actionEvent) {
        try {
            model.aggiungiValutazione(idPreventivo, Integer.parseInt(prezzo.getText()));
            listaPreventivi.getItems().clear();
            listaPreventivi.getItems().addAll(model.vediPreventivi(String.valueOf(Stato.DA_VALUTARE)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void Cancella(ActionEvent event) throws IOException {
        model.cancella(idPreventivo);
        listaPreventivi.getItems().clear();
        listaPreventivi.getItems().addAll(model.vediPreventivi(String.valueOf(Stato.DA_VALUTARE)));
    }

    public void backBtn(ActionEvent event) throws IOException {
        model.OpenCloseFXML("FXML/Dipendente.fxml", event);
    }
}
