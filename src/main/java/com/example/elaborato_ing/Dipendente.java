package com.example.elaborato_ing;

public class Dipendente extends Persona{
    public int idDipendente;

    public Dipendente(String email, String nome, String cognome, String password, int idDipendente) {
        super(email, nome, cognome, password);
        this.idDipendente = idDipendente;
    }

    public int getIdDipendente() {
        return idDipendente;
    }

    public void setIdDipendente(int idDipendente) {
        this.idDipendente = idDipendente;
    }
}
