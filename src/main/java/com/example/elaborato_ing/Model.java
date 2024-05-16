package com.example.elaborato_ing;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
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
    private static Amministrazione amministrazione = new Amministrazione();


    private static Map<Marca, List<AutoNuova>> map = new HashMap<>();
    private static Catalogo catalogo = new Catalogo();
    private static List<String> allOptionals = new ArrayList<>();

    public Model() {
    }

    public Map<Marca, List<AutoNuova>> getMap() {
        return map;
    }

    public Catalogo getCatalogo() {
        return catalogo;
    }


    public void aggiornaFileCatalogo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/com/example/elaborato_ing/TXT/Catalogo.txt"))) {
            for (AutoNuova auto : catalogo.getListaAuto()) {
                writer.write(auto.stampaAutoCatalogo()); // Supponendo che Auto abbia un metodo toString appropriato
                writer.newLine();
            }
            System.out.println("Catalogo aggiornato e salvato su " + "Catalogo.txt");
        } catch (IOException e) {
            System.err.println("Errore durante la scrittura del file: " + e.getMessage());
        }
    }

    public void caricaDaFile(String file, Catalogo catalogo) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 12) {
                    Marca marca = Marca.valueOf(parts[0].trim());
                    String modello = parts[1].trim();
                    double altezza = Double.parseDouble(parts[2]);
                    double lunghezza = Double.parseDouble(parts[3]);
                    double larghezza = Double.parseDouble(parts[4]);
                    double peso = Double.parseDouble(parts[5]);
                    double volumeBagagliaio = Double.parseDouble(parts[6]);
                    String nomeMotore = parts[7].split(";")[0];
                    Alimentazione alimentazione = Alimentazione.valueOf(parts[7].split(";")[1].trim());
                    int cilindrata = Integer.parseInt(parts[7].split(";")[2].trim());
                    int potenza = Integer.parseInt(parts[7].split(";")[3].trim());
                    double consumi = Double.parseDouble(parts[7].split(";")[4]);
                    Motore motore = new Motore(nomeMotore, alimentazione, cilindrata, potenza, consumi);
                    int prezzo = Integer.parseInt(parts[8]);
                    String sconto = parts[9];
                    List<String> colori = List.of(parts[10].trim().split(";"));

                    List<Optionals> optionalSelezionabili = new ArrayList<>();
                    List<String> op = List.of(parts[11].trim().split(":"));
                    for (String o : op) {
                        Optionals nuovoOp = new Optionals(o.split(";")[0], Integer.parseInt(o.split(";")[1]));
                        optionalSelezionabili.add(nuovoOp);
                    }


                    AutoNuova auto = new AutoNuova(marca, modello, altezza, lunghezza, larghezza, peso, volumeBagagliaio, motore, prezzo, colori, sconto, optionalSelezionabili);
                    // auto.caricaImmagini();
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

    public void caricaOptionalDaFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/elaborato_ing/TXT/Optionals.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                allOptionals.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void generaCheckBoxOptionalConfiguratore(AutoNuova auto, ScrollPane scrollPane, VBox checkBoxContainer, List<Optionals> optionalScelti, Label costo) {
        {
            scrollPane.setContent(checkBoxContainer);
            checkBoxContainer.getChildren().clear();

            for (Optionals item : auto.getOptionalSelezionabili()) {
                CheckBox checkBox = new CheckBox(item.getNome());
                checkBox.setText(item.getNome() + " : +" + item.getCosto() + " €");
                checkBoxContainer.getChildren().add(checkBox);
                checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    Optionals op = new Optionals(item.getNome(), item.getCosto());
                    if (newValue) {
                        optionalScelti.add(op);
                        int prezzoAggiornato = Integer.parseInt(costo.getText()) + item.getCosto();
                        costo.setText(String.valueOf(prezzoAggiornato));

                    } else {
                        optionalScelti.remove(op);
                        int prezzoAggiornato = Integer.parseInt(costo.getText()) - item.getCosto();
                        costo.setText(String.valueOf(prezzoAggiornato));

                    }
                });
            }
        }

    }


    public void generaCheckBoxOptionalAmministrazione(AutoNuova auto, ScrollPane scrollPane, VBox checkBoxContainer, List<Optionals> optionalScelti, Label costo) {
        scrollPane.setContent(checkBoxContainer);
        checkBoxContainer.getChildren().clear();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/elaborato_ing/TXT/Optionals.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String nomeOptional = line;
                int costoOptional = auto.getOptionalSelezionabili().stream().filter(op -> op.getNome().equals(nomeOptional)).findFirst().map(Optionals::getCosto).orElse(0);

                // Controllo se l'auto ha l'optional e in tal caso prendo il costo dall'auto

                CheckBox checkBox = new CheckBox(nomeOptional);
                checkBox.setText(nomeOptional + " : +" + costoOptional + " €");
                checkBoxContainer.getChildren().add(checkBox);
                if (costoOptional != 0) {
                    checkBox.setSelected(true);
                }

                checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    Optionals op = new Optionals(nomeOptional, costoOptional);
                    if (newValue) {
                        TextInputDialog dialog = new TextInputDialog();
                        dialog.setHeaderText("Inserisci il prezzo per " + nomeOptional);
                        Optional<String> result = dialog.showAndWait();
                        result.ifPresent(prezzo -> {
                            // Aggiungo l'optional alla lista degli optional dell'auto con il prezzo inserito
                            int prezzoOptional = Integer.parseInt(prezzo);
                            auto.getOptionalSelezionabili().add(new Optionals(nomeOptional, prezzoOptional));
                            checkBox.setText(nomeOptional + " : +" + prezzoOptional + " €");
                            System.out.println(auto.stampaSelezionabili());
                        });
                    } else {
                        // Rimuovo l'optional dalla lista degli optional dell'auto se deselezionato
                        auto.getOptionalSelezionabili().removeIf(optionals -> optionals.getNome().equals(nomeOptional));
                        checkBox.setText(nomeOptional + " : +" + 0 + " €");
                        System.out.println("Rimosso");
                        System.out.println(auto.stampaSelezionabili());

                    }
                });
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public String getImmagineAuto(Marca marca, String modello, String colore, int vista) {
        return getMarcaModello(marca, modello, map).getImmagine(colore.toLowerCase(), vista);
    }


    public void openFXML(String fxmlPath) {

        try {
            // Carica il file FXML specificato
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();


            // Crea una nuova finestra per lanuova form
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
        if (username.equals("amm") && password.equals("amm")) {
            amministrazione.setEmail("amm");
            amministrazione.setNome("amm");
            amministrazione.setCognome("amm");
            amministrazione.setPassword("amm");
            return 2;
        } else {
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

    public void inoltraPreventivo(AutoNuova auto, String colore, int Prezzo, Sede sede) throws IOException {
        LocalDateTime OrarioCreazione = LocalDateTime.now();
        LocalDate inizio = LocalDate.now();
        int giorni = 0;
        for (Optionals _ : auto.getOptionalScelti()) {
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
                if (parts.length > 16 && parts[1].equals(cliente.getEmail())) {
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
                            "\nOptional : " + (parts[10].isEmpty() ? " nessun optional" : parts[10]) +
                            "\nSede : " + parts[11] +
                            "\nColore : " + parts[12] +
                            "\nData Inizio Preventivo : " + parts[13] +
                            "\nData Fine Preventivo : " + parts[14] +
                            "\nPrezzo : " + parts[15] + " €" +
                            "\nStato Preventivo : " + parts[16];
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
                    if (campi.length >= 17 && campi[16].equals(Stato.DA_PAGARE.toString())) {
                        campi[16] = Stato.PAGATA.toString();
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

    public void caricaOpzionalDaFile(String filePath, List<Optionals> listaOp, VBox checkBoxContainer) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            CheckBox checkBox = new CheckBox(line);
            String finalLine = line;
            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    // Show TextInputDialog to get the value
                    TextInputDialog dialog = new TextInputDialog("0");
                    dialog.setTitle("Input Value");
                    dialog.setHeaderText("Enter the value for " + finalLine);
                    dialog.setContentText("Value:");

                    Optional<String> result = dialog.showAndWait();
                    result.ifPresent(value -> {
                        try {
                            int doubleValue = Integer.parseInt(value);
                            // Create new Optional object and add to list
                            Optionals optional = new Optionals(finalLine, doubleValue);
                            listaOp.add(optional);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    // Remove Optional from list and reset price
                    listaOp.removeIf(optional -> optional.getNome().equals(finalLine));
                    // Reset price to 0 if no optional selected
                    // In this example, "prezzo" refers to the TextField where price is entered

                }
            });
            checkBoxContainer.getChildren().add(checkBox);
        }
        reader.close();
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


    public void inoltraPreventivoUsato(AutoUsata auto, String lowerCase, int i, Object o) {

    }
}



