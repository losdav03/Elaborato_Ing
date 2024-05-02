package com.example.elaborato_ing;

public class Motore {
    private String nome;
    private Alimentazione alimentazione;
    private int cilindrata;
    private int potenza;
    private double consumi;
    public Motore(String nome,Alimentazione alimentazione, int cilindrata, int potenza, double consumi){
        this.nome=nome;
        this.alimentazione=alimentazione;
        this.cilindrata=cilindrata;
        this.potenza=potenza;
        this.consumi=consumi;
    }

    @Override
    public String toString() {
        return nome+" alimentazione "+alimentazione+" "+cilindrata+" "+potenza+" "+consumi;
    }
}
