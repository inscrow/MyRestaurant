package upo20052959.ristorante;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.time.LocalDate;
import java.util.*;

public class MyResturantController {
    private static ArrayList<Cliente> clienti;

    @FXML
    private Label welcomeText;

    static {
        clienti = new ArrayList<>();
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
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
        if (id.isEmpty()) {
            try {
                c = new Cliente(nascita, reg);
            } catch (IdAlreadyUsed e) {
                throw new ClienteNonAggiunto(e.getMessage());
            }
        } else {
            try {
                c = new Cliente(id, nascita, reg);
            } catch (IdAlreadyUsed e) {
                throw new ClienteNonAggiunto(e.getMessage());
            }
        }
        clienti.add(c);
    }

    /**
     * Cerca un cliente fornendo un id
     *
     * @param id id del cliente da cercare
     * @return il cliente con l'id cercato, oppure `null` se non viene trovato
     */
    public static Cliente findCliente(String id) {
        for (Cliente c : clienti) {
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
    public static ArrayList<Cliente> findClienteEta(int min, int max) {
        ArrayList<Cliente> results = new ArrayList<>();
        int anno = LocalDate.now().getYear();
        for (Cliente c : clienti) {
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
     */
    public static void addOrdine(String id, int numPiatti, TipoMenu tipoMenu) {
        Cliente c = findCliente(id);
        if (null != c) {
            c.addOrdine(numPiatti, tipoMenu, LocalDate.now());
        }
    }

    /**
     * Stampa il numero minimo, il numero massimo e il numero medio di piatti per un ordine, leggendo i dati direttamente dalla lista di numeri di piatti
     */
    public static void statisticheNumeroPiatti() {
        IntSummaryStatistics stats = clienti.stream().flatMapToInt(c -> c.getListNumPiatti().stream().mapToInt(Integer::intValue)).summaryStatistics();

        if (stats.getCount() == 0) {
            System.out.println("Non è stato ancora caricato nessun ordine");
            return;
        }

        System.out.println("Il numero minimo di piatti in un ordine è: " + stats.getMin());
        System.out.println("Il numero massimo di piatti in un ordine è: " + stats.getMax());
        System.out.println("Il numero medio di piatti in un ordine è: " + stats.getAverage());
    }

    /**
     * Stampare il numero di ordini per ciascun tipo di menù dalla lista di tipi di menu ordinati
     */
    public static void statisticheTipoMenu() {
        Map<TipoMenu, Integer> map = new HashMap<>();
        for (Cliente c : clienti) {
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
