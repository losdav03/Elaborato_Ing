package com.example.elaborato_ing;

public class Motore {
    private final String nome;
    private final Alimentazione alimentazione;
    private final int cilindrata;
    private final int potenza;
    private final double consumi;

    public Motore(String nome, Alimentazione alimentazione, int cilindrata, int potenza, double consumi) {
        this.nome = nome;
        this.alimentazione = alimentazione;
        this.cilindrata = cilindrata;
        this.potenza = potenza;
        this.consumi = consumi;
    }

    @Override
    public String toString() {
        return nome + ";" + alimentazione + ";" + cilindrata + ";" + potenza + ";" + consumi;
    }

    public String getNome() {
        return nome;
    }

    public Alimentazione getAlimentazione() {
        return alimentazione;
    }

    public int getCilindrata() {
        return cilindrata;
    }

    public int getPotenza() {
        return potenza;
    }

    public double getConsumi() {
        return consumi;
    }
}
