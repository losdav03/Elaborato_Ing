package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class DipendenteController {

    Model model = new Model();

    @FXML
    private Button valutaBtn,avvisaBtn;

    public void avvisaCliente(ActionEvent actionEvent) {
        model.OpenCloseFXML("FXML/Avvisa.fxml",avvisaBtn);
    }

    public void valutaUsato(ActionEvent actionEvent) {
        model.OpenCloseFXML("FXML/ValutaUsato.fxml",valutaBtn);
        }
}
