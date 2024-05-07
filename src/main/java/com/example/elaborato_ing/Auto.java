package com.example.elaborato_ing;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Auto {
    private final Marca marca;
    private final Modello modello;
    private final double altezza;
    private final double lunghezza;
    private final double larghezza;
    private final double peso;
    private final double volumeBagagliaio;
    private final List<Image> immagine;
    private final Motore motore;
    private final int costo;
    private String sconto;
    private final List<String> colori;

    public Auto(Marca marca, Modello modello, double lunghezza, double altezza, double larghezza, double peso, double volumeBagagliaio, Motore motore, int costo, String sconto, List<String> colori) {
        this.marca = marca;
        this.modello = modello;
        this.lunghezza = lunghezza;
        this.altezza = altezza;
        this.larghezza = larghezza;
        this.peso = peso;
        this.volumeBagagliaio = volumeBagagliaio;
        this.motore = motore;
        this.costo = costo;
        this.sconto = sconto;
        this.colori = colori;
        this.immagine = new ArrayList<>();
        caricaImmagini();
    }

    public Auto(Marca marca, Modello modello, double altezza, double lunghezza, double larghezza, double peso, double volumeBagagliaio, Motore motore, List<String> colori){
        this.marca=marca;
    }

    public String getImmagine(String colore,int vista) {
        String path = "/com/example/elaborato_ing/images/" + marca.toString().toLowerCase() + modello.toString().toLowerCase() + colore.toLowerCase() + vista + ".png";
        return Objects.requireNonNull(getClass().getResource(path)).toExternalForm();
    }

    public void caricaImmagini(){
        for(String c:colori){
            for(int i = 1;i<=3;i++) {
                String path = "/com/example/elaborato_ing/images/" + marca.toString().toLowerCase() + modello.toString().toLowerCase() + c.toLowerCase() + i + ".png";
                InputStream imageStream = getClass().getResourceAsStream(path);
                if (imageStream != null) {
                    Image image = new Image(imageStream);
                    immagine.add(image);
                }
            }
        }
    }

    public Marca getMarca() {
        return marca;
    }

    public Modello getModello() {
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

    public int getCosto() {
        return costo;
    }

    public List<String> getColori() {
        return colori;
    }
}
