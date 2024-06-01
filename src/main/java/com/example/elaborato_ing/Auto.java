package com.example.elaborato_ing;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Auto {
    private final Marca marca;
    private final String modello;
    private final double altezza;
    private final double lunghezza;
    private final double larghezza;
    private final double peso;
    private final double volumeBagagliaio;
    private Motore motore;
    private List<String> immagini = new ArrayList<>();
    private final List<Optionals> optionalSelezionabili;


    public Auto(Marca marca, String modello, double altezza, double lunghezza, double larghezza, double peso, double volumeBagagliaio, Motore motore, List<Optionals> optionalSelezionabili) {
        this.marca = marca;
        this.modello = modello;
        this.altezza = altezza;
        this.lunghezza = lunghezza;
        this.larghezza = larghezza;
        this.peso = peso;
        this.volumeBagagliaio = volumeBagagliaio;
        this.motore = motore;
        this.optionalSelezionabili = optionalSelezionabili;
    }


    @Override
    public String toString() {
        return marca + "," + modello + "," + altezza + "," + lunghezza + "," + larghezza + "," + peso + "," + volumeBagagliaio + "," + motore.toString() + "," + stampaSelezionabili();
    }

    public String stampaSelezionabili() {
        StringBuilder res = new StringBuilder();
        if (optionalSelezionabili != null) {

            if (optionalSelezionabili.isEmpty()) {
                res = new StringBuilder(":");
            } else {
                for (Optionals op : optionalSelezionabili) {
                    res.append(op.getNome()).append(";").append(op.getCosto()).append(":");
                }

            }
        }
        return res.toString();
    }

    public String getImmagine(String colore, int vista, int tipoAuto) {
        colore = colore.toLowerCase();
        String basePath;

        if (tipoAuto == 0) {
            basePath = "src/main/resources/com/example/elaborato_ing/images/";
        } else if (tipoAuto == 1) {
            basePath = "src/main/resources/com/example/elaborato_ing/imagesAutoUsate/";
        } else {
            return null; // Gestisci i casi in cui tipoAuto non è né 0 né 1
        }

        for (String imgPath : getImmagini()) {
            boolean isMatchingModel = modello.contains("3") || modello.contains("2") || modello.contains("1");
            boolean isMatchingColor = imgPath.contains(colore);
            boolean isMatchingView = imgPath.contains(String.valueOf(vista));

            if (isMatchingModel && isMatchingColor && (vista == 3 || vista == 2 || vista == 1)) {
                return basePath + String.valueOf(marca).toLowerCase() + modello.toLowerCase() + colore + vista + ".png";
            } else if (isMatchingColor && isMatchingView) {
                return imgPath;
            }
        }
        return null;
    }


    public Marca getMarca() {
        return marca;
    }

    public String getModello() {
        return modello;
    }

    public double getAltezza() {
        return altezza;
    }

    public double getLunghezza() {
        return lunghezza;
    }

    public double getLarghezza() {
        return larghezza;
    }

    public double getPeso() {
        return peso;
    }

    public double getVolumeBagagliaio() {
        return volumeBagagliaio;
    }

    public Motore getMotore() {
        return motore;
    }

    public void setMotore(Motore motore) {
        this.motore = motore;
    }

    public List<String> getImmagini() {
        return immagini;
    }

    public List<Optionals> getOptionalSelezionabili() {
        return optionalSelezionabili;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auto auto = (Auto) o;
        return Double.compare(altezza, auto.altezza) == 0 && Double.compare(lunghezza, auto.lunghezza) == 0 && Double.compare(larghezza, auto.larghezza) == 0 && Double.compare(peso, auto.peso) == 0 && Double.compare(volumeBagagliaio, auto.volumeBagagliaio) == 0 && marca == auto.marca && Objects.equals(modello, auto.modello) && Objects.equals(immagini, auto.immagini) && Objects.equals(optionalSelezionabili, auto.optionalSelezionabili);
    }

    @Override
    public int hashCode() {
        return Objects.hash(marca, modello, altezza, lunghezza, larghezza, peso, volumeBagagliaio, immagini, optionalSelezionabili);
    }
}
