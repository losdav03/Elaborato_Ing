package com.example.elaborato_ing.utils;


public class Cliente extends Persona {


    public Cliente(String email, String nome, String cognome, String password) {
        super(email, nome, cognome, password);
    }

    public Cliente(String email) {
        super(email);
    }

    public Cliente() {
        super();
    }

    @Override
    public String toString() {
        return super.getEmail();
    }
}
