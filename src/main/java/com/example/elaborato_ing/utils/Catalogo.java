package com.example.elaborato_ing.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Catalogo {
    private final List<AutoNuova> catalogo = new ArrayList<>();

    public Catalogo() {

    }

    public AutoNuova getAutoNuova(Marca marca, String modello){
        for(AutoNuova a:catalogo){
            if(a.getMarca() == marca && Objects.equals(a.getModello(), modello)){
                return a;
            }
        }
        return null;
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

    public Motore getMotore(Marca marca,String modello,String nomeMotore) {
        for (AutoNuova auto : catalogo) {
            if (auto.getMarca()==marca && auto.getModello().equals(modello)) {
                return auto.trovaMotore(nomeMotore);
            }
        }
        return null;
    }
}
