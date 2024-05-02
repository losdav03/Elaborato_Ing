package com.example.elaborato_ing;

import javafx.scene.image.Image;

import java.util.List;

public class AutoNuova extends Auto{
    private String descrione;
    private double costo;
    private int sconto;

    public AutoNuova(Marca marca, Modello modello, double lunghezza, double altezza, double larghezza, double peso, double volumeBagagliaio, Motore motore, double costo, String sconto) {
        super(marca, modello, lunghezza, altezza, larghezza, peso, volumeBagagliaio, motore, costo, sconto);
    }
}
