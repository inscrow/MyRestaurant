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
     * @param nascita anno di nascita associati al cliente 
     * @param registrazione data di registrazione associati al cliente 
     * @throws IdAlreadyUsed l'id fornito è già stato usato
     */
    public Cliente(String id, int nascita, LocalDate registrazione) throws NoAddClienteException {
        try {
            validaId(id);
            validaNascita(nascita);
            validaRegistrazione(registrazione, nascita);
        } catch (Exception e) {
            throw new NoAddClienteException(e.getMessage());
        }
        this.id = id;
        this.nascita = nascita;
        this.registrazione = registrazione;

        this.ordini = new ArrayList<>();
        listaIds.add(id);
    }

    /**
     * Crea un cliente generando un id unico casuale
     * @param nascita anno di nascita associati al cliente 
     * @param registrazione data di registrazione associati al cliente 
     */
    public Cliente(int nascita, LocalDate registrazione) throws NoAddClienteException {
        this(Cliente.generaId(), nascita, registrazione);
    }

    /**
     * Crea un cliente utilizzando la data di oggi per la registrazione
     * @param id id per il cliente
     * @param nascita anno di nascita del cliente
     */
    public Cliente(String id, int nascita) throws NoAddClienteException {
        this(id, nascita, LocalDate.now());
    }

    /**
     * Crea un cliente generando un id casuale e utilizzando la data di oggi per la registrazione
     * @param nascita anno di nascita del cliente
     */
    public Cliente(int nascita) throws NoAddClienteException {
        this(Cliente.generaId(), nascita, LocalDate.now());
    }

    /**
     * Lancia un'eccezione se l'id passato come input è già stato utilizzato
     * @param id id da controllare
     */
    public void validaId(String id) throws IdAlreadyUsed {
        if (listaIds.contains(id))
            throw new IdAlreadyUsed("L'id inserito è già stato utilizzato");
    }

    /**
     * Controlla che l'anno passato come parametro rientri nei limiti di tempo
     * possibili: tra il 1900 e l'anno corrente
     * @param nascita anno da controllare
     */
    public void validaNascita(int nascita) throws NascitaInvalida {
        if (nascita < 1900)
            throw new NascitaInvalida("Il cliente deve essere nato dal 1900 in avanti");
        if (nascita > LocalDate.now().getYear())
            throw new NascitaInvalida("Il cliente deve già essere nato");
    }

    /**
     * Controlla che la data di registrazione passata come parametro sia
     * valida. In particolare la data di registrazione non può precedere l'anno
     * di nascita e non può superare la data odierna
     * @param reg data di registrazione da controllare
     * @param nascita data di nascita a cui fare riferimento
     */
    public void validaRegistrazione(LocalDate reg, int nascita) throws RegistrazioneInvalida {
        if (reg.compareTo(LocalDate.now()) > 0)
            throw new RegistrazioneInvalida("La data di registrazione non può essere nel futuro");
        if (reg.getYear() < nascita)
            throw new RegistrazioneInvalida("La data di registrazione deve essere successiva alla nascita");
    }

    /**
     * Ritorna l'id associati al cliente 
     * @return id associati al cliente 
     */
    public String getId() {
        return id;
    }

    /**
     * Restituisce l'anno di nascita associati al cliente 
     * @return anno di nascita associati al cliente 
     */
    public int getNascita() {
        return nascita;
    }

    /**
     * Cambia l'anno di nascita associati al cliente 
     * @param nascita nuovo anno di nascita
     */
    public void setNascita(int nascita) {
        this.nascita = nascita;
    }

    /**
     * Restituisce la data di registrazione associati al cliente 
     * @return data di registrazione
     */
    public LocalDate getRegistrazione() {
        return registrazione;
    }

    /**
     * Cambia la data di registrazione associati al cliente 
     * @param registrazione nuova data di registrazione associati al cliente 
     */
    public void setRegistrazione(LocalDate registrazione) {
        this.registrazione = registrazione;
    }
    
    /**
     * Restituisce la lista degli ordini associati al cliente.
     * @return la lista degli ordini
     */
    public List<Ordine> getOrdini() {
        return ordini;
    }

    /**
     * Crea una lista di numero di piatti degli ordini del cliente
     * @return lista con numero di piatti per ogni ordine del cliente
     */
    public ArrayList<Integer> getListNumPiatti() {
        ArrayList<Integer> results = new ArrayList<>();

        for (Ordine ordine : ordini) {
            results.add(ordine.getNumPiatti());
        }
        return results;
    }

    /**
     * Crea una lista di tipi di menù degli ordini del cliente
     * @return lista di tipi di menù per ogni ordine del cliente
     */
    public ArrayList<TipoMenu> getListTipoMenu() {
        ArrayList<TipoMenu> results = new ArrayList<>();

        for (Ordine ordini : ordini) {
            results.add(ordini.getTipoMenu());
        }

        return results;
    }

    /**
     * Restituisce il totale dei piatti ordinati dal cliente.
     *
     * @return il totale dei piatti ordinati
     */
    public int getTotalePiatti() {
        int totale = 0;
        for (Ordine ordine : ordini) {
            totale += ordine.getNumPiatti();
        }
        return totale;
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
     * Restituisce il numero di clienti creati
     * @return numero di clienti creati
     */
    public static int getNumClienti() {
        return listaIds.size();
    }

    /**
     * Aggiunge un ordine alla lista ordini associati al cliente 
     * @param numPiatti numero di piatti nell'ordine
     * @param  Tmenu tipo di menù scelto (carne, pesce, vegetariano, ...)
     * @param data data in cui è stato effettuato l'ordine
     */

    public void addOrdine(int numPiatti,  TipoMenu Tmenu, LocalDate data) throws NoAddOrderException {
        Ordine o = new Ordine(numPiatti,  Tmenu , data);
        ordini.add(o);
    }

    /**
     * Restituisce il numero di ordini associati al cliente 
     * @return numero ordini associati al cliente 
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

    @Override
    public String toString() {
        return "Cliente [id=" + id + ", nascita=" + nascita + ", registrazione=" + registrazione + "]";
    }
}
