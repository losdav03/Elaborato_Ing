package com.example.elaborato_ing;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Auto {
    private Marca marca;
    private Modello modello;
    private double altezza, lunghezza, larghezza, peso, volumeBagagliaio;
    private List<Image> immagine;
    private Motore motore;
    private Alimentazione alimentazione;
    private Optionals optionals;
    private int costo;
    private String sconto;
    private List<String> colori;

    public Auto(Marca marca, Modello modello, double lunghezza, double altezza, double larghezza, double peso, double volumeBagagliaio, Motore motore, int costo, String sconto, List<String> colori) {
        this.marca = marca;
        this.modello = modello;
        this.lunghezza = lunghezza;
        this.altezza = altezza;
        this.larghezza = larghezza;
        this.peso = peso;
        this.volumeBagagliaio = volumeBagagliaio;
        this.motore = motore;
        this.costo=costo;
        this.sconto=sconto;
        this.colori=colori;
        this.immagine = new ArrayList<>();

        for(String colore : colori){
            caricaImmaginiPerColore(marca,modello,colore);
        }
    }

    private void caricaImmaginiPerColore(Marca marca, Modello modello, String colore) {
        List<String> viste = List.of("1","2","3");
        for(String vista : viste){
            String percorso = "C:\\Users\\rockg\\IdeaProjects\\Elaborato_Ing\\src\\main\\resources\\com\\example\\elaborato_ing\\images\\"+marca.toString().toLowerCase()+modello.toString().toLowerCase()+colore.toLowerCase()+vista+".png";
            try {
                Image img = new Image(getClass().getResourceAsStream(percorso));
                immagine.add(img);
            } catch (Exception e) {
                System.err.println("Immagine non trovata: " + percorso);
            }
        }
    }
    public Marca getMarca() {
        return marca;
    }
    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Modello getModello() {
        return modello;
    }

    public void setModello(Modello modello) {
        this.modello = modello;
    }

    public double getAltezza() {
        return altezza;
    }

    public void setAltezza(double altezza) {
        this.altezza = altezza;
    }

    public double getLunghezza() {
        return lunghezza;
    }

    public void setLunghezza(double lunghezza) {
        this.lunghezza = lunghezza;
    }

    public double getLarghezza() {
        return larghezza;
    }

    public void setLarghezza(double larghezza) {
        this.larghezza = larghezza;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getVolumeBagagliaio() {
        return volumeBagagliaio;
    }

    public void setVolumeBagagliaio(double volumeBagagliaio) {
        this.volumeBagagliaio = volumeBagagliaio;
    }

    public List<Image> getImmagine() {
        return immagine;
    }

    public void setImmagine(List<Image> immagine) {
        this.immagine = immagine;
    }

    public Motore getMotore() {
        return motore;
    }

    public void setMotore(Motore motore) {
        this.motore = motore;
    }

    public Alimentazione getAlimentazione() {
        return alimentazione;
    }

    public void setAlimentazione(Alimentazione alimentazione) {
        this.alimentazione = alimentazione;
    }

    public List<String> getColori() {
        return colori;
    }
    private void addImg(Marca marca,Modello modello){

    }
    public String getSconto() {
        return sconto;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public void setSconto(String sconto) {
        this.sconto = sconto;
    }
}
