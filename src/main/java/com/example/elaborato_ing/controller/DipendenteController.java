package com.example.elaborato_ing.controller;

import com.example.elaborato_ing.model.Model;
import javafx.event.ActionEvent;

import java.io.IOException;

public class DipendenteController {

    private Model model = Model.getInstance();


    public void avvisaCliente(ActionEvent event) throws IOException {
        model.openCloseFXML("/com/example/elaborato_ing/viewDipendente/Avvisa.fxml", event);
    }

    public void valutaUsato(ActionEvent event) throws IOException {
        model.openCloseFXML("/com/example/elaborato_ing/viewDipendente/ValutaUsato.fxml", event);
    }
}
