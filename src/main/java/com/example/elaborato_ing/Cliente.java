package com.example.elaborato_ing;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cliente extends Persona{

    private final StringProperty email = new SimpleStringProperty();

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

    public StringProperty emailProperty() {
        return email;
    }
}
