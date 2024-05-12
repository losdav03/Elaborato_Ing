package com.example.elaborato_ing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Catalogo {
    private final List<AutoNuova> catalogo = new ArrayList<>();

    public Catalogo() {

    }

    public void add(Auto a) {
        catalogo.add((AutoNuova) a);
    }

    public List<AutoNuova> getListaAuto() {
        return catalogo;
    }

    public AutoNuova getAuto(Marca marca, String modello) {
        for (AutoNuova auto : catalogo) {
            if (auto.getMarca().equals(marca)) {
                if (auto.getModello().equals(modello)) {
                    return auto;
                }
            }
        }
        return null;
    }

    public void remove(Marca marca, String s) {
        Iterator<AutoNuova> iterator = catalogo.iterator();
        while (iterator.hasNext()) {
            AutoNuova auto = iterator.next();
            if (auto.getMarca().equals(marca) && auto.getModello().equals(s)) {
                iterator.remove(); // Rimuovi l'elemento corrente usando l'iteratore
            }
        }
    }

}
