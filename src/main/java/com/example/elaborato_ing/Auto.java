package com.example.elaborato_ing;

import java.util.ArrayList;
import java.util.List;

public class Auto {
    private final Marca marca;
    private final String modello;
    private final double altezza;
    private final double lunghezza;
    private final double larghezza;
    private final double peso;
    private final double volumeBagagliaio;
    private List<String> immagini = new ArrayList<>();
    private final Motore motore;
    private List<Optionals> optionalSelezionabili;
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
        return marca + "," + modello + "," + altezza + "," + lunghezza + "," + larghezza + "," + peso + "," + volumeBagagliaio + "," + motore + "," + stampaSelezionabili();
    }

    public String stampaSelezionabili() {
        String res = "";
        for (Optionals op : optionalSelezionabili) {
            res += op.getNome() + ";" + op.getCosto() + ":";
        }
        return res;
    }

    public void addImgs(String img1, String img2, String img3) {
        immagini.add(img1);
        immagini.add(img2);
        immagini.add(img3);
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

    public List<String> getImmagini() {
        return immagini;
    }

    public Motore getMotore() {
        return motore;
    }

    public void setOptionalSelezionabili(List<Optionals> optionalSelezionabili) {
        this.optionalSelezionabili=optionalSelezionabili;
    }
    public List<Optionals> getOptionalSelezionabili() {
        return optionalSelezionabili;
    }
}
