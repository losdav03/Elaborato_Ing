package com.example.elaborato_ing;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Auto {
    private final Marca marca;
    private final Modello modello;
    private final double altezza;
    private final double lunghezza;
    private final double larghezza;
    private final double peso;
    private final double volumeBagagliaio;
    private List<Image> immagine;
    private final Motore motore;
    private final int prezzo;
    private String sconto;
    private final List<String> colori;
    private final List<OP> optional;

    public Auto(Marca marca, Modello modello, double altezza, double lunghezza, double larghezza, double peso, double volumeBagagliaio, Motore motore, int prezzo, String sconto, List<String> colori) {
        this.marca = marca;
        this.modello = modello;
        this.altezza = altezza;
        this.lunghezza = lunghezza;
        this.larghezza = larghezza;
        this.peso = peso;
        this.volumeBagagliaio = volumeBagagliaio;
        this.motore = motore;
        this.prezzo = prezzo;
        this.sconto = sconto;
        this.colori = colori;
        immagine = new ArrayList<>();
        optional = new ArrayList<>();
        caricaImmagini();
    }

    public String getImmagine(String colore, int vista) {
        String path = "/com/example/elaborato_ing/images/" + marca.toString().toLowerCase() + modello.toString().toLowerCase() + colore.toLowerCase() + vista + ".png";
        return Objects.requireNonNull(getClass().getResource(path)).toExternalForm();
    }

    public void caricaImmagini() {
        for (String c : colori) {
            for (int i = 1; i <= 3; i++) {
                String path = "/com/example/elaborato_ing/images/" + marca.toString().toLowerCase() + modello.toString().toLowerCase() + c.toLowerCase() + i + ".png";
                InputStream imageStream = getClass().getResourceAsStream(path);
                if (imageStream != null) {
                    Image image = new Image(imageStream);
                    immagine.add(image);
                }
            }
        }
    }

    public void setOptional(boolean infot, boolean sensori, boolean fari, boolean sedili, boolean scorta, boolean vetri, boolean interni, boolean ruote, boolean cruise) {
        if (infot) {
            optional.add(OP.INFOTAINMENT);
        }
        if (sensori) {
            optional.add(OP.SensoriParcheggio);
        }
        if (fari) {
            optional.add(OP.FariFullLED);
        }
        if (sedili) {
            optional.add(OP.SediliRiscaldati);
        }
        if (scorta) {
            optional.add(OP.RuotaDiScorta);
        }
        if (vetri) {
            optional.add(OP.VetriOscurati);
        }
        if (interni) {
            optional.add(OP.InterniInPelle);
        }
        if (ruote) {
            optional.add(OP.RuoteGrandi);
        }
        if (cruise) {
            optional.add(OP.CruiseControl);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auto auto = (Auto) o;
        return Double.compare(altezza, auto.altezza) == 0 && Double.compare(lunghezza, auto.lunghezza) == 0 && Double.compare(larghezza, auto.larghezza) == 0 && Double.compare(peso, auto.peso) == 0 && Double.compare(volumeBagagliaio, auto.volumeBagagliaio) == 0 && prezzo == auto.prezzo && marca == auto.marca && modello == auto.modello && Objects.equals(immagine, auto.immagine) && Objects.equals(motore, auto.motore) && Objects.equals(sconto, auto.sconto) && Objects.equals(colori, auto.colori) && Objects.equals(optional, auto.optional);
    }

    @Override
    public int hashCode() {
        return Objects.hash(marca, modello, altezza, lunghezza, larghezza, peso, volumeBagagliaio, immagine, motore, prezzo, sconto, colori, optional);
    }

    @Override
    public String toString() {
        return marca.toString() + "," + modello.toString() + "," + altezza + "," + lunghezza + "," + larghezza + "," + peso +
                "," + volumeBagagliaio + "," + motore.toString() + "," + prezzo + "," + sconto + "," + colori + "," + optional.toString();
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

    public int getPrezzo() {
        return prezzo;
    }

    public List<String> getColori() {
        return colori;
    }

    public void aggiungiOptional(List<String> optionalSelezionati) {
    }
}
