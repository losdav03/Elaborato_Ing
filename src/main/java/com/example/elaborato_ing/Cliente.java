package com.example.elaborato_ing;


public class Cliente extends Persona{

    public Cliente(String email, String nome, String cognome, String password) {
        super(email, nome, cognome, password);
    }

    public Cliente(String email) {

    }

    public Cliente() {
        super();
    }

    @Override
    public String toString() {
        return super.getEmail();
    }
}
