package com.example.elaborato_ing;

import java.util.ArrayList;
import java.util.List;

public class Catalogo {
    private final List<AutoNuova> catalogo = new ArrayList<>();

    public Catalogo(){

    }

    public void add(AutoNuova a){
        catalogo.add(a);
    }

    public AutoNuova getAuto(Marca marca, Modello modello){
        for(AutoNuova auto : catalogo){
            if(auto.getMarca().equals(marca)){
                if(auto.getModello().equals(modello)){
                    return auto;
                }
            }
        }
        return null;
    }
}
