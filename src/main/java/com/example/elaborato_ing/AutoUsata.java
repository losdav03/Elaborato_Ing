package com.example.elaborato_ing;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AutoUsata extends Auto {
    private final String colore;
    private Sede sede;
    private Cliente cliente;

    public AutoUsata(Cliente cliente, Marca marca, String modello, double altezza, double lunghezza, double larghezza, double peso, double volumeBagagliaio, Motore motore, String colore, Sede sede) {
        super(marca, modello, altezza, lunghezza, larghezza, peso, volumeBagagliaio, motore, null);
        this.cliente = cliente;
        this.colore = colore;
        this.sede = sede;
    }

    public void caricaImmaginiAutoUsata() {
        for (int i = 1; i <= 3; i++) {
            String path = "/com/example/elaborato_ing/imagesAutoUsate/" + cliente.getEmail() + super.getMarca().toString().toLowerCase() + super.getModello().toLowerCase() + colore.toLowerCase() + i + ".png";
            System.out.println("QUA " + path);
            super.getImmagini().add(path);
        }
    }


    public String getColore() {
        return colore;
    }
}
