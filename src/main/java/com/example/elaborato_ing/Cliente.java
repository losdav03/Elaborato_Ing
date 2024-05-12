package com.example.elaborato_ing;

import java.lang.classfile.ClassElement;

public class Cliente extends Persona{

    public Cliente(String email, String nome, String cognome, String password) {
        super(email, nome, cognome, password);
    }

    public Cliente() {
        super();
    }

    @Override
    public String toString() {
        return super.getEmail();
    }
}
