package com.example.elaborato_ing;

import javafx.scene.image.Image;

import java.util.List;

public class AutoUsata extends Auto{
    private int annoProduzione;
    private int km;
    private int proprietari;
    private Stato carrozzeria;
    private int incidenti;
    private Stato interni;

    public AutoUsata(Marca marca, Modello modello, double lunghezza, double altezza, double larghezza, double peso, double volumeBagagliaio, Motore motore, int costo, String sconto/*, List<String> colori*/) {
        super(marca, modello, lunghezza, altezza, larghezza, peso, volumeBagagliaio, motore, costo, sconto/*, colori*/ );
    }
}
