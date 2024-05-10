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

    public void addImgs(ImageView img1, ImageView img2, ImageView img3) {
        super.getImmagine().add(img1.getImage());
        super.getImmagine().add(img2.getImage());
        super.getImmagine().add(img3.getImage());
    }

    public void aggiungiOptional(boolean infot, boolean sensori, boolean fari, boolean sedili, boolean scorta, boolean vetri, boolean interni, boolean ruote, boolean cruise) {
        if (infot) {
            super.setOptional(OP.infotainment);
        }
        if (sensori) {
            super.setOptional(OP.SensoriParcheggio);
        }
        if (fari) {
            super.setOptional(OP.FariFullLED);
        }
        if (sedili) {
            super.setOptional(OP.SediliRiscaldati);
        }
        if (scorta) {
            super.setOptional(OP.RuotaDiScorta);
        }
        if (vetri) {
            super.setOptional(OP.VetriOscurati);
        }
        if (interni) {
            super.setOptional(OP.InterniInPelle);
        }
        if (ruote) {
            super.setOptional(OP.RuoteGrandi);
        }
        if (cruise) {
            super.setOptional(OP.CruiseControl);
        }
    }

    public String getColore() {
        return colore;
    }
}
