package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;

public class RiepilogoController {
    @FXML
    private ListView<String> listView;
    private Model model = new Model();

    public void InizializzaPreventivi(){
        model.inizializzaPreventivo(listView);
    }
    public void paga(ActionEvent actionEvent) {
        String preventivoSelezionato = listView.getSelectionModel().getSelectedItem();
        if (preventivoSelezionato != null) {
            // Aggiungi "pagato" alla fine della riga selezionata
            aggiungiPagamento(preventivoSelezionato);
            // Aggiorna la ListView
            model.inizializzaPreventivo(listView);
        }
    }

    private void aggiungiPagamento(String preventivoSelezionato) {
        try {
            // Leggi il file
            File file = new File("Preventivi.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
            reader.close();

            // Modifica la riga selezionata
            String content = sb.toString();
            content = content.replaceAll(preventivoSelezionato, preventivoSelezionato + ",pagato");

            // Scrivi il file aggiornato
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
