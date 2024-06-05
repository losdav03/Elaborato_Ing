package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;


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
    @FXML
    private DatePicker dataRitiro;

    public void initialize() {
        listaPreventivi.getItems().addAll(model.vediPreventivi(String.valueOf(Stato.DA_VALUTARE)));
        model.caricaMappaAutoUsate();

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
                model.setImageViewPreventivi(idPreventivo, vista1, 1);
                model.setImageViewPreventivi(idPreventivo, vista2, 2);
                model.setImageViewPreventivi(idPreventivo, vista3, 3);
            }
        });
        model.numeric(prezzo);
        prezzo.textProperty().addListener((observable, oldValue, newValue) -> {
            valutaBtn.setDisable(newValue.trim().isEmpty() || dataRitiro.getValue() == null);
        });

        dataRitiro.getEditor().setDisable(true);
        dataRitiro.setDayCellFactory(new Callback<>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(LocalDate.now().plusDays(1))) {
                            setDisable(true);
                        }
                    }
                };
            }
        });

        dataRitiro.valueProperty().addListener((observable, oldValue, newValue) -> {
            dataRitiro.getEditor().setOnMouseClicked(event -> dataRitiro.show());
            valutaBtn.setDisable(newValue == null || prezzo.getText().trim().isEmpty());
        });
    }

    public void Valuta(ActionEvent actionEvent) {
        try {
            LocalDate ritiroData = dataRitiro.getValue();
            if (ritiroData == null) {
                // Mostra un messaggio di errore se la data non è selezionata
                System.out.println("Data di ritiro non selezionata");
                return;
            }
            model.aggiungiValutazione(idPreventivo, Integer.parseInt(prezzo.getText()), ritiroData);
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
        vista1.setImage(null);
        vista2.setImage(null);
        vista3.setImage(null);
    }

    public void backBtn(ActionEvent event) throws IOException {
        model.openCloseFXML("FXML/Dipendente.fxml", event);
    }
}
