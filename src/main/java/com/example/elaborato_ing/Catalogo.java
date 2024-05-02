package com.example.elaborato_ing;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Catalogo {
    private final List<Auto> catalogo = new ArrayList<>();

    public Catalogo(){

    }

    public void add(Auto a){
        catalogo.add(a);
    }

}
