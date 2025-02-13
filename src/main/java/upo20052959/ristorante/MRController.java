package upo20052959.ristorante;

import java.time.LocalDate;
import java.util.*;

/**
 * Controller principale dell'applicazione ristorante.
 * Gestisce le interazioni dell'utente con l'interfaccia grafica.
 */
public class MRController {
    private static final List<Cliente> clientiList = new ArrayList<>();

    /**
     * Ritorna la lista di clienti del ristorante
     * @return lista di clienti
     */
    public static List<Cliente> getClientiList() {
        return clientiList;
    }

    /**
     * Ritorna la lista degli ordini di tutti i clienti
     * @return lista di ordini
     */
    public static List<Ordine> getOrdiniList() {
        List<Ordine> ordiniList = new ArrayList<>();
        for (Cliente c : clientiList) {
            ordiniList.addAll(c.getOrdini());
        }
        return ordiniList;
    }

    /**
     * Metodo che gestisce l'input ricevuto dal `addCliente` per aggiungere un nuovo cliente alla lista
     *
     * @param id      id del nuovo cliente (può essere vuoto)
     * @param nascita anno di nascita del nuovo cliente
     * @param reg     data di registrazione del nuovo cliente
     * @throws ClienteNonAggiunto il cliente non è stato aggiunto per qualche motivo
     */
    public static void addCliente(String id, int nascita, LocalDate reg) throws ClienteNonAggiunto {
        Cliente c;
        // Se l'id inserito è vuoto, usa il costruttore che genera l'id randomicamente
        try {
            if (id.isEmpty()) {
                if (reg == null) {
                    c = new Cliente(nascita);
                } else {
                    c = new Cliente(nascita, reg);
                }
            } else {
                if (reg == null) {
                    c = new Cliente(id, nascita);
                } else {
                    c = new Cliente(id, nascita, reg);
                }
            }
        } catch (NoAddClienteException e) {
            throw new ClienteNonAggiunto(e.getMessage());
        }

        clientiList.add(c);
    }

    /**
     * Cerca un cliente fornendo un id
     *
     * @param id id del cliente da cercare
     * @return il cliente con l'id cercato, oppure `null` se non viene trovato
     */
    public static Cliente findCliente(String id) {
        for (Cliente c : clientiList) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Trova una lista di clienti con età compresa tra un valore minimo e un valore massimo
     *
     * @param min età minima dei clienti da cercare
     * @param max età massima dei clienti da cercare
     * @return lista di clienti con età compresa tra `min` e `max`
     */
    public static List<Cliente> findClienteEta(int min, int max) {
        List<Cliente> results = new ArrayList<>();
        int anno = LocalDate.now().getYear();
        for (Cliente c : clientiList) {
            int eta = anno - c.getNascita();
            if (min <= eta && eta <= max) {
                results.add(c);
            }
        }
        return results;
    }

    /**
     * Aggiunge un ordine al cliente con id selezionato
     *
     * @param id        id del cliente che ha ordinato
     * @param numPiatti numero di piatti nel pasto
     * @param tipoMenu  tipo di menù ordinato
     * @param date data in cui è stato effettuato l'ordine
     */
    public static void addOrdine(String id, int numPiatti, TipoMenu tipoMenu, LocalDate date) throws OrdineNonAggiunto {
        Cliente c = findCliente(id);
        if (null == c) {
            throw new OrdineNonAggiunto("Id del cliente non trovato");
        }
        try {
            c.addOrdine(numPiatti, tipoMenu, date);
        } catch (NoAddOrderException e) {
            throw new OrdineNonAggiunto(e.getMessage());
        }
    }

    /**
     * Stampa il numero minimo, il numero massimo e il numero medio di piatti
     */
    public static IntSummaryStatistics statisticheNumeroPiattiLista() {
        return clientiList.stream().flatMapToInt(c ->
                        c.getListNumPiatti().stream()
                                .mapToInt(Integer::intValue))
                .summaryStatistics();
    }

    /**
     * Stampare il numero di ordini per ciascun tipo di menù dalla lista di tipi di menu ordinati
     */
    public static void statisticheTipoMenuLista() {
        Map<TipoMenu, Integer> map = new HashMap<>();
        for (Cliente c : clientiList) {
            for (TipoMenu tm : c.getListTipoMenu()) {
                map.merge(tm, 1, Integer::sum);
            }
        }

        //eccezione
        if (!map.isEmpty()) {
            map.forEach((K, n) -> System.out.println(K + ": " + n));
        } else {
            System.out.println("non sono ancora state registrate ordinazioni");
        }
    }
}
