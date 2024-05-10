package com.example.elaborato_ing;

import javafx.scene.image.Image;

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
    private List<Image> immagine;
    private final Motore motore;
    private final List<OP> optional;

    public Auto(Marca marca, String modello, double altezza, double lunghezza, double larghezza, double peso, double volumeBagagliaio, Motore motore) {
        this.marca = marca;
        this.modello = modello;
        this.altezza = altezza;
        this.lunghezza = lunghezza;
        this.larghezza = larghezza;
        this.peso = peso;
        this.volumeBagagliaio = volumeBagagliaio;
        this.motore = motore;
        immagine = new ArrayList<>();
        optional = new ArrayList<>();
    }

    @Override
    public String toString() {
        return marca + "," + modello + "," + altezza + "," + lunghezza + "," + larghezza + "," + peso + "," + volumeBagagliaio + "," + motore + "," + optional;
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

    public List<Image> getImmagine() {
        return immagine;
    }

    public Motore getMotore() {
        return motore;
    }


    public List<OP> getOptional() {
        return optional;
    }

    public void setOptional(OP optional) {
        this.optional.add(optional);
    }

}
