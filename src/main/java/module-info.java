module com.example.elaborato_ing {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires itextpdf;


    opens com.example.elaborato_ing to javafx.fxml;
    exports com.example.elaborato_ing;
    exports com.example.elaborato_ing.controller;
    opens com.example.elaborato_ing.controller to javafx.fxml;
    exports com.example.elaborato_ing.model;
    opens com.example.elaborato_ing.model to javafx.fxml;
    exports com.example.elaborato_ing.utils;
    opens com.example.elaborato_ing.utils to javafx.fxml;
}