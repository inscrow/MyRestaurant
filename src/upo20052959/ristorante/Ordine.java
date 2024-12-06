package upo20052959.ristorante;

import java.time.LocalDate;

public class Ordine {
    private int numPiatti;
    private String tipoMenu;
    private final LocalDate data;

    Ordine(int numPiatti, String tipoMenu, LocalDate data) {
        this.numPiatti = numPiatti;
        this.tipoMenu = tipoMenu;
        this.data = data;
    }

    public int getNumPiatti() {
        return numPiatti;
    }

    public void setNumPiatti(int numPiatti) {
        this.numPiatti = numPiatti;
    }

    public String getTipoMenu() {
        return tipoMenu;
    }

    public void setTipoMenu(String tipoMenu) {
        this.tipoMenu = tipoMenu;
    }

    public LocalDate getData() {
        return data;
    }

    public boolean equals(Ordine ordine) {
        return this.data.equals(ordine.data) && this.numPiatti == ordine.numPiatti && this.tipoMenu.equals(ordine.tipoMenu);
    }
}
