package com.example.elaborato_ing.model;

import com.example.elaborato_ing.utils.Dipendente;
import com.example.elaborato_ing.utils.Marca;
import com.example.elaborato_ing.utils.Preventivo;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    private final Model modelTest = new Model();
    @Test
    void autenticato() throws IOException {
        // Dovrebbe restituire 2 se il cliente si logga correttamente con user e pass corretta
        int res = modelTest.autenticato("utente", "pass");
        assertEquals(2,res);
    }

   @Test
    void utenteEsiste() throws IOException {
        boolean res = modelTest.utenteEsiste("utente");
        assertTrue(res);
   }

   @Test
    void setDipendente(){
       Preventivo prev = new Preventivo();
       Dipendente dip = new Dipendente();
       dip.setIdDipendente(123);
        String id = prev.setDipendente(dip);
        assertEquals("231",id);
   }
}
