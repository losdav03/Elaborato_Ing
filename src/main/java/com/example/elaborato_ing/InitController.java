package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;


public class InitController {
    @FXML
    private Label altezza, lunghezza, larghezza, peso, volume, alimentazione, motore, prezzo;

    private int costo = 0;

    @FXML
    private ComboBox<Marca> marca;

    @FXML
    private ComboBox<Modello> modello;

    @FXML
    private ComboBox<String> colori;

    @FXML
    private CheckBox infot, sensori, fari, sedili, scorta, vetri, interni, ruote, cruise;

    @FXML
    private Button acquistabtn, vendibtn, btnPDF;

    @FXML
    private ImageView img;
    private Map<Marca, List<Auto>> map;
    private Catalogo catalogo = new Catalogo();
    private Model model = new Model();
    private int vista = 1;

    public void initialize() {
        String filePath = "src\\main\\resources\\com\\example\\elaborato_ing\\TXT\\Catalogo.txt";
        File file = new File(filePath);

        if (!file.exists()) {
            System.err.println("File non trovato: " + filePath);
            return;
        }

        map = model.caricaDaFile(filePath, catalogo);
        marca.getItems().addAll(map.keySet());
        marca.setOnAction(e -> aggiornaModello());
        modello.setOnAction(e -> aggiornaColori());
        vista = 1;
        colori.setOnAction(e -> aggiornaImg());

        colori.setDisable(true);
        infot.setDisable(true);
        sensori.setDisable(true);
        fari.setDisable(true);
        sedili.setDisable(true);
        scorta.setDisable(true);
        vetri.setDisable(true);
        interni.setDisable(true);
        ruote.setDisable(true);
        cruise.setDisable(true);
        btnPDF.setVisible(false);
    }

    private void aggiornaImg() {
        String path = model.getImgColori(marca.getValue(), modello.getValue(), colori.getValue(), vista);
        InputStream imageStream = getClass().getResourceAsStream(path);

        if (imageStream != null) {
            Image image = new Image(imageStream);
            img.setImage(image);
        }

    }


    private void aggiornaModello() {
        Marca marcaSelezionata = marca.getValue();

        if (marcaSelezionata != null) {
            List<Auto> listaAuto = map.getOrDefault(marcaSelezionata, Collections.emptyList());
            List<Modello> listaModelli = listaAuto.stream().map(Auto::getModello).toList();

            modello.getItems().setAll(listaModelli);
            modello.setDisable(false);
        } else {
            modello.getItems().clear();
            modello.setDisable(true);
        }
    }


    private void aggiornaColori() {
        Marca marcaSelezionata = marca.getValue();
        Modello modelloSelezionato = modello.getValue();


        if (marcaSelezionata != null && modelloSelezionato != null) {

            Auto auto = map.values().stream().flatMap(List::stream).filter(a -> a.getModello().equals(modelloSelezionato)).findFirst().orElse(null);

            colori.setDisable(modelloSelezionato.toString().equals("CYBERTRUCK"));
            infot.setDisable(false);
            sensori.setDisable(false);
            fari.setDisable(false);
            sedili.setDisable(false);
            scorta.setDisable(false);
            vetri.setDisable(false);
            interni.setDisable(false);
            ruote.setDisable(false);
            cruise.setDisable(false);

            if (auto != null) {
                lunghezza.setText(String.valueOf(auto.getLunghezza()));
                altezza.setText(String.valueOf(auto.getAltezza()));
                larghezza.setText(String.valueOf(auto.getLarghezza()));
                peso.setText(String.valueOf(auto.getPeso()));
                volume.setText(String.valueOf(auto.getVolumeBagagliaio()));
                alimentazione.setText(String.valueOf(auto.getAlimentazione()));
                motore.setText(String.valueOf(auto.getMotore()));
                prezzo.setText(String.valueOf(auto.getCosto()));
                colori.getItems().clear();
                colori.getItems().addAll(auto.getColori());
                colori.setDisable(false);
            }
        }
    }

    public void goToUsatoForm(ActionEvent event) {
        loadScene("FXML/Usato.fxml", event);
    }

    public void loadScene(String fxmlFile, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            if (event != null && event.getSource() instanceof Node) {
                // Se l'evento è presente e proviene da un nodo, allora è stata richiesta una finestra modale
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(primaryStage);
                primaryStage.hide(); // Nasconde la finestra principale invece di chiuderla
                // Aggiungi un listener per gestire l'evento di chiusura della finestra del login
                stage.setOnCloseRequest(e -> primaryStage.show()); // Mostra di nuovo la finestra principale quando la finestra del login viene chiusa
            } else {
                // Altrimenti, se l'evento è nullo o non proviene da un nodo, apri la finestra in modo modale
                stage.initModality(Modality.APPLICATION_MODAL);
            }

            stage.showAndWait(); // Mostra la finestra e attendi che venga chiusa
        } catch (IOException e) {
            System.err.println("Errore nel caricamento della scena: " + e.getMessage());
        }
    }


    public void addOption(ActionEvent actionEvent) {
        Modello modelloSelezionato = modello.getValue();
        Auto auto = map.values().stream()
                .flatMap(List::stream)
                .filter(a -> a.getModello().equals(modelloSelezionato))
                .findFirst()
                .orElse(null);

        if (auto != null) {
            int costoAggiuntivo = auto.getCosto() / 300;
            int costoCheckBox = 0;

            if (infot.isSelected()) {
                costoCheckBox += costoAggiuntivo;
            }
            if (sensori.isSelected()) {
                costoCheckBox += costoAggiuntivo;
            }
            if (fari.isSelected()) {
                costoCheckBox += costoAggiuntivo;
            }
            if (sedili.isSelected()) {
                costoCheckBox += costoAggiuntivo;
            }
            if (scorta.isSelected()) {
                costoCheckBox += costoAggiuntivo;
            }
            if (vetri.isSelected()) {
                costoCheckBox += costoAggiuntivo;
            }
            if (interni.isSelected()) {
                costoCheckBox += costoAggiuntivo;
            }
            if (ruote.isSelected()) {
                costoCheckBox += costoAggiuntivo;
            }
            if (cruise.isSelected()) {
                costoCheckBox += costoAggiuntivo;
            }

            costo = auto.getCosto() + costoCheckBox;
            prezzo.setText(String.valueOf(costo));
        }
    }

    public void btnSx(ActionEvent actionEvent) {
        String pathSx = "";
        switch (vista) {
            case 1:
                vista = 3;
                pathSx = model.getImgColori(marca.getValue(), modello.getValue(), colori.getValue(), vista);
                break;
            case 2:
                vista = 1;
                pathSx = model.getImgColori(marca.getValue(), modello.getValue(), colori.getValue(), vista);
                break;
            case 3:
                vista = 2;
                pathSx = model.getImgColori(marca.getValue(), modello.getValue(), colori.getValue(), vista);
                break;
        }
        InputStream imageStream = getClass().getResourceAsStream(pathSx);

        if (imageStream != null) {
            Image image = new Image(imageStream);
            img.setImage(image);
        }
    }

    public void btnDx(ActionEvent actionEvent) {
        String pathDx = "";
        switch (vista) {
            case 1:
                vista = 2;
                pathDx = model.getImgColori(marca.getValue(), modello.getValue(), colori.getValue(), vista);
                break;
            case 2:
                vista = 3;
                pathDx = model.getImgColori(marca.getValue(), modello.getValue(), colori.getValue(), vista);
                break;
            case 3:
                vista = 1;
                pathDx = model.getImgColori(marca.getValue(), modello.getValue(), colori.getValue(), vista);
                break;
        }
        InputStream imageStream = getClass().getResourceAsStream(pathDx);

        if (imageStream != null) {
            Image image = new Image(imageStream);
            img.setImage(image);
        }
    }

    public void acquistaFunction(ActionEvent actionEvent) {
        if (acquistabtn.getText().equals("Login") && !prezzo.getText().isEmpty()) {
            acquistabtn.setText("Inoltra Preventivo");
            loadScene("FXML/Login.fxml", actionEvent);
        }
        if (acquistabtn.getText().equals("Inoltra Preventivo") && !prezzo.getText().isEmpty()) {
            // manca codice per esportare e aggiungere il preventivo in un file txt e creare l'oggetto Preventivo

            // abilito il  bottone PDF
            btnPDF.setVisible(true);

        }


    }

    public void generaPDF(ActionEvent actionEvent) {
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
            e.printStackTrace();
        }
    }


}






