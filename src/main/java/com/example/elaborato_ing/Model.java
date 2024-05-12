package com.example.elaborato_ing;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.List;

public class Model {

    private static Cliente cliente = new Cliente();
    private static Dipendente dipendente = new Dipendente();

    private static Map<Marca, List<AutoNuova>> map;

    public  Cliente getCliente() {
        return cliente;
    }

    public  Dipendente getDipendente() {
        return dipendente;
    }

    public  Map<Marca, List<AutoNuova>> getMap() {
        return map;
    }

    public Model() {
    }

    public void caricaDaFile(String file, Catalogo catalogo) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 16) {
                    Marca marca = Marca.valueOf(parts[0].trim());
                    String modello = parts[1].trim();
                    double lunghezza = Double.parseDouble(parts[2]);
                    double altezza = Double.parseDouble(parts[3]);
                    double larghezza = Double.parseDouble(parts[4]);
                    double peso = Double.parseDouble(parts[5]);
                    double volumeBagagliaio = Double.parseDouble(parts[6]);
                    String nomeMotore = parts[7];
                    Alimentazione alimentazione = Alimentazione.valueOf(parts[8].trim());
                    int cilindrata = Integer.parseInt(parts[9].trim());
                    int potenza = Integer.parseInt(parts[10].trim());
                    double consumi = Double.parseDouble(parts[11]);
                    Motore motore = new Motore(nomeMotore, alimentazione, cilindrata, potenza, consumi);
                    int prezzo = Integer.parseInt(parts[12]);
                    String sconto = parts[13];
                    List<String> colori = List.of(parts[14].trim().split(";"));
                    List<String> listaOp = List.of(parts[15].trim().split(";"));
                    AutoNuova auto = new AutoNuova(marca, modello, altezza, lunghezza, larghezza, peso, volumeBagagliaio, motore, prezzo, colori, sconto, listaOp);
                    auto.caricaImmagini();
                    catalogo.add(auto);
                    map.computeIfAbsent(marca, k -> new ArrayList<>()).add(auto);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File non trovato: " + file);
        } catch (IOException e) {
            System.err.println("Errore nella lettura del file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Errore nei dati: " + e.getMessage());
        }
    }

    public void openFXML(String fxmlPath) {

        try {
            // Carica il file FXML specificato
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Crea una nuova finestra per la nuova form
            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL); // Imposta la finestra come modale (blocca l'interazione con altre finestre)

            // Imposta il contenuto della nuova form
            Scene scene = new Scene(root);
            newStage.setScene(scene);

            // Mostra la nuova finestra
            newStage.showAndWait(); // Attendere che la finestra venga chiusa prima di tornare al chiamante

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void OpenCloseFXML(String fxmlPath, Node oggetto) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene loginScene = new Scene(loader.load());
            Stage loginStage = new Stage();
            loginStage.initModality(Modality.APPLICATION_MODAL);
            loginStage.setScene(loginScene);
            loginStage.show();

            ((Stage) oggetto.getScene().getWindow()).close(); // Chiude la scena iniziale

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //LOGIN

    public int autenticato(String username, String password) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/com/example/elaborato_ing/TXT/LoginFile.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parti = line.split(",");
                if (parti.length == 4 && parti[0].equals(username) && parti[3].equals(password)) {
                    cliente.setEmail(parti[0]);
                    cliente.setNome(parti[1]);
                    cliente.setCognome(parti[2]);
                    cliente.setPassword(parti[3]);
                    return 0;
                } else if (parti.length == 5 && parti[0].equals(username) && parti[3].equals(password)) {
                    dipendente.setEmail(parti[0]);
                    dipendente.setNome(parti[1]);
                    dipendente.setCognome(parti[2]);
                    dipendente.setPassword(parti[3]);
                    dipendente.setIdDipendente(Integer.parseInt(String.valueOf(parti[4])));
                    return 1;
                }
            }
        }
        return -1;
    }

    public String getClienteLoggato() {
        return cliente.getEmail();
    }

    public void eliminaCliente() {
        cliente = new Cliente();
    }


    //REGISTRAZIONE

    public void Registrazione(String email, String nome, String cognome, String password) {


        if (!email.isEmpty() && !nome.isEmpty() && !cognome.isEmpty() && !password.isEmpty()) {
            // Apertura del file in modalità append
            try (FileWriter writer = new FileWriter("src/main/resources/com/example/elaborato_ing/TXT/LoginFile.txt", true)) {

                // controllo se l'utente è già inserito nel file login, si controlla solo l'email, quella è la chiave e deve essere unica
                if (utenteEsiste(email)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Attenzione");
                    alert.setHeaderText("L'utente sembra già essere registrato");
                    alert.setContentText("Questa email esiste già");
                    alert.showAndWait();
                } else {
                    // Scrivi i dati dell'utente nel file, separati da virgole
                    writer.write(email + "," + nome + "," + cognome + "," + password + "\n");

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Successo");
                    alert.setHeaderText("Registrazione avvenuta con successo");
                    alert.setContentText("Messaggio dettagliato sull'errore.");

                    // Gestione dell'azione del bottone OK
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            openFXML("FXML/Login.fxml");
                        }
                    });

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Devi completare tutti i campi prima di registrarti");
            alert.setContentText("Messaggio dettagliato sull'errore.");

            // Aggiunta di un pulsante "OK" al box di errore
            alert.getButtonTypes().setAll(ButtonType.OK);

            // Visualizzazione del box di errore
            alert.showAndWait();
        }
    }

    // se trovo l'email allora return TRUE se no FALSE
    private boolean utenteEsiste(String email) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/com/example/elaborato_ing/TXT/LoginFile.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parti = line.split(",");
                if (parti[0].equals(email)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void PDF() {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Imposta il font e il colore del testo
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 20);
            contentStream.setNonStrokingColor(Color.BLUE);

            // Scrivi il titolo
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Preventivo per l'auto:");
            contentStream.endText();

            // Imposta il font e il colore del testo per le informazioni
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 14);
            contentStream.setNonStrokingColor(Color.BLACK);

            // Disegna una linea
            contentStream.moveTo(100, 680);
            contentStream.lineTo(500, 680);
            contentStream.stroke();

            contentStream.close();

            // Salva il documento
            document.save("src/main/resources/com/example/elaborato_ing/Preventivo.pdf");
            document.close();

            System.out.println("PDF generato con successo.");
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    public void inoltraPreventivo(Auto auto, String colore, int Prezzo, Sede sede) throws IOException {
        LocalDateTime OrarioCreazione = LocalDateTime.now();
        LocalDate inizio = LocalDate.now();
        int giorni = 0;
        for (OP _ : auto.getOptional()) {
            giorni += 10;
        }
        LocalDate fine = inizio.plusMonths(1);
        fine = fine.plusDays(giorni);
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date dataCreazione = Date.from(inizio.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dataFine = Date.from(fine.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Preventivo preventivo = new Preventivo(String.valueOf(auto.hashCode() * OrarioCreazione.hashCode()), dataCreazione, dataFine, cliente, auto, sede);

        // esporto il preventivo sul filesrc
        try (FileWriter writer = new FileWriter("src/main/resources/com/example/elaborato_ing/TXT/Preventivi.txt", true)) {
            writer.write(preventivo + "," + colore + "," + formato.format(dataCreazione) + "," + formato.format(dataFine) + "," + Prezzo + ",DA PAGARE" + "\n");
        }
    }

    public AutoNuova getMarcaModello(Marca marca, String modello, Map<Marca, List<AutoNuova>> map) {
        List<AutoNuova> autoList = map.get(marca);

        if (autoList == null) { // Se non esiste una lista per la marca data
            System.out.println("Marca non trovata: " + marca);
            return null;
        }

        for (AutoNuova auto : autoList) {
            if (auto.getModello().equals(modello)) { // Cerca il modello
                return auto; // Se il modello corrisponde, restituisce l'auto
            }
        }
        return null;
    }

    public List<String> inizializzaPreventivo() {
        List<String> filteredLines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/com/example/elaborato_ing/TXT/Preventivi.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0 && parts[1].equals(cliente.getEmail())) {
                    String linea = "Id Preventivo : " + parts[0] +
                            "\nMarca : " + parts[2] +
                            "\nModello : " + parts[3] +
                            "\nAltezza : " + parts[4] + " cm" +
                            "\nLunghezza : " + parts[5] + " cm" +
                            "\nLarghezza : " + parts[6] + " cm" +
                            "\nPeso : " + parts[7] + " kg" +
                            "\nVolume Bagagliaio : " + parts[8] + " L" +
                            "\nNome motore : " + parts[9].split(";")[0] +
                            "\nAlimentazione : " + parts[9].split(";")[1] +
                            "\nCilindrata : " + parts[9].split(";")[2] + " m³" +
                            "\nPotenza : " + parts[9].split(";")[3] + " kW" +
                            "\nConsumi : " + parts[9].split(";")[4] + " km/L" +
                            "\nOptional : " + parts[10] +
                            "\nColore : " + parts[11] +
                            "\nData Inizio Preventivo : " + parts[12] +
                            "\nData Fine Preventivo : " + parts[13] +
                            "\nPrezzo : " + parts[14] + " €" +
                            "\nStato Preventivo : " + parts[15];
                    filteredLines.add(linea);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filteredLines;
    }

    public static void aggiungiPagamento(String idPreventivo, String statoPreventivo) throws IOException {
        BufferedReader reader;
        BufferedWriter writer;

        try {
            reader = new BufferedReader(new FileReader("src/main/resources/com/example/elaborato_ing/TXT/Preventivi.txt"));
            StringBuilder fileContent = new StringBuilder();
            String riga;

            while ((riga = reader.readLine()) != null) {
                String[] campi = riga.split(",");

                if (campi[0].equals(idPreventivo)) {
                    if (campi.length >= 16 && campi[15].equals("DA PAGARE")) {
                        campi[15] = "PAGATO";
                    }
                }
                fileContent.append(String.join(",", campi)).append("\n");
            }
            reader.close();

            writer = new BufferedWriter(new FileWriter("src/main/resources/com/example/elaborato_ing/TXT/Preventivi.txt"));
            writer.write(fileContent.toString());
            writer.close();
            System.out.println("Sostituzione completata.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void valuta(String text) {

    }

    public void avvisa() {

    }

    public void aggiornaImmagine(String nuovo) {

    }

    public void setMarca(ComboBox<Marca> marca) {
        marca.getItems().addAll(getMap().keySet());
    }
}



