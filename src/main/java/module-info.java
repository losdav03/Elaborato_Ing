module com.example.elaborato_ing {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires itextpdf;
    //  requires pdfbox.app;


    opens com.example.elaborato_ing to javafx.fxml;
    exports com.example.elaborato_ing;
}