package upo20052959.ristorante;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Test della classe MioRistorante
 */
public class MioRistoranteTest {
    /**
     * Funzione di utilità per testare se un cliente ha i valori richiesti
     * @param c cliente da controllare
     * @param id id atteso
     * @param nascita anno di nascita attesa
     * @param reg data di registrazione attesa
     * @return `true` se i valori sono uguali a quelli attesi, `false` altrimenti
     */
    private boolean clienteEquals(Cliente c, String id, int nascita, LocalDate reg) {
        return c.getId().equals(id) && c.getNascita() == nascita && c.getRegistrazione().equals(reg);
    }

    @Test
    void addClienteConIdTest() {
        String id = "TestMR1";
        int nascita = 1998;
        LocalDate reg = LocalDate.of(2023, 11, 5);
        int numClienti = Cliente.getNumClienti();
        Assertions.assertDoesNotThrow(() -> MioRistorante.addClienteCM(id, nascita, reg));
        Assertions.assertEquals(numClienti+1, Cliente.getNumClienti());
    }

    @Test
    void findClienteConIdTest() {
        String id = "TestMR2";
        int nascita = 1998;
        LocalDate reg = LocalDate.of(2023, 11, 5);
        Assertions.assertDoesNotThrow(() -> {
            MioRistorante.addClienteCM("", 1998, LocalDate.of(2023, 11, 5));
            MioRistorante.addClienteCM(id, nascita, reg);
            MioRistorante.addClienteCM("", 2000, LocalDate.now());
        });
        Cliente c = MioRistorante.findClienteCM(id);
        Assertions.assertNotNull(c);
        Assertions.assertTrue(clienteEquals(c, id, nascita, reg));
    }

    // Per testare se la lista di clienti è quella che ci aspettiamo, creiamo un po' di valori per id, nascita e
    // registrazione, inseriamo questi clienti (in mezzo ad altri con id casuale) e infine controlliamo se nella lista
    // risultante otteniamo i valori attesi
    @Test
    void findClienteConEtaTest() {
        String[] id = { "TestEta1", "TestEta2", "TestEta3" };
        int[] nascita = { 1400, 1420, 1340 };
        LocalDate[] reg = { LocalDate.of(2020, 3, 7), LocalDate.of(2021, 12, 8), LocalDate.of(2022, 10, 20) };
        Assertions.assertDoesNotThrow(() -> {
            MioRistorante.addClienteCM("", 2000, LocalDate.now());
            MioRistorante.addClienteCM(id[0], nascita[0], reg[0]);
            MioRistorante.addClienteCM(id[1], nascita[1], reg[1]);
            MioRistorante.addClienteCM("", 1900, LocalDate.now());
            MioRistorante.addClienteCM(id[2], nascita[2], reg[2]);
        });
        ArrayList<Cliente> clist = MioRistorante.findClienteEtaCM(600, 700);
        Assertions.assertNotEquals(clist.size(), 0);
        Assertions.assertTrue(clienteEquals(clist.get(0), id[0], nascita[0], reg[0]));
        Assertions.assertTrue(clienteEquals(clist.get(1), id[1], nascita[1], reg[1]));
        Assertions.assertTrue(clienteEquals(clist.get(2), id[2], nascita[2], reg[2]));
    }

    @Test
    void addOrdineTest() {
        String id = "TestOrdine000";
        Ordine o = new Ordine(4, "vegano", LocalDate.now());
        Assertions.assertDoesNotThrow(() -> MioRistorante.addClienteCM(id, 1998, LocalDate.of(2023, 11, 5)));
        Cliente c = MioRistorante.findClienteCM(id);
        Assertions.assertNotNull(c);
        MioRistorante.addOrdineCM(id, o.getNumPiatti(), o.getTipoMenu());
        Assertions.assertTrue(c.containsOrdine(LocalDate.now()));
        Assertions.assertNotNull(c.getOrdine(LocalDate.now()));
        Assertions.assertTrue(o.equals(c.getOrdine(LocalDate.now())));
    }
}
