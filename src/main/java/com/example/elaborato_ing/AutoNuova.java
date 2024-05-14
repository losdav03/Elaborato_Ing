package com.example.elaborato_ing;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AutoNuova extends Auto {
    private final int prezzo;
    private final String sconto;
    private List<String> colori;
    private List<String> optionalScelti;

    public AutoNuova(Marca marca, String modello, double altezza, double lunghezza, double larghezza, double peso, double volumeBagagliaio, Motore motore, int prezzo, List<String> colori, String sconto, List<String> optionalSelezionabili) {
        super(marca, modello, altezza, lunghezza, larghezza, peso, volumeBagagliaio, motore, optionalSelezionabili);
        this.colori = colori;
        this.sconto = sconto;
        this.prezzo = prezzo;
        this.optionalScelti = new ArrayList<>();
        caricaImmagini();
    }

    public String getImmagine(String colore, int vista) {
        for (String imgPath : super.getImmagini()) {
            if (imgPath.contains(colore.toLowerCase()) && imgPath.contains(String.valueOf(vista)))
                return imgPath;
        }
        return null;
    }

    public void caricaImmagini() {
        for (String c : colori) {
            for (int i = 1; i <= 3; i++) {
                String path = "/com/example/elaborato_ing/images/" + super.getMarca().toString().toLowerCase() + super.getModello().toLowerCase() + c.toLowerCase() + i + ".png";
                super.getImmagini().add(path);
            }

        }
    }

    public List<String> getOptionalScelti() {
        return optionalScelti;
    }

    public void addOptionalScelti(List<String> optionalScelti) {
        this.optionalScelti = optionalScelti;
    }

    public String getSconto() {
        return sconto;
    }

    public List<String> getColori() {
        return colori;
    }

    public int getPrezzo() {
        return prezzo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AutoNuova autoNuova = (AutoNuova) o;
        return Objects.equals(sconto, autoNuova.sconto) && Objects.equals(colori, autoNuova.colori);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sconto, colori);
    }
}
