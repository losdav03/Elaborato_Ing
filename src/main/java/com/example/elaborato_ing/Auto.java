package com.example.elaborato_ing;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Auto {
    private final Marca marca;
    private final String modello;
    private final double altezza;
    private final double lunghezza;
    private final double larghezza;
    private final double peso;
    private final double volumeBagagliaio;
    private final List<String> immagini = new ArrayList<>();
    private final List<Optionals> optionalSelezionabili;


    public Auto(Marca marca, String modello, double altezza, double lunghezza, double larghezza, double peso, double volumeBagagliaio, List<Optionals> optionalSelezionabili) {
        this.marca = marca;
        this.modello = modello;
        this.altezza = altezza;
        this.lunghezza = lunghezza;
        this.larghezza = larghezza;
        this.peso = peso;
        this.volumeBagagliaio = volumeBagagliaio;
        this.optionalSelezionabili = optionalSelezionabili;
    }


    @Override
    public String toString() {
        return marca + "," + modello + "," + altezza + "," + lunghezza + "," + larghezza + "," + peso + "," + volumeBagagliaio + "," + stampaSelezionabili();
    }

    public String stampaSelezionabili() {
        StringBuilder res = new StringBuilder();
        if (optionalSelezionabili != null) {

            if (optionalSelezionabili.isEmpty()) {
                res = new StringBuilder(":");
            } else {
                for (Optionals op : optionalSelezionabili) {
                    res.append(op.getNome()).append(";").append(op.getCosto()).append(":");
                }

            }
        }
        return res.toString();
    }

    public String getImmagine(String colore, int vista, int tipoAuto) {
        if (tipoAuto == 0) {
            for (String imgPath : getImmagini()) {
              if (modello.contains("3") && imgPath.contains(colore.toLowerCase()) && vista == 3) {
                  System.out.println(imgPath);
                  return "/com/example/elaborato_ing/images/" + marca + modello + colore.toLowerCase() + "3.png";
              }
                else if (imgPath.contains(colore.toLowerCase()) && imgPath.contains(String.valueOf(vista))) {
                    return imgPath;
                }
            }
        } else if (tipoAuto == 1) {

            for (String imgPath : getImmagini()) {
                if (modello.contains("3") && imgPath.contains(colore.toLowerCase()) && vista == 3)
                    return "/com/example/elaborato_ing/imagesAutoUsate/" + marca + modello + colore.toLowerCase() + "3.png";
                else if (imgPath.contains(colore.toLowerCase()) && imgPath.contains(String.valueOf(vista))) {
                    return imgPath;
                }
            }
        }
        return null;
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

    public List<String> getImmagini() {
        return immagini;
    }

    public List<Optionals> getOptionalSelezionabili() {
        return optionalSelezionabili;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auto auto = (Auto) o;
        return Double.compare(altezza, auto.altezza) == 0 && Double.compare(lunghezza, auto.lunghezza) == 0 && Double.compare(larghezza, auto.larghezza) == 0 && Double.compare(peso, auto.peso) == 0 && Double.compare(volumeBagagliaio, auto.volumeBagagliaio) == 0 && marca == auto.marca && Objects.equals(modello, auto.modello) && Objects.equals(immagini, auto.immagini) && Objects.equals(optionalSelezionabili, auto.optionalSelezionabili);
    }

    @Override
    public int hashCode() {
        return Objects.hash(marca, modello, altezza, lunghezza, larghezza, peso, volumeBagagliaio, immagini, optionalSelezionabili);
    }
}
