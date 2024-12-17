package upo20052959.ristorante;

import java.time.LocalDate;
import java.util.*;

/**
 * Classe driver dell'applicazione del ristorante
 */
public class MioRistorante {
    private static ArrayList<Cliente> clienti;
    private final static Scanner tastiera;

    // Inizializiamo clienti e tastiera in blocco static
    static {
        clienti = new ArrayList<>();
        tastiera = new Scanner(System.in);
    }

    /**
     * Metodo principale dell'applicazione, stampa il menù principale ed esegue le scelte in loop
     */
    public static void main(String[] args) {
        int scelta;

        do {
            menu();
            scelta = tastiera.nextInt();
            esegui(scelta);
        } while (scelta != 100);
    }
    
    /**
     * Menù principale dell'applicazione: stampa le possibili azioni dell'utente
     */
    public static void menu() {
        System.out.println("Scegli cosa vuoi fare: ");
        System.out.println("1   - Aggiungi un nuovo cliente");
        System.out.println("2   - Cerca un cliente per id");
        System.out.println("3   - Cerca un cliente per età");
        System.out.println("4   - Aggiungi un'ordinazione");
        System.out.println("5   - Stampa le statistiche sul numero di piatti ordinati");
        System.out.println("6   - Stampa le statistiche sul tipo di menù ordinato");
        System.out.println("100 - Esci dall'applicazione");
        System.out.println(">");
    }

    /**
     * Metodo che esegue un'azione diversa in base al codice inserito dall'utente
     * @param scelta è il codice scelto dall'utente
     */
    public static void esegui(int scelta) {
        switch (scelta) {
            case 1:
                addCliente();
                break;
            case 2:
                findCliente();
                break;
            case 3:
                findClienteEta();
                break;
            case 4:
                addOrdine();
                break;
            case 5:
                 statisticheNumeroPiatti();
                 break;
             case 6:
                 statisticheTipoMenu();
                 break;
            case 100:
                System.out.println("L'applicazione si sta chiudendo");
                break;
            default:
                System.out.println("Scelta non valida");
        }
    }

    /**
     * Metodo che chiede dati di un cliente da aggiungere e richiama `addClienteCM` per crearlo e aggiungerlo
     */
    public static void addCliente() {
        System.out.println("Id del nuovo cliente: ");
        tastiera.skip("\n"); // potrebbe esserci un "\n" nel buffer di input, in questo modo lo saltiamo
        String id = tastiera.nextLine();
        System.out.println("Anno di nascita del cliente: ");
        int anno = tastiera.nextInt();
        System.out.println("Data di registrazione del cliente: [aaaa-mm-gg]");
        LocalDate registrazione = LocalDate.parse(tastiera.next());

        try {
            addClienteCM(id, anno, registrazione);
        } catch (ClienteNonAggiunto e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metodo che gestisce l'input ricevuto dal `addCliente` per aggiungere un nuovo cliente alla lista
     * @param id id del nuovo cliente (può essere vuoto)
     * @param nascita anno di nascita del nuovo cliente
     * @param reg data di registrazione del nuovo cliente
     * @throws ClienteNonAggiunto il cliente non è stato aggiunto per qualche motivo
     */
    public static void addClienteCM(String id, int nascita, LocalDate reg) throws ClienteNonAggiunto {
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
     * Chiede l'id di un utente da cercare, e se viene trovato ne stampa i dati
     */
    public static void findCliente() {
        System.out.println("Id del cliente da cercare: ");
        String id = tastiera.next();

        Cliente c = findClienteCM(id);
        if (c != null) {
            System.out.println(c);
        } else {
            System.out.println("Cliente non trovato");
        }
    }

    /**
     * Cerca un cliente fornendo un id
     * @param id id del cliente da cercare
     * @return il cliente con l'id cercato, oppure `null` se non viene trovato
     */
    public static Cliente findClienteCM(String id) {
        for (Cliente c : clienti) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Chiede all'utente un'età minima e una massima per i clienti da cercare, e se ne trova stampa i loro dati
     */
    public static void findClienteEta() {
        System.out.println("Età minima dei clienti da cercare: ");
        int min = tastiera.nextInt();
        System.out.println("Età massima dei clienti da cercare: ");
        int max = tastiera.nextInt();

        ArrayList<Cliente> ricerca = findClienteEtaCM(min, max);
        if (ricerca.isEmpty()) {
            System.out.println("Nessun cliente nel range di età selezionato");
        } else {
            for (Cliente c : ricerca) {
                System.out.println(c);
            }
        }
    }

    /**
     * Trova una lista di clienti con età compresa tra un valore minimo e un valore massimo
     * @param min età minima dei clienti da cercare
     * @param max età massima dei clienti da cercare
     * @return lista di clienti con età compresa tra `min` e `max`
     */
    public static ArrayList<Cliente> findClienteEtaCM(int min, int max) {
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
     * Questo metodo chiede i dati relativi a una nuova ordinazione e crea l'ordine tramite `addOrdineCM`
     */
    public static void addOrdine() {
        System.out.println("Id del cliente che sta ordinando: ");
        String id = tastiera.next();
        System.out.println("Numero di piatti ordinati: ");
        int numPiatti = tastiera.nextInt();
        System.out.println("Tipo di menù scelto: ");
        String tipo = tastiera.next();

        addOrdineCM(id, numPiatti, tipo);
    }

    /**
     * Aggiunge un ordine al cliente con id selezionato
     * @param id id del cliente che ha ordinato
     * @param numPiatti numero di piatti nel pasto
     * @param tipoMenu tipo di menù ordinato
     */
    public static void addOrdineCM(String id, int numPiatti, String tipoMenu) {
        Cliente c = findClienteCM(id);
        if (null != c) {
            c.addOrdine(numPiatti, tipoMenu, LocalDate.now());
        }
    }

    /**
     * Stampa il numero minimo, il numero massimo e il numero medio di piatti per un ordine
     */
    public static void statisticheNumeroPiatti() {
        IntSummaryStatistics stats = clienti.stream()
                        .flatMapToInt(c -> c.getListNumPiatti().stream().mapToInt(Integer::intValue))
                        .summaryStatistics();

        if (stats.getCount() == 0) {
            System.out.println("Non è stato ancora caricato nessun ordine");
            return;
        }

        System.out.println("Il numero minimo di piatti in un ordine è: " + stats.getMin());
        System.out.println("Il numero massimo di piatti in un ordine è: " + stats.getMax());
        System.out.println("Il numero medio di piatti in un ordine è: " + stats.getAverage());

    }

    /**
     * Stampare il numero di ordini per ciascun tipo di menù
     */
    public static void statisticheTipoMenu() {
        Map<String, Integer> map = new HashMap<>();
        for (Cliente c : clienti) {
            for (String tm : c.getListTipoMenu()) {
                map.merge(tm, 1, Integer::sum);
            }
        }

        if (!map.isEmpty()) {
            map.forEach((K,n) -> System.out.println(K + ": " + n));
        } else {
            System.out.println("non sono ancora state registrate ordinazioni");
        }
    }
}
