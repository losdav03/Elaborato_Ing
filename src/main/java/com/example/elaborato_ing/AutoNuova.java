package com.example.elaborato_ing;

import javafx.scene.image.Image;

import java.util.List;

public class AutoNuova extends Auto{
    private String descrione;
    private double costo;
    private int sconto;

    public AutoNuova(String marca, String modello, double lunghezza, double altezza, double larghezza, double peso, double volumeBagagliaio, List<Image> immagine, Motore motore, List<Optional> optionals) {
        super(marca, modello, lunghezza, altezza, larghezza, peso, volumeBagagliaio, immagine, motore, optionals);
    }
}
