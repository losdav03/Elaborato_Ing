package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class DipendenteController {

    Model model = new Model();

    public void avvisaCliente(ActionEvent event) throws IOException {
        model.OpenCloseFXML("FXML/Avvisa.fxml", event);
    }

    public void valutaUsato(ActionEvent event) throws IOException {
        model.OpenCloseFXML("FXML/ValutaUsato.fxml", event);
    }
}
