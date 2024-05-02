package com.example.elaborato_ing;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Optionals {

    private List<Optional> listaOp = new ArrayList<>();

    public Optionals(List<Optional> listaOp) {
        this.listaOp = listaOp;
    }
    public List<Optional> getListaOp() {
        return listaOp;
    }
    public void setListaOp(List<Optional> listaOp) {
        this.listaOp = listaOp;
    }
    public void addOp(Optional op) {
        listaOp.add(op);
    }
    public void removeOp(Optional op) {
        listaOp.remove(op);
    }
}

