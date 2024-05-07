package com.example.elaborato_ing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class UsatoController {

    @FXML
    private ImageView imgUsato;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void caricaFoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Scegli un'immagine");
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            imgUsato.setImage(image);
        }
    }

    public void addOption(ActionEvent actionEvent) {
    }
}
