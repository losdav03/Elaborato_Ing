package com.example.elaborato_ing;

public class Amministrazione extends Persona{
    public Amministrazione() {
    }

    public Amministrazione(String email, String nome, String cognome, String password) {
        super(email, nome, cognome, password);
    }

    public Amministrazione(String email) {
        super(email);
    }

}
