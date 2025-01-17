package upo20052959.ristorante;

import java.time.LocalDate;

/**
 * Classe che rappresenta gli ordini dei clienti di un ristorante
 */
public class Ordine {
    private int numPiatti;
    private TipoMenu tipoMenu;
    private final LocalDate data;

    /**
     * Crea un ordine    con numero di piatti ordinati, tipo di menu e data dell'ordine
     * @param numPiatti numero di piatti ordinati
     * @param tipo tipo di menu
     * @param data data dell'ordine
     */
    public Ordine(int numPiatti, TipoMenu  tipo, LocalDate data) {
        this.numPiatti = numPiatti;
        this.tipoMenu = tipo; // impostiamo sempre il tipo ordine in lower case
        this.data = data;
    }

    /**
     * Restituisce il numero di piatti ordinati
     * @return numero di piatti nell'ordine
     */
    public int getNumPiatti() {
        return numPiatti;
    }

    /**
     * Cambia il numero di piatti dell'ordine
     * @param numPiatti nuovo numero di piatti dell'ordine
     */
    public void setNumPiatti(int numPiatti) {
        this.numPiatti = numPiatti;
    }

    /**
     * Restituisce una stringa che descrive il tipo di menu
     * @return tipo di menu ordinato
     */
    public TipoMenu getTipoMenu() {
        return tipoMenu;
    }


    /**
     * Cambia il tipo di menu ordinato
     * @param String nuovo tipo di menu dell'ordine
     */
    public void setString(TipoMenu String) {
        this.tipoMenu = String;
    }

    /**
     * Restituisce la data di ordinazione del menu
     * @return data in cui è stato effettuato l'ordine
     */
    public LocalDate getData() {
        return data;
    }

    @Override
    public String toString() {
        return numPiatti + " " + tipoMenu + " " + data;
    }

    /**
     * Verifica se due ordini sono uguali tra loro
     * @param obj oggetto da verificare
     * @return `true` se i due ordini sono uguali, `false` altrimenti
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Ordine) {
            Ordine ordine = (Ordine) obj;
            return this.data.equals(ordine.data) && this.numPiatti == ordine.numPiatti && this.tipoMenu.equals(ordine.tipoMenu);
        } else {
            return false;
        }
    }
}
