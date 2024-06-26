package com.example.elaborato_ing.utils;

public class AutoUsata extends Auto {
    private final String colore;
    private Sede sede;

    public AutoUsata(Marca marca, String modello, double altezza, double lunghezza, double larghezza, double peso, double volumeBagagliaio, Motore motore, String colore, Sede sede) {
        super(marca, modello, altezza, lunghezza, larghezza, peso, volumeBagagliaio, motore, null);
        this.colore = colore;
        this.sede = sede;
    }

    public void caricaImmaginiAutoUsata() {
        for (int i = 1; i <= 3; i++) {
            String path = "src/main/resources/com/example/elaborato_ing/imagesAutoUsate/" + super.getMarca().toString().toLowerCase() + super.getModello().toLowerCase() + colore.toLowerCase() + i + ".png";
            super.getImmagini().add(path);
        }
    }

    public String getColore() {
        return colore;
    }
}
