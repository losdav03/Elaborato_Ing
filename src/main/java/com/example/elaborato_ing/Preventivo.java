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
    private final Sede sede;

    public Preventivo(String id, Date creazione, Date scadenza, Cliente cliente, Auto auto, Sede sede) {
        this.id = id;
        this.creazione = creazione;
        this.scadenza = scadenza;
        this.cliente = cliente;
        this.auto = auto;
        this.sede = sede;
        if (auto instanceof AutoNuova) {
            stato = Stato.DA_PAGARE;
        } else {
            stato = Stato.DA_VALUTARE;
        }
    }

    public String toString() {
        return id + "," + cliente.toString() + "," + auto.toString() + "," + sede;
    }

    public Sede getSede() {
        return sede;
    }

    public Auto getAuto() {
        return auto;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
