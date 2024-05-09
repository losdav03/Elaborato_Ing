package com.example.elaborato_ing;

import java.util.Date;

public class Preventivo {
    private final String id;
    private final Date creazione;
    private final Date scadenza;
    private final Cliente cliente;
    private Dipendente dipendente;
    private final Auto auto;
    private Stato stato;

    public Preventivo(String id, Date creazione, Date scadenza, Cliente cliente, Auto auto) {
        this.id = id;
        this.creazione = creazione;
        this.scadenza = scadenza;
        this.cliente = cliente;
        this.auto = auto;
    }


    public String toString() {
        return id + "," + cliente.toString() + "," + auto.toString();

    }
}
