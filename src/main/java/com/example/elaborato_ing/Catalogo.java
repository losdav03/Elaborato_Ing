package com.example.elaborato_ing;

import java.util.ArrayList;
import java.util.List;

public class Catalogo {
    private final List<Auto> catalogo = new ArrayList<>();

    public Catalogo(){

    }

    public void add(Auto a){
        catalogo.add(a);
    }

    public Auto getAuto(Marca marca,Modello modello){
        for(Auto auto : catalogo){
            if(auto.getMarca().equals(marca)){
                if(auto.getModello().equals(modello)){
                    return auto;
                }
            }
        }
        return null;
    }
}
