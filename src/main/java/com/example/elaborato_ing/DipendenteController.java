package com.example.elaborato_ing;

import javafx.event.ActionEvent;

import java.io.IOException;

public class DipendenteController {

    private Model model = Model.getInstance();


    public void avvisaCliente(ActionEvent event) throws IOException {
        model.openCloseFXML("FXML/Avvisa.fxml", event);
    }

    public void valutaUsato(ActionEvent event) throws IOException {
        model.openCloseFXML("FXML/ValutaUsato.fxml", event);
    }
}
