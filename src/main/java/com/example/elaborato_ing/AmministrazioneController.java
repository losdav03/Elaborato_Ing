package com.example.elaborato_ing;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AmministrazioneController {

    @FXML private ComboBox<Marca> marca;
    @FXML private ComboBox<String> modello;
    @FXML private CheckBox infot, sensori, fari, sedili, scorta, vetri, interni, ruote, cruise;
    @FXML private Button modificaOption,visualizzaPreventivi,visualizzaMarca,visualizzaSede,aggiungi;
    @FXML private ListView preventiviListView;
    Model model = new Model();
    private List<Preventivo> preventivi = new ArrayList<>();

    public void initialize() {
        caricaPreventivi();
        model.setMarca(marca);
        marca.setOnAction(_ -> aggiornaModello());
    }

    private void caricaPreventivi() {
        try {
            File file = new File("com/example/elaborato_ing/TXT/Preventivi.txt");
            Scanner scanner = new Scanner(file);

            preventivi.clear(); // Pulisce la lista preventivi prima di caricare i nuovi dati

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] elements = line.split(",");

                // Estrai i singoli elementi
                String id = elements[0];
                String clienteEmail = elements[1];
                Marca marca =  Marca.valueOf(elements[2].trim());
                String modelloAuto = elements[3];
                double altezzaAuto = Double.parseDouble(elements[4]);
                double lunghezzaAuto = Double.parseDouble(elements[5]);
                double larghezzaAuto = Double.parseDouble(elements[6]);
                double pesoAuto = Double.parseDouble(elements[7]);
                double volumeBagagliaioAuto = Double.parseDouble(elements[8]);
                String motoreAuto = elements[9];
                Alimentazione alimentazione = Alimentazione.valueOf(elements[8].trim());
                int cilindrata = Integer.parseInt(elements[11]);
                int potenza = Integer.parseInt(elements[12]);
                int consumi = Integer.parseInt(elements[13]);
                String optionals = elements[14];
                Sede sede =  Sede.valueOf(elements[15].trim());
                String colore = elements[16];
                String creazione = elements[17];
                String fine = elements[18];
                int prezzo = Integer.parseInt(elements[19]);
                String daPagare = elements[20];
                // Creare oggetto Cliente
                Cliente cliente = new Cliente(clienteEmail);
                // Creare oggetto Auto
                Motore motore = new Motore(motoreAuto,alimentazione,cilindrata, potenza, consumi); // Assumendo che il motore sia una classe
                Auto auto = new Auto(marca, modelloAuto, altezzaAuto, lunghezzaAuto, larghezzaAuto, pesoAuto, volumeBagagliaioAuto, motore);

                // Parsing delle date
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date dataCreazione = sdf.parse(elements[16]);
                Date dataScadenza = sdf.parse(elements[17]);

                // Creare e restituire l'oggetto Preventivo
                Preventivo preventivo = new Preventivo(id, dataCreazione, dataScadenza, cliente, auto, sede);
                // Aggiungi il preventivo alla lista preventivi
                preventivi.add(preventivo);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void aggiornaModello() {
        modello.getItems().clear();
        List<AutoNuova> listaAuto = model.getMap().getOrDefault(marca.getValue(), Collections.emptyList());
        List<String> listaModelli = listaAuto.stream().map(Auto::getModello).distinct().toList();

        if (listaModelli.isEmpty()) {
            modello.getItems().clear();
            modello.setDisable(true);
        } else {
            modello.getItems().setAll(listaModelli);
            modello.setDisable(false);
        }
    }

    public void xcliente(ActionEvent actionEvent) {
        Collections.sort(preventivi, (p1, p2) -> p1.getCliente().getNome().compareTo(p2.getCliente().getNome()));

        // Aggiorna la ListView con la lista di preventivi ordinata
        preventiviListView.setItems(FXCollections.observableArrayList(preventivi));
    }

    public void xmarca(ActionEvent actionEvent) {
        Collections.sort(preventivi, (p1, p2) -> p1.getAuto().getMarca().compareTo(p2.getAuto().getMarca()));

        // Aggiorna la ListView con la lista di preventivi ordinata
        preventiviListView.setItems(FXCollections.observableArrayList(preventivi));
    }

    public void xsede(ActionEvent actionEvent) {
        Collections.sort(preventivi, (p1, p2) -> p1.getSede().compareTo(p2.getSede()));

        // Aggiorna la ListView con la lista di preventivi ordinata
        preventiviListView.setItems(FXCollections.observableArrayList(preventivi));
    }
    public void  modificaOptionals(ActionEvent actionEvent) {
        Marca marcaSelezionata = marca.getValue();
    }

    public void aggiungiAuto(ActionEvent actionEvent) {
        model.OpenCloseFXML("FXML/AggiungiAuto.fxml",aggiungi);
    }
}