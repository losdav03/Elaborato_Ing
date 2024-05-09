package com.example.elaborato_ing;

import java.util.Date;

public class Preventivo {
    private final String id;
    private final Date creazione;
    private final Date scadenza;
    private final Cliente cliente;
    private Dipendente dipendente;
    private final AutoNuova auto;
    private Stato stato;

    public Preventivo(String id, Date creazione, Date scadenza, Cliente cliente, AutoNuova auto) {
        this.id = id;
        this.creazione = creazione;
        this.scadenza = scadenza;
        this.cliente = cliente;
        this.auto = auto;
    }

    @Override
    public String toString() {
        return  id + "," + creazione.toString() + "," + scadenza.toString() + "," + cliente.toString() + "," + auto.toString();

    }
}
