package com.example.elaborato_ing;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SegreteriaController {

    @FXML
    private ComboBox<Marca> marca;
    @FXML
    private ComboBox<String> modello, allOptionals;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox vBox;
    @FXML
    private TextField nomeOptional;
    @FXML
    private ListView preventiviListView;


    Model model = new Model();
    private List<Preventivo> preventivi = new ArrayList<>();

    public void initialize() {
        caricaPreventivi();
        setMarche(marca);
        marca.setOnAction(_ -> aggiornaModello());
        modello.setOnAction(_ -> aggiornaCheckbox());
        allOptionals.getItems().setAll(model.caricaOptionalDaFile());
        modello.setDisable(true);
    }

    private void setMarche(ComboBox<Marca> marca) {
        marca.getItems().clear();
        List<Marca> marche = new ArrayList<>();
        for(Auto x : model.getCatalogo().getListaAuto()){
            if(!marche.contains(x.getMarca())) {
                marche.add(x.getMarca());
            }
        }
        marca.getItems().setAll(marche);
    }

    private void caricaPreventivi() {
        try {
            File file = new File("src/main/resources/com/example/elaborato_ing/TXT/Preventivi.txt");
            Scanner scanner = new Scanner(file);

            preventivi.clear(); // Pulisce la lista preventivi prima di caricare i nuovi dati

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] elements = line.split(",");
                if (elements.length >= 15 && !elements[14].equals("ritiro auto da definire")) {
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
                    double consumi = Double.parseDouble(motoreElements[4]);
                    List<Optionals> optionalScelti = new ArrayList<>();
                    List<String> op = List.of(elements[10].trim().split(";"));
                    for (String o : op) {
                        Optionals nuovoOp = new Optionals(o);
                        optionalScelti.add(nuovoOp);
                    }
                    Sede sede = Sede.valueOf(elements[11]);
                    List<String> colore = new ArrayList<>();
                    colore.add(elements[12]);

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date dataCreazione = sdf.parse(elements[13]);
                    Date dataScadenza = sdf.parse(elements[14]);
                    int prezzo = Integer.parseInt(elements[15]);

                    // Creare oggetto Cliente
                    Cliente cliente = new Cliente(clienteEmail);

                    // Creare oggetto Auto
                    Motore motore = new Motore(tipoMotore, alimentazione, cilindrata, potenza, consumi);
                    AutoNuova auto = new AutoNuova(marca, modelloAuto, altezzaAuto, lunghezzaAuto, larghezzaAuto, pesoAuto, volumeBagagliaioAuto, motore, prezzo, colore, null, null);
                    auto.setOptionalScelti(optionalScelti);

                    // Ottenere la data odierna
                    Date dataOdierna = Calendar.getInstance().getTime();

                    // Creare e aggiungere il preventivo alla lista solo se la data di scadenza è maggiore della data odierna
                    if (dataScadenza.after(dataOdierna)) {
                        Preventivo preventivo = new Preventivo(id, dataCreazione, dataScadenza, cliente, auto, sede);
                        preventivi.add(preventivo);
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void aggiornaModello() {
        model.aggiornaFileCatalogo();
        model.caricaDaFile("src/main/resources/com/example/elaborato_ing/TXT/Catalogo.txt", model.getCatalogo());
        List<AutoNuova> listaAuto = model.getMapAutoNuova().getOrDefault(marca.getValue(), Collections.emptyList());
        List<String> listaModelli = listaAuto.stream().map(Auto::getModello).distinct().toList();

        modello.getItems().clear();
        modello.getItems().setAll(listaModelli);
        modello.setDisable(false);
    }

    private void aggiornaCheckbox() {
        AutoNuova auto = model.getMarcaModelloAutoNuova(marca.getValue(), modello.getValue(), model.getMapAutoNuova());
        model.generaCheckBoxOptionalAmministrazione(auto, scrollPane, vBox, auto.getOptionalSelezionabili(), null);
    }

    public void xcliente() {
        preventivi.sort(Comparator.comparing(p -> p.getCliente().getEmail()));

        // Aggiorna la ListView con la lista di preventivi ordinata
        preventiviListView.setItems(FXCollections.observableArrayList(preventivi));
    }

    public void xmarca() {
        preventivi.sort(Comparator.comparing(p -> p.getAuto().getMarca().toString()));

        // Aggiorna la ListView con la lista di preventivi ordinata
        preventiviListView.setItems(FXCollections.observableArrayList(preventivi));
    }

    public void xsede() {
        preventivi.sort(Comparator.comparing(p -> p.getSede().toString()));

        // Aggiorna la ListView con la lista di preventivi ordinata
        preventiviListView.setItems(FXCollections.observableArrayList(preventivi));
    }

    public void aggiungiAuto(ActionEvent event) {
        model.aggiornaFileCatalogo();
        model.caricaDaFile("src/main/resources/com/example/elaborato_ing/TXT/Catalogo.txt", model.getCatalogo());
        model.openFXML("FXML/AggiungiAuto.fxml", event);
    }

    public void aggiungiOptional() {
        if (nomeOptional.getText() != null && !nomeOptional.getText().isEmpty()) {
            model.aggiungiOptionalDaFile(nomeOptional.getText());
            allOptionals.getItems().setAll(model.caricaOptionalDaFile());
            aggiornaCheckbox();
        }
        nomeOptional.clear();
    }


    public void rimuoviOptional() {
        if (allOptionals.getValue() != null) {
            model.rimuoviOptionalDaFile(allOptionals.getValue());
            allOptionals.getItems().setAll(model.caricaOptionalDaFile());
            model.eliminaOptionalTolti();
            model.aggiornaFileCatalogo();
            if (marca.getValue() != null)
                aggiornaCheckbox();
        }
        allOptionals.setValue(null);
    }

    public void modificaAuto(ActionEvent event) {
        model.openFXML("FXML/ModificaAuto.fxml", event);
    }

    public void eliminaAuto(ActionEvent event) {
        model.openFXML("FXML/EliminaAuto.fxml", event);
    }
}