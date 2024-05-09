package com.example.elaborato_ing;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AutoUsata {
    private final String marca;
    private final String modello;
    private final double altezza;
    private final double lunghezza;
    private final double larghezza;
    private final double peso;
    private final double volumeBagagliaio;
    private List<Image> immagini;
    private final Motore motore;
    private final String colore;
    private int prezzo;

    public AutoUsata(String marca, String modello, double altezza, double lunghezza, double larghezza, double peso, double volumeBagagliaio, Motore motore, String colore) {
        this.marca = marca;
        this.modello = modello;
        this.altezza = altezza;
        this.lunghezza = lunghezza;
        this.larghezza = larghezza;
        this.peso = peso;
        this.volumeBagagliaio = volumeBagagliaio;
        this.motore = motore;
        this.colore = colore;
        immagini = new ArrayList<>();
    }

    public String getMarca() {
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

    public List<Image> getImmagini() {
        return immagini;
    }

    public Motore getMotore() {
        return motore;
    }

    public String getColore() {
        return colore;
    }

    public void addImgs(ImageView img1, ImageView img2, ImageView img3) {
        immagini.add(img1.getImage());
        immagini.add(img2.getImage());
        immagini.add(img3.getImage());
    }
    public void setPrezzo(int p){
        prezzo = p;
    }
}