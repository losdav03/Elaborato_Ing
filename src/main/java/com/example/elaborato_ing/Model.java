package com.example.elaborato_ing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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
}
