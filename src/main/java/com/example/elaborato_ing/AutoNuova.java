package com.example.elaborato_ing;

import java.time.LocalDate;
import java.util.*;

public class AutoNuova extends Auto {
    private int prezzo;
    private String sconto;
    private List<String> colori;
    private List<Optionals> optionalScelti;

    private static final Map<Character, Integer> mappaSconti = new HashMap<>();

    static {
        mappaSconti.put('A', 10);
        mappaSconti.put('B', 5);
        mappaSconti.put('C', 20);
        mappaSconti.put('D', 15);
        mappaSconti.put('E', 25);
        mappaSconti.put('F', 5);
        mappaSconti.put('G', 30);
        mappaSconti.put('H', 10);
        mappaSconti.put('I', 20);
        mappaSconti.put('J', 15);
        mappaSconti.put('K', 10);
        mappaSconti.put('L', 5);
    }

    public AutoNuova(Marca marca, String modello, double altezza, double lunghezza, double larghezza, double peso, double volumeBagagliaio, Motore motore, int prezzo, List<String> colori, String sconto, List<Optionals> optionalSelezionabili) {
        super(marca, modello, altezza, lunghezza, larghezza, peso, volumeBagagliaio, motore, optionalSelezionabili);
        this.colori = colori;
        this.sconto = sconto;
        this.prezzo = prezzo;
        this.optionalScelti = new ArrayList<>();
        caricaImmaginiAutoNuova();
    }

    public void setOptionalScelti(List<Optionals> optionalScelti) {
        this.optionalScelti = optionalScelti;
    }

    public int calcolaPrezzoScontato() {
        char currentMonthCode = getCurrentMonthCode();
        if (sconto.indexOf(currentMonthCode) >= 0) {
            int discountPercentage = mappaSconti.getOrDefault(currentMonthCode, 0);
            return prezzo - (prezzo * discountPercentage / 100);
        }
        return prezzo;
    }

    private char getCurrentMonthCode() {
        int month = LocalDate.now().getMonthValue();
        return (char) ('A' + (month - 1));
    }


    public void caricaImmaginiAutoNuova() {
        for (String c : colori) {
            for (int i = 1; i <= 3; i++) {
                String path = "/com/example/elaborato_ing/images/" + super.getMarca().toString().toLowerCase() + super.getModello().toLowerCase() + c.toLowerCase() + i + ".png";
                super.getImmagini().add(path);
            }
        }
    }

    public List<Optionals> getOptionalScelti() {
        return optionalScelti;
    }

    public String getSconto() {
        return sconto;
    }

    public List<String> getColori() {
        return colori;
    }

    public void setColori(List<String> colori) {
        this.colori = colori;
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

    @Override
    public String toString() {
        return super.getMarca() + "," + super.getModello() + "," + super.getAltezza() + "," + super.getLunghezza() + "," + super.getLarghezza() + "," + super.getPeso() + "," + super.getVolumeBagagliaio() + "," + super.getMotore() + "," + stampaScelti();
    }

    public String stampaAutoCatalogo() {
        return super.getMarca() + "," + super.getModello().toUpperCase() + "," + super.getAltezza() + "," + super.getLunghezza() + "," + super.getLarghezza() + "," + super.getPeso() + "," + super.getVolumeBagagliaio() + "," + super.getMotore() + "," + prezzo + "," + sconto + "," + stampaColori() + "," + super.stampaSelezionabili();
    }

    public String stampaColori() {
        StringBuilder res = new StringBuilder();
        for (String c : colori) {
            res.append(c).append(";");
        }
        return res.toString();
    }

    public void setSconto(String sconto) {
        this.sconto = sconto;
    }

    public String stampaScelti() {
        StringBuilder res = new StringBuilder();
        for (Optionals op : optionalScelti) {
            res.append(op.getNome()).append(";");
        }
        return res.toString();
    }

    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }
}
