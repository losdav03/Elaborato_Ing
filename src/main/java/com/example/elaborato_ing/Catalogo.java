package com.example.elaborato_ing;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Catalogo {
    private final List<Auto> catalogo = new ArrayList<>();

    public Catalogo(Auto... auto) {
        for (Auto a : auto)
            catalogo.add(a);
    }
    public Catalogo(){

    }
    public Catalogo(Auto auto) {

        catalogo.add(auto);
    }

}
