package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class SegreteriaController {

    @FXML private ComboBox<Marca> marca;
    @FXML private ComboBox<String> modello;
    @FXML private CheckBox infot, sensori, fari, sedili, scorta, vetri, interni, ruote, cruise;
    @FXML private Button modificaOption,visualizzaPreventivi,visualizzaMarca,visualizzaSede;
    @FXML private ListView preventiviListView;


    public void xcliente(ActionEvent actionEvent) {

    }

    public void xmarca(ActionEvent actionEvent) {

    }

    public void xsede(ActionEvent actionEvent) {

    }
    public void  modificaOptionals(ActionEvent actionEvent) {

    }

}