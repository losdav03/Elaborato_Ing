package com.example.elaborato_ing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Catalogo {
    private final List<AutoNuova> catalogo = new ArrayList<>();

    public Catalogo() {

    }

    public void add(AutoNuova a) {
        if (!catalogo.contains(a))
            catalogo.add(a);
    }

    public List<AutoNuova> getListaAuto() {
        return catalogo;
    }

    public void remove(Marca marca, String s) {
        // Rimuovi l'elemento corrente usando l'iteratore
        catalogo.removeIf(auto -> auto.getMarca().equals(marca) && auto.getModello().equals(s));
    }

}
