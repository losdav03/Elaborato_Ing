package com.example.elaborato_ing;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AutoUsata extends Auto {
    private final String colore;

    public AutoUsata(Marca marca, String modello, double altezza, double lunghezza, double larghezza, double peso, double volumeBagagliaio, Motore motore, String colore) {
        super(marca, modello, altezza, lunghezza, larghezza, peso, volumeBagagliaio, motore);
        this.colore = colore;
    }

    public String getColore() {
        return colore;
    }
}
