package com.example.elaborato_ing;

import java.util.List;
import java.util.Objects;

public class AutoNuova extends Auto {
    private final int prezzo;
    private final String sconto;
    private List<String> colori;
    private List<String> listaOp;

    public AutoNuova(Marca marca, String modello, double altezza, double lunghezza, double larghezza, double peso, double volumeBagagliaio, Motore motore, int prezzo, List<String> colori, String sconto, List<String> listaOp) {
        super(marca, modello, altezza, lunghezza, larghezza, peso, volumeBagagliaio, motore);
        this.colori = colori;
        this.sconto = sconto;
        this.prezzo = prezzo;
        this.listaOp = listaOp;
        caricaImmagini();
    }

    public String getImmagine(String colore, int vista) {
        for(String imgPath : super.getImmagini()){
            if(imgPath.contains(colore.toLowerCase()) && imgPath.contains(String.valueOf(vista)))
                return imgPath;
        }
        return null;
    }

    public void caricaImmagini() {
        for (String c : colori) {
            for (int i = 1; i <= 3; i++) {
                String path = "/com/example/elaborato_ing/images/" + super.getMarca().toString().toLowerCase() + super.getModello().toLowerCase() + c.toLowerCase() + i + ".png";
                super.getImmagini().add(path);
            }

        }
    }


    public boolean getListaOp(OP optional) {
        if (listaOp.contains(optional.toString().trim())) {
            return false;
        }
        return true;
    }

    public void setOptional(boolean infot, boolean sensori, boolean fari, boolean sedili, boolean scorta, boolean vetri, boolean interni, boolean ruote, boolean cruise) {
        if (infot) {
            setOptional(OP.infotainment);
        }
        if (sensori) {
            setOptional(OP.SensoriParcheggio);
        }
        if (fari) {
            setOptional(OP.FariFullLED);
        }
        if (sedili) {
            setOptional(OP.SediliRiscaldati);
        }
        if (scorta) {
            setOptional(OP.RuotaDiScorta);
        }
        if (vetri) {
            setOptional(OP.VetriOscurati);
        }
        if (interni) {
            setOptional(OP.InterniInPelle);
        }
        if (ruote) {
            setOptional(OP.RuoteGrandi);
        }
        if (cruise) {
            setOptional(OP.CruiseControl);
        }
    }

    public String getSconto() {
        return sconto;
    }

    public List<String> getColori() {
        return colori;
    }

    public int getPrezzo() {
        return prezzo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AutoNuova autoNuova = (AutoNuova) o;
        return Objects.equals(sconto, autoNuova.sconto) && Objects.equals(colori, autoNuova.colori);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sconto, colori);
    }
}
