package com.example.elaborato_ing;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SegreteriaController {

    @FXML
    private ComboBox<Marca> marca;
    @FXML
    private ComboBox<String> modello;
    @FXML
    private Button chiudi, modificaOption, visualizzaPreventivi, visualizzaMarca, visualizzaSede, aggiungiAuto, aggiungiOptionals;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox vBox;
    @FXML
    private ListView preventiviListView;


    Model model = new Model();
    private List<Preventivo> preventivi = new ArrayList<>();

    public void initialize() {
        caricaPreventivi();
        model.setMarca(marca);
        marca.setOnAction(_ -> aggiornaModello());
        modello.setOnAction(_ -> aggiornaCheckbox());

    }

    @FXML
    public void handleCloseRequest(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma chiusura");
        alert.setHeaderText("Sei sicuro di voler chiudere la finestra?");
        alert.setContentText("La finestra verrà chiusa e tutte le modifiche non salvate potrebbero essere perse.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Se l'utente conferma la chiusura, chiudi la finestra
            Stage stage = (Stage) chiudi.getScene().getWindow();
            stage.close();
        }
    }

    private void caricaPreventivi() {
        try {
            File file = new File("src/main/resources/com/example/elaborato_ing/TXT/Preventivi.txt");
            Scanner scanner = new Scanner(file);

            preventivi.clear(); // Pulisce la lista preventivi prima di caricare i nuovi dati

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] elements = line.split(",");
                if (elements.length >= 15) {
                    // Estrai i singoli elementi
                    String id = elements[0];
                    String clienteEmail = elements[1];
                    Marca marca = Marca.valueOf(elements[2].trim());
                    String modelloAuto = elements[3];
                    double altezzaAuto = Double.parseDouble(elements[4]);
                    double lunghezzaAuto = Double.parseDouble(elements[5]);
                    double larghezzaAuto = Double.parseDouble(elements[6]);
                    double pesoAuto = Double.parseDouble(elements[7]);
                    double volumeBagagliaioAuto = Double.parseDouble(elements[8]);
                    String[] motoreElements = elements[9].split(";"); // Suddividi il campo motore
                    String tipoMotore = motoreElements[0];
                    Alimentazione alimentazione = Alimentazione.valueOf(motoreElements[1]);
                    int cilindrata = Integer.parseInt(motoreElements[2]);
                    int potenza = Integer.parseInt(motoreElements[3]);
                    Double consumi = Double.parseDouble(motoreElements[4]);
                    List<Optionals> optionalScelti = new ArrayList<>();
                    List<String> op = List.of(elements[10].trim().split(";"));
                    for (String o : op) {
                        Optionals nuovoOp = new Optionals(o);
                        optionalScelti.add(nuovoOp);
                    }
                    Sede sede = Sede.valueOf(elements[11]);
                    List<String> colore = new ArrayList<>();
                    colore.add(elements[12]);
                    String creazione = elements[13];
                    String fine = elements[14];
                    int prezzo = Integer.parseInt(elements[15]);
                    Stato StatoPreventivo = Stato.valueOf(elements[16]);
                    // Creare oggetto Cliente
                    Cliente cliente = new Cliente(clienteEmail);
                    // Creare oggetto Auto
                    Motore motore = new Motore(tipoMotore, alimentazione, cilindrata, potenza, consumi);
                    //Marca marca, String modello, double altezza, double lunghezza, double larghezza, double peso, double volumeBagagliaio, Motore motore, int prezzo, List<String> colori, String sconto, List<Optionals> optionalSelezionabili
                    AutoNuova auto = new AutoNuova(marca, modelloAuto, altezzaAuto, lunghezzaAuto, larghezzaAuto, pesoAuto, volumeBagagliaioAuto, motore, prezzo, colore, null, null);
                    auto.setOptionalScelti(optionalScelti);
                    // Parsing delle date
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date dataCreazione = sdf.parse(elements[13]);
                    Date dataScadenza = sdf.parse(elements[14]);
                    // Creare e restituire l'oggetto Preventivo
                    Preventivo preventivo = new Preventivo(id, dataCreazione, dataScadenza, cliente, auto, sede);
                    // Aggiungi il preventivo alla lista preventivi
                    preventivi.add(preventivo);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void aggiornaModello() {
        List<AutoNuova> listaAuto = model.getMap().getOrDefault(marca.getValue(), Collections.emptyList());
        List<String> listaModelli = listaAuto.stream().map(Auto::getModello).distinct().toList();

        modello.getItems().clear();
        modello.getItems().setAll(listaModelli);
        modello.setDisable(false);
    }

    private void aggiornaCheckbox() {
        AutoNuova auto = model.getMarcaModello(marca.getValue(), modello.getValue(), model.getMap());
        model.generaCheckBoxOptional(auto, scrollPane, vBox, auto.getOptionalSelezionabili(), null);
    }


    public void xcliente(ActionEvent actionEvent) {
        preventivi.sort(Comparator.comparing(p -> p.getCliente().getEmail()));

        // Aggiorna la ListView con la lista di preventivi ordinata
        preventiviListView.setItems(FXCollections.observableArrayList(preventivi));
    }

    public void xmarca(ActionEvent actionEvent) {
        preventivi.sort(Comparator.comparing(p -> p.getAuto().getMarca()));

        // Aggiorna la ListView con la lista di preventivi ordinata
        preventiviListView.setItems(FXCollections.observableArrayList(preventivi));
    }

    public void xsede(ActionEvent actionEvent) {
        preventivi.sort(Comparator.comparing(p -> p.getSede().toString()));

        // Aggiorna la ListView con la lista di preventivi ordinata
        preventiviListView.setItems(FXCollections.observableArrayList(preventivi));
    }

    public void modificaOptionals(ActionEvent actionEvent) {
        Marca marcaSelezionata = marca.getValue();
    }

    public void aggiungiAuto(ActionEvent actionEvent) {
        model.OpenCloseFXML("FXML/AggiungiAuto.fxml", aggiungiAuto);
    }

    public void aggiungiOptionals(ActionEvent actionEvent) {
    }

    public void chiudiSegreteria(ActionEvent event) {
        handleCloseRequest(event);
        model.OpenCloseFXML("FXML/Configuratore.fxml", chiudi);
    }
}