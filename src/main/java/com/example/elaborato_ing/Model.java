package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Model {
    public Model() {

    }

    public Map<Marca, List<Auto>> caricaDaFile(String file, Catalogo catalogo) {

        Map<Marca, List<Auto>> dati = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 14) {
                    Marca marca = Marca.valueOf(parts[0].trim());
                    Modello modello = Modello.valueOf(parts[1].trim());
                    double lunghezza = Double.parseDouble(parts[2]);
                    double altezza = Double.parseDouble(parts[3]);
                    double larghezza = Double.parseDouble(parts[4]);
                    double peso = Double.parseDouble(parts[5]);
                    double volume = Double.parseDouble(parts[6]);
                    String nomeMotore = parts[7];
                    Alimentazione alimentazione = Alimentazione.valueOf(parts[8].trim());
                    int cilindrata = Integer.parseInt(parts[9].trim());
                    int potenza = Integer.parseInt(parts[10].trim());
                    double consumi = Double.parseDouble(parts[11]);
                    Motore motore = new Motore(nomeMotore, alimentazione, cilindrata, potenza, consumi);
                    int prezzo = Integer.parseInt(parts[12]);
                    String sconto = parts[13];
                    String[] colorOptions = parts[14].trim().split(";");
                    List<String> colori = Arrays.asList(colorOptions);
                    Auto auto = new Auto(marca, modello, lunghezza, altezza, larghezza, peso, volume, motore, prezzo, sconto, colori);
                    catalogo.add(auto);
                    dati.computeIfAbsent(marca, k -> new ArrayList<>()).add(auto);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File non trovato: " + file);
        } catch (IOException e) {
            System.err.println("Errore nella lettura del file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Errore nei dati: " + e.getMessage());
        }
        return dati;
    }

    public String getImgColori(Marca marca, Modello modello, String colore, int vista) {
        String imagePath = "/com/example/elaborato_ing/images/" + marca.toString().toLowerCase() + modello.toString().toLowerCase() + colore.toLowerCase() + vista + ".png";
        return imagePath;
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

    public void PDF(){
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
