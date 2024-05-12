package com.example.elaborato_ing;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Auto {
    private final Marca marca;
    private final String modello;
    private final double altezza;
    private final double lunghezza;
    private final double larghezza;
    private final double peso;
    private final double volumeBagagliaio;
    private List<Image> immagine;
    private final Motore motore;
    private final List<OP> optional;

    public Auto(Marca marca, String modello, double altezza, double lunghezza, double larghezza, double peso, double volumeBagagliaio, Motore motore) {
        this.marca = marca;
        this.modello = modello;
        this.altezza = altezza;
        this.lunghezza = lunghezza;
        this.larghezza = larghezza;
        this.peso = peso;
        this.volumeBagagliaio = volumeBagagliaio;
        this.motore = motore;
        immagine = new ArrayList<>();
        optional = new ArrayList<>();
    }

    @Override
    public String toString() {
        return marca + "," + modello + "," + altezza + "," + lunghezza + "," + larghezza + "," + peso + "," + volumeBagagliaio + "," + motore + "," + stampa();
    }

    public String stampa(){
        String res = "";
        for(OP op : optional){
            res += op.toString() + ";";
        }
        return res;
    }
    public void addImgs(ImageView img1, ImageView img2, ImageView img3) {
        immagine.add(img1.getImage());
        immagine.add(img2.getImage());
        immagine.add(img3.getImage());
    }


    public Marca getMarca() {
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

    public List<Image> getImmagine() {
        return immagine;
    }

    public Motore getMotore() {
        return motore;
    }


    public List<OP> getOptional() {
        return optional;
    }

    public void setOptional(OP optional) {
        this.optional.add(optional);
    }
    public void aggiungiOptional(boolean infot, boolean sensori, boolean fari, boolean sedili, boolean scorta, boolean vetri, boolean interni, boolean ruote, boolean cruise) {
        if (infot) {
            optional.add(OP.infotainment);
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

}
