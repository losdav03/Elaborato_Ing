package com.example.elaborato_ing;

import javafx.scene.image.Image;

import java.util.List;

public class Auto {
    private Marca marca;
    private Modello modello;
    private double altezza, lunghezza, larghezza, peso, volumeBagagliaio;
    private List<Image> immagine;
    private Motore motore;
    private Alimentazione alimentazione;
    private Optionals optionals;
    private int costo;
    private String sconto;

    public Auto(Marca marca, Modello modello, double lunghezza, double altezza, double larghezza, double peso, double volumeBagagliaio, Motore motore, int costo, String sconto) {
        this.marca = marca;
        this.modello = modello;
        this.lunghezza = lunghezza;
        this.altezza = altezza;
        this.larghezza = larghezza;
        this.peso = peso;
        this.volumeBagagliaio = volumeBagagliaio;
        this.motore = motore;
        this.costo=costo;
        this.sconto=sconto;
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

    public Alimentazione getAlimentazione() {
        return alimentazione;
    }

    public void setAlimentazione(Alimentazione alimentazione) {
        this.alimentazione = alimentazione;
    }



    public String getSconto() {
        return sconto;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public void setSconto(String sconto) {
        this.sconto = sconto;
    }
}
