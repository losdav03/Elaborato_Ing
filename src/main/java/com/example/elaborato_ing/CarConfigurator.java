package com.example.elaborato_ing;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CarConfigurator extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Configuratore Auto");

        // Creazione dei componenti UI
        Label modelLabel = new Label("Modello:");
        ComboBox<String> modelComboBox = new ComboBox<>();
        modelComboBox.getItems().addAll("Modello 1", "Modello 2", "Modello 3"); // Aggiungere i modelli disponibili

        Label optionsLabel = new Label("Optional:");
        CheckBox leatherInteriorCheckBox = new CheckBox("Interni in Pelle");
        CheckBox spareWheelCheckBox = new CheckBox("Ruota di Scorta");
        // Aggiungere gli altri optional disponibili

        Button calculatePriceButton = new Button("Calcola Prezzo");
        Label totalPriceLabel = new Label("Prezzo Totale: ");

        // Layout della schermata
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(modelLabel, 0, 0);
        grid.add(modelComboBox, 1, 0);
        grid.add(optionsLabel, 0, 1);
        grid.add(leatherInteriorCheckBox, 1, 1);
        grid.add(spareWheelCheckBox, 1, 2);
        // Aggiungere gli altri optional disponibili
        grid.add(calculatePriceButton, 0, 3);
        grid.add(totalPriceLabel, 1, 3);

        // Evento per calcolare il prezzo totale
        calculatePriceButton.setOnAction(event -> {
            double totalPrice = 0;

            // Calcolo del prezzo in base ai modelli e agli optional selezionati
            String selectedModel = modelComboBox.getValue();
            if (selectedModel != null) {
                // Calcola il prezzo del modello
                totalPrice += calculateModelPrice(selectedModel);

                // Aggiungi il prezzo degli optional selezionati
                if (leatherInteriorCheckBox.isSelected()) {
                    totalPrice += calculateOptionPrice("Interni in Pelle");
                }
                if (spareWheelCheckBox.isSelected()) {
                    totalPrice += calculateOptionPrice("Ruota di Scorta");
                }
                // Aggiungere il prezzo degli altri optional selezionati
            }

            // Aggiorna l'etichetta del prezzo totale
            totalPriceLabel.setText("Prezzo Totale: " + totalPrice);
        });

        Scene scene = new Scene(grid, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Metodo per calcolare il prezzo del modello selezionato
    private double calculateModelPrice(String modelName) {
        // Implementazione del calcolo del prezzo in base al modello
        // Questo è un esempio, sostituire con la logica reale
        return switch (modelName) {
            case "Modello 1" -> 20000;
            case "Modello 2" -> 25000;
            case "Modello 3" -> 30000;
            default -> 0; // Modello non valido
        };
    }

    // Metodo per calcolare il prezzo dell'optional selezionato
    private double calculateOptionPrice(String optionName) {
        // Implementazione del calcolo del prezzo in base all'optional
        // Questo è un esempio, sostituire con la logica reale
        return switch (optionName) {
            case "Interni in Pelle" -> 1500;
            case "Ruota di Scorta" -> 200;
            // Aggiungere il prezzo degli altri optional disponibili
            default -> 0; // Optional non valido
        };
    }

    public static void main(String[] args) {
        launch(args);
    }
}

