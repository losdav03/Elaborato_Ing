package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ValutaController {
    @FXML
    private TextField prezzo;


    Model model = new Model();
    public void Valuta(ActionEvent actionEvent) {

        model.valuta(prezzo.getText());
    }
}
