package upo20052959.ristorante;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe rappresenta i clienti del ristorante
 */
public class Cliente {
    private final String id;
    private int nascita;
    private LocalDate registrazione;
    private List<Ordine> ordini;
    static private List<String> listaIds;

    // Inizializiamo la lista degli id a una lista vuota
    static {
        listaIds = new ArrayList<>();
    }

    /**
     * Crea un cliente fornendo un id, se è già utilizzato lancia un eccezione
     * @param id id scelto dal cliente
     * @param nascita anno di nascita del cliente
     * @param registrazione data di registrazione del cliente
     * @throws IdAlreadyUsed l'id fornito è già stato usato
     */
    public Cliente(String id, int nascita, LocalDate registrazione) throws IdAlreadyUsed {
        if (listaIds.contains(id))
            throw new IdAlreadyUsed("Id già utilizzato");
        this.id = id;
        this.nascita = nascita;
        this.registrazione = registrazione;

        this.ordini = new ArrayList<>();
        listaIds.add(id);
    }

    /**
     * Crea un cliente generando un id unico casuale
     * @param nascita anno di nascita del cliente
     * @param registrazione data di registrazione del cliente
     */
    public Cliente(int nascita, LocalDate registrazione) throws IdAlreadyUsed {
        this(Cliente.generaId(), nascita, registrazione);
    }

    /**
     * Ritorna l'id del cliente
     * @return id del cliente
     */
    public String getId() {
        return id;
    }

    // NOTE: non aggiungiamo il metodo setId perché non ha senso cambiare l'id
    // di un cliente già esistente

    /**
     * Restituisce l'anno di nascita del cliente
     * @return anno di nascita del cliente
     */
    public int getNascita() {
        return nascita;
    }

    /**
     * Cambia l'anno di nascita del cliente
     * @param nascita nuovo anno di nascita
     */
    public void setNascita(int nascita) {
        this.nascita = nascita;
    }

    /**
     * Restituisce la data di registrazione del cliente
     * @return data di registrazione
     */
    public LocalDate getRegistrazione() {
        return registrazione;
    }

    /**
     * Cambia la data di registrazione del cliente
     * @param registrazione nuova data di registrazione del cliente
     */
    public void setRegistrazione(LocalDate registrazione) {
        this.registrazione = registrazione;
    }

    /**
     * Genera un id casuale unico nel formato "user_" + numero
     * @return id casuale unico
     */
    public static String generaId() {
        SecureRandom rand = new SecureRandom();
        int n;
        String random_id;

        do {
            n = rand.nextInt(9000) + 1000;
            random_id = "user_" + String.format("%04d", n);
        } while (listaIds.contains(random_id));

        return random_id;
    }

    /**
     * Aggiunge un ordine alla lista ordini del cliente
     * @param numPiatti numero di piatti nell'ordine
     * @param tipoMenu tipo di menù scelto (carne, pesce, vegetariano, ...)
     * @param data data in cui è stato effettuato l'ordine
     */
    public void addOrdine(int numPiatti, String tipoMenu, LocalDate data) {
        Ordine o = new Ordine(numPiatti, tipoMenu, data);
        ordini.add(o);
    }

    /**
     * Restituisce il numero di ordini del cliente
     * @return numero ordini del cliente
     */
    public int getNumOrdini() {
        return ordini.size();
    }

    /**
     * Verifica se esiste un ordine in una certa data
     * @param data data in cui cercare l'ordine
     * @return `true` se esiste un ordine per la data, `false` altrimenti
     */
    public boolean containsOrdine(LocalDate data) {
        for (Ordine o : ordini) {
            if (o.getData().equals(data))
                return true;
        }
        return false;
    }

    /**
     * Restituisce un ordine per una certa data
     * @param data data dell'ordine
     * @return ordine trovato oppure `null`
     */
    public Ordine getOrdine(LocalDate data) {
        for (Ordine o : ordini) {
            if (o.getData().equals(data))
                return o;
        }

        return null;
    }

    /**
     * Cancella l'ordine per una certa data, se esiste
     * @param data data dell'ordine da cancellare
     */
    public void deleteOrdine(LocalDate data) {
        if (this.containsOrdine(data)) {
            ordini.remove(getOrdine(data));
        }
    }

    public String toString() {
        return "Cliente [id=" + id + ", nascita=" + nascita + ", registrazione=" + registrazione + "]";
    }
}
