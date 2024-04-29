package com.example.elaborato_ing;

import javafx.scene.image.Image;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class Catalogo {
    private final SortedSet<Auto> catalogo = new TreeSet<Auto>();

    public Catalogo(Auto... auto){
        for (Auto a: auto)
            catalogo.add(a);
    }
    public Catalogo(Auto auto) {
            catalogo.add(auto);
    }
}
