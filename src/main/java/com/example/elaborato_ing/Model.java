package com.example.elaborato_ing;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;


public class Model {
    private static Model instance;
    private static Cliente cliente = new Cliente();
    private static Dipendente dipendente = new Dipendente();
    private static Amministrazione amministrazione = new Amministrazione();
    private static final Map<Marca, List<AutoNuova>> mapAutoNuova = new HashMap<>();
    private static final Map<Marca, List<AutoUsata>> mapAutoUsata = new HashMap<>();
    private static Catalogo catalogo = new Catalogo();
    private List<String> allOptionals = new ArrayList<>();
    private Stage stage;
    private Scene scene;
    private Parent root;
    private File fileScelto1, fileScelto2, fileScelto3;
    private Model() {

    }

    public static Model getInstance(){
        if (instance == null) {
            synchronized (Model.class) {
                if (instance == null) {
                    instance = new Model();
                }
            }
        }
        return instance;
    }

    public Map<Marca, List<AutoNuova>> getMapAutoNuova() {
        return mapAutoNuova;
    }

    public Map<Marca, List<AutoUsata>> getMapAutoUsata() {
        return mapAutoUsata;
    }


    public Catalogo getCatalogo() {
        return catalogo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public File getFileScelto1() {
        return fileScelto1;
    }

    public File getFileScelto2() {
        return fileScelto2;
    }

    public File getFileScelto3() {
        return fileScelto3;
    }

    public void aggiornaFileCatalogo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/com/example/elaborato_ing/TXT/Catalogo.txt"))) {
            for (AutoNuova auto : catalogo.getListaAuto()) {
                writer.write(auto.stampaAutoCatalogo());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Errore durante la scrittura del file: " + e.getMessage());
        }
    }

    public void caricaDaFile(String file, Catalogo catalogo) {
        mapAutoNuova.clear();
        catalogo.getListaAuto().clear();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                String[] parts = line.split(",");
                if (parts.length == 12) {
                    try {
                        Marca marca = Marca.valueOf(parts[0].trim());
                        String modello = parts[1].trim();
                        double altezza = Double.parseDouble(parts[2].trim());
                        double lunghezza = Double.parseDouble(parts[3].trim());
                        double larghezza = Double.parseDouble(parts[4].trim());
                        double peso = Double.parseDouble(parts[5].trim());
                        double volumeBagagliaio = Double.parseDouble(parts[6].trim());

                        // Parsing del motore
                        String[] motorePartsArray = parts[7].trim().split("%");
                        List<Motore> motori = new ArrayList<>();
                        for (String motorePart : motorePartsArray) {
                            String[] motoreParts = motorePart.split(";");
                            if (motoreParts.length != 5)
                                throw new IllegalArgumentException("Dati del motore non corretti");
                            String nomeMotore = motoreParts[0].trim();
                            Alimentazione alimentazione = Alimentazione.valueOf(motoreParts[1].trim());
                            int cilindrata = Integer.parseInt(motoreParts[2].trim());
                            int potenza = Integer.parseInt(motoreParts[3].trim());
                            double consumi = Double.parseDouble(motoreParts[4].trim());
                            Motore motore = new Motore(nomeMotore, alimentazione, cilindrata, potenza, consumi);
                            motori.add(motore);
                        }

                        int prezzo = Integer.parseInt(parts[8].trim());
                        String sconto = parts[9].trim();
                        List<String> colori = List.of(parts[10].trim().split(";"));

                        // Parsing degli optional
                        List<Optionals> optionalSelezionabili = new ArrayList<>();
                        if (!parts[11].trim().isEmpty()) {
                            String[] optionParts = parts[11].trim().split(":");
                            for (String o : optionParts) {
                                String[] optionalParts = o.split(";");
                                if (optionalParts.length == 2) {
                                    Optionals nuovoOp = new Optionals(optionalParts[0].trim(), Integer.parseInt(optionalParts[1].trim()));
                                    optionalSelezionabili.add(nuovoOp);
                                }
                            }
                        }

                        // Creazione dell'oggetto AutoNuova e aggiunta al catalogo

                        AutoNuova auto = new AutoNuova(marca, modello, altezza, lunghezza, larghezza, peso, volumeBagagliaio, motori, prezzo, colori, sconto, optionalSelezionabili);
                        catalogo.add(auto);
                        mapAutoNuova.computeIfAbsent(marca, k -> new ArrayList<>()).add(auto);
                    } catch (Exception e) {
                        System.err.println("Errore nel parsing della riga: " + line);
                        e.printStackTrace();
                    }
                } else {
                    System.err.println("Riga non valida (numero di campi non corretto): " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File non trovato: " + file);
        } catch (IOException e) {
            System.err.println("Errore nella lettura del file: " + e.getMessage());
        }
    }

    public List<String> caricaOptionalDaFile() {
        allOptionals.clear();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/elaborato_ing/TXT/Optionals.txt"));
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                allOptionals.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return allOptionals;
    }

    public void aggiungiOptionalDaFile(String optional) {
        if (!rigaUnica(optional, "src/main/resources/com/example/elaborato_ing/TXT/Optionals.txt")) {
            allOptionals.add(optional);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/com/example/elaborato_ing/TXT/Optionals.txt"))) {
                for (String op : allOptionals) {
                    writer.write(op); // Supponendo che Auto abbia un metodo toString appropriato
                    writer.newLine();
                }
            } catch (IOException e) {
                System.err.println("Errore durante la scrittura del file: " + e.getMessage());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attenzione");
            alert.setHeaderText("L'optional è già presente");
            alert.setContentText("Questo optional esiste già");
            alert.showAndWait();
        }
    }

    public void rimuoviOptionalDaFile(String optional) {
        allOptionals.remove(optional);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/com/example/elaborato_ing/TXT/Optionals.txt"))) {
            for (String op : allOptionals) {
                writer.write(op); // Supponendo che Auto abbia un metodo toString appropriato
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Errore durante la scrittura del file: " + e.getMessage());
        }
    }

    public void generaCheckBoxOptionalConfiguratore(AutoNuova auto, ScrollPane scrollPane, VBox checkBoxContainer, List<Optionals> optionalScelti, Label costo, Label costoScontato) {
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
                        int prezzoScontato = Integer.parseInt(costoScontato.getText()) + item.getCosto();
                        costo.setText(String.valueOf(prezzoAggiornato));
                        costoScontato.setText(String.valueOf(prezzoScontato));

                    } else {
                        optionalScelti.remove(op);
                        int prezzoAggiornato = Integer.parseInt(costo.getText()) - item.getCosto();
                        int prezzoScontato = Integer.parseInt(costoScontato.getText()) - item.getCosto();
                        costo.setText(String.valueOf(prezzoAggiornato));
                        costoScontato.setText(String.valueOf(prezzoScontato));

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
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
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

    public String getImmagineAuto(Marca marca, String modello, String colore, int vista, int tipoAuto, String nomeUtente) {
        if (tipoAuto == 0)
            return getMarcaModelloAutoNuova(marca, modello, mapAutoNuova).getImmagine(colore.toLowerCase(), vista, 0);
        else if (tipoAuto == 1 && !Objects.equals(nomeUtente, "")) {
            return getMarcaModelloAutoUsata(marca, modello, mapAutoUsata).getImmagine(colore.toLowerCase(), vista, 1);
        } else {
            return getMarcaModelloAutoUsata(marca, modello, mapAutoUsata).getImmagine(colore.toLowerCase(), vista, 1);
        }
    }

    public void caricaImmaginiImageView(ImageView imageView1, ImageView imageView2, ImageView imageView3) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleziona un'immagine");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Immagini", "*.png"));

        File immagine = fileChooser.showOpenDialog(imageView1.getScene().getWindow());
        if (immagine != null) {
            Image image = new Image(immagine.toURI().toString());
            if (imageView1.getImage() == null) {
                fileScelto1 = immagine;
                imageView1.setImage(image);
            } else if (imageView2.getImage() == null) {
                fileScelto2 = immagine;
                imageView2.setImage(image);
            } else {
                fileScelto3 = immagine;
                imageView3.setImage(image);
            }
        }
    }


    public void salvaImageViewImage(File fileScelto, ImageView imageView, int vista, Marca marca, String modello, String colore, int tipoAuto) throws IOException {
        if (imageView.getImage() != null) {
            String newFileName = marca.toString().trim().toLowerCase() + modello.trim().toLowerCase() + colore.trim().toLowerCase() + vista + ".png";

            String path;
            if (tipoAuto == 0) {
                path = "src/main/resources/com/example/elaborato_ing/images/";
            } else {
                path = "src/main/resources/com/example/Elaborato_ing/imagesAutoUsate/";
            }
            File destination = new File(path + newFileName);
            copyFile(new File(fileScelto.toURI().getPath()), destination);
        }
    }

    private void copyFile(File source, File destination) throws IOException {
        try (FileInputStream fis = new FileInputStream(source);
             FileOutputStream fos = new FileOutputStream(destination)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
        }
    }

    public void rimuoviImgs(ImageView imageView1, ImageView imageView2, ImageView imageView3) {
        // Trova il primo ImageView con un'immagine e rimuovila
        if (imageView3.getImage() != null) {
            imageView3.setImage(null);
        } else if (imageView2.getImage() != null) {
            imageView2.setImage(null);
        } else if (imageView1.getImage() != null) {
            imageView1.setImage(null);
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

    public void openCloseFXML(String fxmlPath, ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setOnCloseRequest(e -> {
            aggiornaFileCatalogo();
            caricaDaFile("src/main/resources/com/example/elaborato_ing/TXT/Catalogo.txt", getCatalogo());
        });
        scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void onCloseFXML(String path, Node nodo) {

        Platform.runLater(() -> {
            Stage stage = (Stage) nodo.getScene().getWindow();
            stage.setOnCloseRequest(event -> handleCloseRequest(path));
        });
    }

    private void handleCloseRequest(String path) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        try {
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gestisci eventuali eccezioni
        }
    }

    //LOGIN
    public int autenticato(String username, String password) throws IOException {
        if (username.equals("amm") && password.equals("amm")) {
            amministrazione.setEmail("amm");
            amministrazione.setNome("amm");
            amministrazione.setCognome("amm");
            amministrazione.setPassword("amm");
            return 1;
        } else {
            try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/com/example/elaborato_ing/TXT/LoginFile.txt"))) {
                String line;
                while ((line = br.readLine()) != null && !line.isEmpty()) {
                    String[] parti = line.split(",");
                    if (parti.length == 4 && parti[0].equals(username) && parti[3].equals(password)) {
                        cliente.setEmail(parti[0]);
                        cliente.setNome(parti[1]);
                        cliente.setCognome(parti[2]);
                        cliente.setPassword(parti[3]);
                        return 2;
                    } else if (parti.length == 5 && parti[0].equals(username) && parti[3].equals(password)) {
                        dipendente.setEmail(parti[0]);
                        dipendente.setNome(parti[1]);
                        dipendente.setCognome(parti[2]);
                        dipendente.setPassword(parti[3]);
                        dipendente.setIdDipendente(Integer.parseInt(String.valueOf(parti[4])));
                        return 3;
                    }
                }
            }
        }
        return -1;
    }

    public void eliminaCliente() {
        cliente = new Cliente();
    }

    //REGISTRAZIONE

    public void registrazione(String email, String nome, String cognome, String password, ActionEvent event) {
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
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confermi la registrazione?", ButtonType.YES, ButtonType.NO);
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.YES) {
                            try {
                                // Scrivi i dati dell'utente nel file, separati da virgole
                                writer.write(email + "," + nome + "," + cognome + "," + password + "\n");
                                openCloseFXML("FXML/Login.fxml", event);
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
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                String[] parti = line.split(",");
                if (parti[0].equals(email)) {
                    return true;
                }
            }
        }
        return false;
    }


    public void inoltraPreventivo(Auto auto, String colore, int Prezzo, Sede sede) throws IOException {
        LocalDate inizio = LocalDate.now();
        LocalDate fine;

        if (auto instanceof AutoNuova) {
            int giorni = 0;
            for (Optionals _ : ((AutoNuova) auto).getOptionalScelti()) {
                giorni += 10;
            }
            fine = inizio.plusMonths(1).plusDays(giorni);
        } else {
            fine = inizio.plusYears(1);
        }

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date dataCreazione = Date.from(inizio.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dataFine = Date.from(fine.atStartOfDay(ZoneId.systemDefault()).toInstant());
        String idPreventivo = String.valueOf(creazioneIdPreventivo(auto.hashCode()+ cliente.getEmail()));
        Preventivo preventivo = new Preventivo(idPreventivo, dataCreazione, (auto instanceof AutoNuova) ? dataFine : null, cliente, auto, sede);

        String stato = (auto instanceof AutoNuova) ? String.valueOf(Stato.DA_PAGARE) : String.valueOf(Stato.DA_VALUTARE);
        String prv = (auto instanceof AutoNuova) ?
                String.format("%s,%s,%s,%s,%d,%s\n", preventivo, colore.toUpperCase(), formato.format(dataCreazione), formato.format(dataFine), Prezzo, stato) :
                String.format("%s,%s,%s,%s,%s,%s,%s\n", preventivo, colore.toUpperCase(), formato.format(dataCreazione), "ritiro auto da definire", "da definire", stato, preventivo.setDipendente(dipendente));

        if (!rigaUnica(prv, "src/main/resources/com/example/elaborato_ing/TXT/Preventivi.txt")) {
            try (FileWriter writer = new FileWriter("src/main/resources/com/example/elaborato_ing/TXT/Preventivi.txt", true)) {
                writer.write(prv);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Fallito");
            alert.setContentText("Hai già effettuato un preventivo identico");
            alert.showAndWait();
        }
    }

    public void isDouble(TextField txt) {
        txt.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String character = event.getCharacter();

            // Consenti il tasto backspace e delete
            if (character.equals("\b") || character.equals("\u007F")) {
                return;
            }

            // Consenti solo numeri, punto decimale e segno meno
            if (!character.matches("[\\d\\.-]")) {
                event.consume(); // Blocca l'evento se non è un numero, punto o segno meno
                return;
            }

            // Assicurati che ci sia solo un punto decimale
            if (character.equals(".") && txt.getText().contains(".")) {
                event.consume(); // Blocca l'evento se c'è già un punto decimale
                return;
            }

            // Assicurati che il segno meno sia solo all'inizio
            if (character.equals("-")) {
                if (txt.getText().contains("-")) {
                    event.consume(); // Blocca se c'è già un segno meno
                } else if (txt.getCaretPosition() > 0) {
                    event.consume(); // Blocca se il segno meno non è all'inizio
                }
            }
        });
    }

    public void numeric(TextField txt) {
        txt.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String character = event.getCharacter();
            // Consenti solo numeri (0-9) e impedisci input di altri caratteri
            // Consenti il tasto backspace e delete
            if (character.equals("\b") || character.equals("\u007F")) {
                return;
            }
            if (!character.matches("\\d")) {
                event.consume(); // Blocca l'evento se non è un numero
            }

        });
    }

    public void checkColore(TextField colore) {
        colore.addEventFilter(KeyEvent.ANY, event -> {
            String character = event.getCharacter();
            // Consenti solo numeri (0-9) e impedisci input di altri caratteri
            if (character.matches("\\d")) {
                event.consume(); // Blocca l'evento se non è un numero
            }
        });
    }

    private boolean rigaUnica(String lineToCheck, String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                if (line.equals(lineToCheck.trim())) {
                    return true;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public int creazioneIdPreventivo(String input) {
        int sum = 0;
        input = input.toUpperCase();

        for (char c : input.toCharArray()) {
            if (c == '_') {
                sum += 10;
            } else if (c >= 'A' && c <= 'Z') {
                sum += c - 'A' + 1;
            } else if (c >= '0' && c <= '9') {
                sum += c - '0';
            } else {
                sum+=10;
            }
        }

        return sum;
    }

    public static AutoNuova getMarcaModelloAutoNuova(Marca marca, String
            modello, Map<Marca, List<AutoNuova>> map) {
        List<AutoNuova> autoList = map.get(marca);
        if (autoList == null) { // Se non esiste una lista per la marca data
            return null;
        }
        for (AutoNuova auto : autoList) {
            if (auto.getModello().equals(modello)) { // Cerca il modello
                return auto; // Se il modello corrisponde, restituisce l'auto
            }
        }
        return null;
    }

    public AutoUsata getMarcaModelloAutoUsata(Marca marca, String modello, Map<Marca, List<AutoUsata>> map) {

        List<AutoUsata> autoList = map.get(marca);
        if (autoList == null) { // Se non esiste una lista per la marca data
            return null;
        }
        for (AutoUsata auto : autoList) {
            if (auto.getModello().equals(modello)) { // Cerca il modello
                return auto; // Se il modello corrisponde, restituisce l'auto
            }
        }
        return null;
    }

    public List<String> vediPreventivi(String s) {
        List<String> filteredLines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/com/example/elaborato_ing/TXT/Preventivi.txt"))) {
            String line;
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                String[] parts = line.split(",");
                if (parts.length > 16 && parts[16].equals(s)) {
                    filteredLines.add(creaStringaPreventivo(parts));
                } else if (parts.length > 16 && parts[1].equals(s)) {
                    filteredLines.add(creaStringaPreventivo(parts));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filteredLines;
    }

    private String creaStringaPreventivo(String[] parts) {
        return "Id Preventivo : " + parts[0] +
                "\nUtente : " + parts[1] +
                "\nMarca : " + parts[2] +
                "\nModello : " + parts[3] +
                "\nAltezza : " + parts[4] + " cm" +
                "\nLunghezza : " + parts[5] + " cm" +
                "\nLarghezza : " + parts[6] + " cm" +
                "\nPeso : " + parts[7] + " kg" +
                "\nVolume Bagagliaio : " + parts[8] + " L" +
                "\nNome motore : " + parts[9].split("%")[0].split(";")[0] +
                "\nAlimentazione : " + parts[9].split("%")[0].split(";")[1] +
                "\nCilindrata : " + parts[9].split("%")[0].split(";")[2] + " m³" +
                "\nPotenza : " + parts[9].split("%")[0].split(";")[3] + " kW" +
                "\nConsumi : " + parts[9].split("%")[0].split(";")[4] + " km/L" +
                "\nOptional : " + (parts[10].isEmpty() ? " nessun optional" : parts[10]) +
                "\nSede : " + parts[11] +
                "\nColore : " + parts[12] +
                "\nData Inizio Preventivo : " + parts[13] +
                "\nData Fine Preventivo : " + parts[14] +
                "\nPrezzo : " + parts[15] + " €" +
                "\nStato Preventivo : " + parts[16];
    }

    public static void aggiungiValutazione(String idPreventivo, int prezzo, LocalDate ritiroData) throws
            IOException {
        BufferedReader reader;
        BufferedWriter writer;
        try {
            reader = new BufferedReader(new FileReader("src/main/resources/com/example/elaborato_ing/TXT/Preventivi.txt"));
            StringBuilder fileContent = new StringBuilder();
            String riga;

            while ((riga = reader.readLine()) != null && !riga.isEmpty()) {
                String[] campi = riga.split(",");

                if (campi[0].equals(idPreventivo)) {
                    if (campi.length >= 17 && campi[16].equals(String.valueOf(Stato.DA_VALUTARE))) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Sicuro di voler valutare?", ButtonType.YES, ButtonType.NO);
                        alert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.YES) {
                                campi[15] = String.valueOf(prezzo);
                                campi[16] = String.valueOf(Stato.VALUTATA);
                                campi[14] = ritiroData.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                            }
                        });
                    }
                }
                fileContent.append(String.join(",", campi)).append("\n");
            }
            reader.close();
            writer = new BufferedWriter(new FileWriter("src/main/resources/com/example/elaborato_ing/TXT/Preventivi.txt"));
            writer.write(fileContent.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cancella(String idPreventivo) throws IOException {
        BufferedReader reader;
        BufferedWriter writer;
        try {
            reader = new BufferedReader(new FileReader("src/main/resources/com/example/elaborato_ing/TXT/Preventivi.txt"));
            StringBuilder fileContent = new StringBuilder();
            String riga;

            while ((riga = reader.readLine()) != null && !riga.isEmpty()) {
                String[] campi = riga.split(",");

                if (campi[0].equals(idPreventivo)) {
                    if (campi.length >= 17 && campi[16].equals(Stato.DA_VALUTARE.toString())) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confermi la cancellazione?", ButtonType.YES, ButtonType.NO);
                        alert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.YES) {
                                campi[16] = Stato.RIFIUTATO.toString();
                            }
                        });
                    }
                    fileContent.append(String.join(",", campi)).append("\n");
                }
            }
            reader.close();

            writer = new BufferedWriter(new FileWriter("src/main/resources/com/example/elaborato_ing/TXT/Preventivi.txt"));
            writer.write(fileContent.toString());
            writer.close();
        } catch (IOException e) {
            throw new IOException();

        }
    }

    public static void aggiungiPagamento(String idPreventivo) throws IOException {
        BufferedReader reader;
        BufferedWriter writer;

        try {
            reader = new BufferedReader(new FileReader("src/main/resources/com/example/elaborato_ing/TXT/Preventivi.txt"));
            StringBuilder fileContent = new StringBuilder();
            String riga;

            while ((riga = reader.readLine()) != null && !riga.isEmpty()) {
                String[] campi = riga.split(",");

                if (campi[0].equals(idPreventivo)) {
                    if (campi.length >= 17 && campi[16].equals(Stato.DA_PAGARE.toString())) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confermi il pagamento?", ButtonType.YES, ButtonType.NO);
                        alert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.YES) {
                                campi[16] = Stato.PAGATO.toString();
                            }
                        });
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Preventivo non pagabile");

                        switch (campi[16]) {
                            case "DA_VALUTARE":
                            case "VALUTATO":
                                alert.setContentText("Questo preventivo è di tipo auto usata");
                                break;
                            case "PAGATO":
                                alert.setContentText("Questo preventivo è già stato pagato");
                                break;
                            case "PRONTO":
                                alert.setContentText("Questo preventivo è già pronto per il ritiro");
                                break;
                            case "SCADUTO":
                                alert.setContentText("Questo preventivo è scaduto");
                                break;
                        }
                        alert.showAndWait();
                    }
                }
                fileContent.append(String.join(",", campi)).append("\n");
            }
            reader.close();

            writer = new BufferedWriter(new FileWriter("src/main/resources/com/example/elaborato_ing/TXT/Preventivi.txt"));
            writer.write(fileContent.toString());
            writer.close();
        } catch (IOException e) {
            throw new IOException();
        }
    }

    public void caricaOpzionalDaFile(String filePath, List<Optionals> listaOp, VBox checkBoxContainer) throws
            IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
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
                    if (result.isPresent()) {
                        String prezzoString = result.get();
                        if (prezzoString.matches("\\d+")) {
                            int prezzoOptional = Integer.parseInt(prezzoString);
                            Optionals optional = new Optionals(finalLine, prezzoOptional);
                            listaOp.add(optional);
                        } else {
                            checkBox.setSelected(false);
                        }
                    } else {
                        checkBox.setSelected(false);
                    }
                } else {
                    // Remove Optional from list and reset price
                    listaOp.removeIf(optional -> optional.getNome().equals(finalLine));
                }
            });
            checkBoxContainer.getChildren().add(checkBox);
        }
        reader.close();
    }

    public void avvisa(String idPreventivo) {
        BufferedReader reader;
        BufferedWriter writer;

        try {
            reader = new BufferedReader(new FileReader("src/main/resources/com/example/elaborato_ing/TXT/Preventivi.txt"));
            StringBuilder fileContent = new StringBuilder();
            String riga;

            while ((riga = reader.readLine()) != null && !riga.isEmpty()) {
                String[] campi = riga.split(",");

                if (campi[0].equals(idPreventivo)) {
                    if (campi.length >= 17 && campi[16].equals(Stato.PAGATO.toString())) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Vuoi avvisare il cliente?", ButtonType.YES, ButtonType.NO);
                        alert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.YES) {
                                campi[16] = Stato.PRONTA.toString();
                            }
                        });
                    }
                }
                fileContent.append(String.join(",", campi)).append("\n");
            }
            reader.close();
            writer = new BufferedWriter(new FileWriter("src/main/resources/com/example/elaborato_ing/TXT/Preventivi.txt"));
            writer.write(fileContent.toString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }


    public void setMarca(ComboBox<Marca> marca) {
        marca.getItems().clear();
        marca.getItems().addAll(getMapAutoNuova().keySet());
    }

    public void caricaMappaAutoUsate() {
        mapAutoUsata.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/com/example/elaborato_ing/TXT/Preventivi.txt"))) {
            String line;
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                String[] parts = line.split(",");
                if (parts[16].equals(String.valueOf(Stato.DA_VALUTARE)) || parts[16].equals(String.valueOf(Stato.VALUTATA))) {
                    AutoUsata auto = new AutoUsata(Enum.valueOf(Marca.class, String.valueOf(parts[2])), parts[3], Double.parseDouble(parts[4]), Double.parseDouble(parts[5]), Double.parseDouble(parts[6]), Double.parseDouble(parts[7]), Double.parseDouble(parts[8]), new Motore(parts[9].split(";")[0], Enum.valueOf(Alimentazione.class, String.valueOf(parts[9].split(";")[1])), Integer.parseInt(parts[9].split(";")[2]), Integer.parseInt(parts[9].split(";")[3]), Double.parseDouble(parts[9].split(";")[3])), parts[12], Enum.valueOf(Sede.class, String.valueOf(parts[11])));
                    auto.caricaImmaginiAutoUsata();
                    mapAutoUsata.computeIfAbsent(Enum.valueOf(Marca.class, String.valueOf(parts[2])), k -> new ArrayList<>()).add(auto);
                }


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void generaPDF() throws FileNotFoundException, DocumentException {
        var doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream("src/main/resources/com/example/elaborato_ing/Preventivo.pdf"));
        doc.open();
    }


    public void setImageViewPreventivi(String idPreventivo, ImageView img, int vista) {
        Auto auto = null;
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/com/example/elaborato_ing/TXT/Preventivi.txt"))) {
            String line;
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                String[] parts = line.split(",");
                if (parts[0].equals(idPreventivo) && parts[16].equals(String.valueOf(Stato.DA_VALUTARE)) || parts[16].equals(String.valueOf(Stato.VALUTATA))) {
                    auto = getMarcaModelloAutoUsata(Enum.valueOf(Marca.class, String.valueOf(parts[2])), parts[3], mapAutoUsata);
                } else if (parts[0].equals(idPreventivo)) {
                    auto = getMarcaModelloAutoNuova(Enum.valueOf(Marca.class, String.valueOf(parts[2])), parts[3], mapAutoNuova);
                }
            }
            if (auto != null) {
                String path = auto.getImmagini().get(vista - 1);
                File file = new File(path);
                FileInputStream stream = new FileInputStream(file);
                Image image = new Image(stream);
                img.setImage(image);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sostituisciAuto(AutoNuova auto) {
        for (int i = 0; i < catalogo.getListaAuto().size(); i++) {
            Auto autoEsistente = catalogo.getListaAuto().get(i);
            if (autoEsistente.getModello().equals(auto.getModello()) && autoEsistente.getMarca() == auto.getMarca()) {
                catalogo.getListaAuto().set(i, auto);
                return;
            }
        }
    }

    public String aggiornaAlix(Marca marca, String modello, String motore) {
        AutoNuova auto = catalogo.getAutoNuova(marca, modello);
        for (Motore m : auto.getMotori()) {
            if (m.getNome().equals(motore)) {
                System.out.println(motore + " ma rimane" + m.getNome());
                return m.getAlimentazione().toString();
            }
        }
        return "Motore non trovato";
    }
}
