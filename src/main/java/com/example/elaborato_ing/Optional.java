package com.example.elaborato_ing;

public class Optional {
    private OP nome;
    private int costo;

    public Optional(OP nome, int costo) {
        this.nome = nome;
        this.costo = costo;
    }

    public OP getNome() {
        return nome;
    }

    public void setNome(OP nome) {
        this.nome = nome;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }
}
