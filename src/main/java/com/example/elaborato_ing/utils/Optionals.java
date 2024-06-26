package com.example.elaborato_ing.utils;

public class Optionals {
    private String nome;
    private int costo;

    public Optionals(String nome, int costo) {
        this.nome = nome;
        this.costo = costo;
    }

    public Optionals(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCosto() {
        return costo;
    }

    @Override
    public String toString() {
        return nome + ";" + costo;
    }
}


