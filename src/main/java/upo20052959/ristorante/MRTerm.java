package upo20052959.ristorante;


import java.time.LocalDate;
import java.util.*;

/**
 * Classe driver dell'applicazione del ristorante
 */
public class MRTerm {
    private static ArrayList<Cliente> clienti;
    private static final Scanner tastiera;

    // Inizializiamo clienti e tastiera in blocco static
    static {
        clienti = new ArrayList<>();
        tastiera = new Scanner(System.in);
    }

    /**
     * Restituisce la lista dei clienti registrati nel sistema.
     *
     * @return la lista dei clienti (Collection di Cliente)
     */
    public static ArrayList<Cliente> getClienti() {
        return clienti;
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
     *
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
                stampaStatisticheNumPiatti();
                break;
            case 6:
                stampaStatisticheTipoMenu();
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
        String id = tastiera.next();
        System.out.println("Anno di nascita del cliente: ");
        int anno = tastiera.nextInt();
        System.out.println("Data di registrazione del cliente: [aaaa-mm-gg]");
        LocalDate registrazione = LocalDate.parse(tastiera.next());

        try {
            MRController.addCliente(id, anno, registrazione);
        } catch (ClienteNonAggiunto e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Chiede l'id di un utente da cercare, e se viene trovato ne stampa i dati
     */
    public static void findCliente() {
        System.out.println("Id del cliente da cercare: ");
        String id = tastiera.next();

        Cliente c = MRController.findCliente(id);
        if (c != null) {
            System.out.println(c);
        } else {
            System.out.println("Cliente non trovato");
        }
    }

    /**
     * Chiede all'utente un'età minima e una massima per i clienti da cercare, e se ne trova stampa i loro dati
     */
    public static void findClienteEta() {
        System.out.println("Età minima dei clienti da cercare: ");
        int min = tastiera.nextInt();
        System.out.println("Età massima dei clienti da cercare: ");
        int max = tastiera.nextInt();

        List<Cliente> ricerca = MRController.findClienteEta(min, max);
        if (ricerca.isEmpty()) {
            System.out.println("Nessun cliente nel range di età selezionato");
        } else {
            for (Cliente c : ricerca) {
                System.out.println(c);
            }
        }
    }

    /**
     * Questo metodo chiede i dati relativi a una nuova ordinazione e crea l'ordine tramite `addOrdineCM`
     */
    public static void addOrdine() {
        System.out.println("Id del cliente che sta ordinando: ");
        String id = tastiera.next();
        System.out.println("Numero di piatti ordinati: ");
        int numPiatti = tastiera.nextInt();
        System.out.println("Tipo di menù disponibili:\n" + TipoMenu.tipiMenu() + "Inserisci il numero del menu scelto: ");
        int codiceMenu = tastiera.nextInt();
        TipoMenu tipo = TipoMenu.tipoMenu(codiceMenu);
        System.out.println("Data dell'ordine: ");
        LocalDate data = LocalDate.parse(tastiera.nextLine());

        try {
            MRController.addOrdine(id, numPiatti, tipo, data);
        } catch (OrdineNonAggiunto e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Stampa il numero di ordini per ogni tipo di menù
     */
    public static void stampaStatisticheTipoMenu() {
        Map<TipoMenu, Integer> map = MRController.statisticheTipoMenu();
        for (TipoMenu tm : map.keySet()) {
            System.out.println(tm.nome() + ": " + map.get(tm));
        }
    }
    /**
     * Stampa le statistiche del numero di piatti (numero minimo, numero massimo e media)
     */
    private static void stampaStatisticheNumPiatti() {
        IntSummaryStatistics stats = MRController.statisticheNumeroPiatti();
        if (stats.getCount() == 0) {
            System.out.println("Non è stato ancora caricato nessun ordine");
            return;
        }

        System.out.println("Il numero minimo di piatti in un ordine è: " + stats.getMin());
        System.out.println("Il numero massimo di piatti in un ordine è: " + stats.getMax());
        System.out.println("Il numero medio di piatti in un ordine è: " + stats.getAverage());
    }
}
