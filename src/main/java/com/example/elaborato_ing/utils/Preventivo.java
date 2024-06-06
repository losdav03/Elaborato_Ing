package com.example.elaborato_ing.utils;

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

    public String perListView() {
        return id + " " + cliente.toString() + " " + auto.getMarca()+ " " + auto.getModello() +" "+ sede;
    }

    public String getId() {
        return id;
    }

    public Sede getSede() {
        return sede;
    }

    public Auto getAuto() {
        return auto;
    }

    public Dipendente getDipendente() {
        return dipendente;
    }

    public String setDipendente(Dipendente dipendente) {
        this.dipendente = dipendente;
        return String.valueOf(dipendente.getIdDipendente());
    }

    public Cliente getCliente() {
        return cliente;
    }
}
