package com.example.elaborato_ing;

import javafx.scene.image.Image;

import java.util.List;

public class Auto {
    private String marca;
    private String modello;
    private double altezza,lunghezza, larghezza,peso, volumeBagagliaio;
    private List<Image> immagine;
    private Motore motore;
    private List<Optional> optionals;

    public Auto(String marca, String modello, double lunghezza, double altezza, double larghezza, double peso, double volumeBagagliaio, List<Image> immagine, Motore motore, List<Optional> optionals) {
        this.marca = marca;
        this.modello = modello;
        this.lunghezza = lunghezza;
        this.altezza = altezza;
        this.larghezza = larghezza;
        this.peso = peso;
        this.volumeBagagliaio = volumeBagagliaio;
        this.immagine = immagine;
        this.motore = motore;
        this.optionals = optionals;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModello() {
        return modello;
    }

    public void setModello(String modello) {
        this.modello = modello;
    }

    public double getAltezza() {
        return altezza;
    }

    public void setAltezza(double altezza) {
        this.altezza = altezza;
    }

    public double getLunghezza() {
        return lunghezza;
    }

    public void setLunghezza(double lunghezza) {
        this.lunghezza = lunghezza;
    }

    public double getLarghezza() {
        return larghezza;
    }

    public void setLarghezza(double larghezza) {
        this.larghezza = larghezza;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getVolumeBagagliaio() {
        return volumeBagagliaio;
    }

    public void setVolumeBagagliaio(double volumeBagagliaio) {
        this.volumeBagagliaio = volumeBagagliaio;
    }

    public List<Image> getImmagine() {
        return immagine;
    }

    public void setImmagine(List<Image> immagine) {
        this.immagine = immagine;
    }

    public Motore getMotore() {
        return motore;
    }

    public void setMotore(Motore motore) {
        this.motore = motore;
    }

    public List<Optional> getOptionals() {
        return optionals;
    }

    public void setOptionals(List<Optional> optionals) {
        this.optionals = optionals;
    }
}
