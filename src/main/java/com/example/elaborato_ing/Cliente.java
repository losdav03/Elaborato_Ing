package com.example.elaborato_ing;

public class Cliente extends Persona{

    public Cliente(String email, String nome, String cognome, String password) {
        super(email, nome, cognome, password);
    }

    @Override
    public String toString() {
        return super.getEmail();
    }
}
