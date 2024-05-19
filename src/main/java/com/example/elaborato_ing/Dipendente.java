package com.example.elaborato_ing;

public class Dipendente extends Persona {
    public int IdDipendente;

    public Dipendente() {
    }

    public Dipendente(String email, String nome, String cognome, String password, int dipendente) {
        super(email, nome, cognome, password);
        this.IdDipendente = dipendente;
    }

    public void setIdDipendente(int dipendente) {
        this.IdDipendente = dipendente;
    }
}
