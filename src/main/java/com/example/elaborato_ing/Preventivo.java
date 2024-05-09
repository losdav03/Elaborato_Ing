package com.example.elaborato_ing;

import java.util.Date;

public class Preventivo {
    private String id;
    private Date creazione;
    private Date scadenza;
    private Cliente cliente;
    private Dipendente dipendente;
    private Auto auto;
    private Stato stato;

    public Preventivo(String id, Date creazione, Date scadenza, Cliente cliente, Auto auto) {
        this.id = id;
        this.creazione = creazione;
        this.scadenza = scadenza;
        this.cliente = cliente;
        this.auto = auto;
    }
}
