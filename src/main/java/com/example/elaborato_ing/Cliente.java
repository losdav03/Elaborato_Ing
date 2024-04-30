package com.example.elaborato_ing;

public class Cliente extends Persona{
    public int idCliente;

    public Cliente(String email, String nome, String cognome, String password, int idCliente) {
        super(email, nome, cognome, password);
        this.idCliente = idCliente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
}
