package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class Model {

    private static Cliente cliente = new Cliente();
    private static Dipendente dipendente = new Dipendente();
    private static Amministrazione amministrazione = new Amministrazione();


    private static Map<Marca, List<AutoNuova>> map = new HashMap<>();
    private static Catalogo catalogo = new Catalogo();
    private List<String> allOptionals = new ArrayList<>();

    private Stage stage;
    private Scene scene;
    private Parent root;


    public Model() {
    }

    public Map<Marca, List<AutoNuova>> getMap() {
        return map;
    }

    public Catalogo getCatalogo() {
        return catalogo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Dipendente getDipendente() {
        return dipendente;
    }

    public Amministrazione getAmministrazione() {
        return amministrazione;
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
                        // controllo se dentro al catalogo ci sono optional che sono stati rimossi precedentemente dall'amministrazione
                        Optionals nuovoOp = new Optionals(o.split(";")[0], Integer.parseInt(o.split(";")[1]));
                        optionalSelezionabili.add(nuovoOp);
                    }


                    AutoNuova auto = new AutoNuova(marca, modello, altezza, lunghezza, larghezza, peso, volumeBagagliaio, motore, prezzo, colori, sconto, optionalSelezionabili);
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

    public List<String> caricaOptionalDaFile() {
        allOptionals.clear();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/elaborato_ing/TXT/Optionals.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                allOptionals.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return allOptionals;
    }

    public void aggiungiOptionalDaFile(String optional) {
        allOptionals.add(optional);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/com/example/elaborato_ing/TXT/Optionals.txt"))) {
            for (String op : allOptionals) {
                writer.write(op); // Supponendo che Auto abbia un metodo toString appropriato
                writer.newLine();
            }
            System.out.println("Optionals aggiornati e salvati su " + "Optionals.txt");
        } catch (IOException e) {
            System.err.println("Errore durante la scrittura del file: " + e.getMessage());
        }
    }

    public void rimuoviOptionalDaFile(String optional) {
        allOptionals.remove(optional);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/com/example/elaborato_ing/TXT/Optionals.txt"))) {
            for (String op : allOptionals) {
                writer.write(op); // Supponendo che Auto abbia un metodo toString appropriato
                writer.newLine();
            }
            System.out.println("Optionals aggiornati e salvati su " + "Optionals.txt");
        } catch (IOException e) {
            System.err.println("Errore durante la scrittura del file: " + e.getMessage());
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
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/elaborato_ing/TXT/Optionals.txt"))) {
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
                    if (newValue) {
                        TextInputDialog dialog = new TextInputDialog();
                        dialog.setHeaderText("Inserisci il prezzo per " + nomeOptional);
                        Optional<String> result = dialog.showAndWait();

                        if (result.isPresent()) {
                            String prezzoString = result.get();
                            if (prezzoString.matches("\\d+")) {
                                int prezzoOptional = Integer.parseInt(prezzoString);
                                auto.getOptionalSelezionabili().add(new Optionals(nomeOptional, prezzoOptional));
                                checkBox.setText(nomeOptional + " : +" + prezzoOptional + " €");
                                System.out.println(auto.stampaSelezionabili());
                                aggiornaFileCatalogo();
                            } else {
                                checkBox.setSelected(false);
                            }
                        } else {
                            checkBox.setSelected(false);
                        }
                    } else {
                        // Rimuovo l'optional dalla lista degli optional dell'auto se deselezionato
                        auto.getOptionalSelezionabili().removeIf(optionals -> optionals.getNome().equals(nomeOptional));
                        checkBox.setText(nomeOptional + " : +" + 0 + " €");
                        System.out.println("Rimosso");
                        System.out.println(auto.stampaSelezionabili());
                        aggiornaFileCatalogo();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void eliminaOptionalTolti() {

        for (AutoNuova auto : catalogo.getListaAuto()) {
            List<Optionals> optionalSelezionabili = auto.getOptionalSelezionabili();
            Iterator<Optionals> iterator = optionalSelezionabili.iterator();

            while (iterator.hasNext()) {
                Optionals optional = iterator.next();
                if (!allOptionals.contains(optional.getNome())) {
                    iterator.remove();
                }
            }
        }
    }


    public String getImmagineAuto(Marca marca, String modello, String colore, int vista) {
        return getMarcaModello(marca, modello, map).getImmagine(colore.toLowerCase(), vista);
    }

    public void caricaImmaginiImageView(ImageView imageView1, ImageView imageView2, ImageView imageView3) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleziona un'immagine");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Immagini", "*.png"));

        File immagine = fileChooser.showOpenDialog(imageView1.getScene().getWindow());
        if (immagine != null) {
            Image image = new Image(immagine.toURI().toString());
            if (imageView1.getImage() == null) {
                imageView1.setImage(image);
            } else if (imageView2.getImage() == null) {
                imageView2.setImage(image);
            } else {
                imageView3.setImage(image);
            }
        }
    }

    public void salvaImageViewImage(ImageView imageView, int vista, Marca marca, String modello, String colore) throws IOException {
        if (imageView.getImage() != null) {
            String newFileName = marca.toString().toLowerCase().trim() + modello.trim().toLowerCase() + colore.trim().toLowerCase() + vista + ".png";

            // Percorso relativo della cartella delle immagini
            File outputDir = new File("src/main/resources/com/example/elaborato_ing/images");
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }
            File outputFile = new File(outputDir, newFileName);

            // Leggi il contenuto dell'immagine da ImageView
            String imageUrl = imageView.getImage().getUrl();
            File inputFile = new File(imageUrl.replace("file:", ""));
            if (inputFile.exists()) {
                // Copia il file immagine nella directory di output
                Files.copy(inputFile.toPath(), outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }


public void openFXML(String fxmlPath, ActionEvent event) {
    try {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}

public void OpenCloseFXML(String fxmlPath, ActionEvent event) throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
}

public void AccediPersonaFXML(String fxmlPath, ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(fxmlPath)));
    root = loader.load();
    LoginController loginController = loader.getController();
    loginController.setMainWindow((Stage) ((Node) event.getSource()).getScene().getWindow());
    Stage loginStage = new Stage();
    loginStage.initModality(Modality.APPLICATION_MODAL);
    loginStage.setScene(new Scene(root));
    loginStage.show();
}


//LOGIN

public void autenticato(String username, String password) throws IOException {
    if (username.equals("amm") && password.equals("amm")) {
        amministrazione.setEmail("amm");
        amministrazione.setNome("amm");
        amministrazione.setCognome("amm");
        amministrazione.setPassword("amm");
        System.out.println(amministrazione.getEmail());
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
                    System.out.println(cliente.getEmail());
                } else if (parti.length == 5 && parti[0].equals(username) && parti[3].equals(password)) {
                    dipendente.setEmail(parti[0]);
                    dipendente.setNome(parti[1]);
                    dipendente.setCognome(parti[2]);
                    dipendente.setPassword(parti[3]);
                    dipendente.setIdDipendente(Integer.parseInt(String.valueOf(parti[4])));
                    System.out.println(dipendente.getEmail());
                }
            }
        }
    }
}


public void eliminaCliente() {
    cliente = new Cliente();
}


//REGISTRAZIONE

public void Registrazione(String email, String nome, String cognome, String password, ActionEvent event) {


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
                        try {
                            OpenCloseFXML("FXML/Login.fxml", event);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
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

public void caricaOpzionalDaFile(String filePath, List<Optionals> listaOp, VBox checkBoxContainer) throws
        IOException {
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

public void setMarca(ComboBox<Marca> marca) {
    marca.getItems().addAll(getMap().keySet());
}
}



