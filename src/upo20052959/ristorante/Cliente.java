package upo20052959.ristorante;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private final String id;
    private int nascita;
    private LocalDate registrazione;
    private List<Ordine> ordini;
    static private List<String> listaIds;

    static {
        listaIds = new ArrayList<>();
    }

    Cliente(String id, int nascita, LocalDate registrazione) {
        this.id = id;
        this.nascita = nascita;
        this.registrazione = registrazione;

        this.ordini = new ArrayList<>();
        listaIds.add(id);
    }

    Cliente(int nascita, LocalDate registrazione) {
        this(Cliente.generaId(), nascita, registrazione);
    }

    public String getId() {
        return id;
    }

    public int getNascita() {
        return nascita;
    }

    public void setNascita(int nascita) {
        this.nascita = nascita;
    }

    public LocalDate getRegistrazione() {
        return registrazione;
    }

    public void setRegistrazione(LocalDate registrazione) {
        this.registrazione = registrazione;
    }

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

    public void addOrdine(int numPiatti, String tipoMenu, LocalDate data) {
        Ordine o = new Ordine(numPiatti, tipoMenu, data);
        ordini.add(o);
    }

    public int getNumOrdini() {
        return ordini.size();
    }

    public boolean containsOrdine(LocalDate data) {
        for (Ordine o : ordini) {
            if (o.getData().equals(data))
                return true;
        }
        return false;
    }

    public Ordine getOrdine(LocalDate data) {
        for (Ordine o : ordini) {
            if (o.getData().equals(data))
                return o;
        }

        return null;
    }

    public void deleteOrdine(LocalDate data) {
        if (this.containsOrdine(data)) {
            ordini.remove(getOrdine(data));
        }
    }

    public String toString() {
        return "Cliente [id=" + id + ", nascita=" + nascita + ", registrazione=" + registrazione + "]";
    }
}
