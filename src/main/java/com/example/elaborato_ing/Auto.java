package com.example.elaborato_ing;

import javafx.scene.image.Image;

import java.util.List;

public class Auto {
    private Marca marca;
    private Modello modello;
    private double altezza, lunghezza, larghezza, peso, volumeBagagliaio;
    private List<Image> immagine;
    private Motore motore;
    private String colore;
    private List<Optional> optionals;

    public Auto(Marca marca, Modello modello, double lunghezza, double altezza, double larghezza, double peso, double volumeBagagliaio, List<Image> immagine, Motore motore, String colore, List<Optional> optionals) {
        this.marca = marca;
        this.modello = modello;
        this.lunghezza = lunghezza;
        this.altezza = altezza;
        this.larghezza = larghezza;
        this.peso = peso;
        this.volumeBagagliaio = volumeBagagliaio;
        this.immagine = immagine;
        this.motore = motore;
        this.colore = colore;
        this.optionals = optionals;
    }

    public Auto(Marca marca, Modello modello, double lunghezza, double altezza, double larghezza, double peso, double volumeBagagliaio, Motore motore, String colore) {
        this.marca = marca;
        this.modello = modello;
        this.lunghezza = lunghezza;
        this.altezza = altezza;
        this.larghezza = larghezza;
        this.peso = peso;
        this.volumeBagagliaio = volumeBagagliaio;
        this.motore = motore;
        this.colore = colore;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Modello getModello() {
        return modello;
    }

    public void setModello(Modello modello) {
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

    public String getColore() {
        return colore;
    }

    public void setColore(String colore) {
        this.colore = colore;
    }

    public List<Optional> getOptionals() {
        return optionals;
    }

    public void setOptionals(List<Optional> optionals) {
        this.optionals = optionals;
    }
}
