package upo20052959.ristorante;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Test della classe MyResturantController
 */
public class MyResturantControllerTest {
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
        Assertions.assertDoesNotThrow(() -> MyResturantController.addCliente(id, nascita, reg));
        Assertions.assertEquals(numClienti+1, Cliente.getNumClienti());
    }

    @Test
    void findClienteConIdTest() {
        String id = "TestMR2";
        int nascita = 1998;
        LocalDate reg = LocalDate.of(2023, 11, 5);
        Assertions.assertDoesNotThrow(() -> {
            MyResturantController.addCliente("", 1998, LocalDate.of(2023, 11, 5));
            MyResturantController.addCliente(id, nascita, reg);
            MyResturantController.addCliente("", 2000, LocalDate.now());
        });
        Cliente c = MyResturantController.findCliente(id);
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
            MyResturantController.addCliente("", 2000, LocalDate.now());
            MyResturantController.addCliente(id[0], nascita[0], reg[0]);
            MyResturantController.addCliente(id[1], nascita[1], reg[1]);
            MyResturantController.addCliente("", 1900, LocalDate.now());
            MyResturantController.addCliente(id[2], nascita[2], reg[2]);
        });
        ArrayList<Cliente> clist = MyResturantController.findClienteEta(600, 700);
        Assertions.assertNotEquals(clist.size(), 0);
        Assertions.assertTrue(clienteEquals(clist.get(0), id[0], nascita[0], reg[0]));
        Assertions.assertTrue(clienteEquals(clist.get(1), id[1], nascita[1], reg[1]));
        Assertions.assertTrue(clienteEquals(clist.get(2), id[2], nascita[2], reg[2]));
    }

    @Test
    void addOrdineTest() {
        String id = "TestOrdine000";
        Ordine o = new Ordine(4, TipoMenu.VEGANO, LocalDate.now());
        Assertions.assertDoesNotThrow(() -> MyResturantController.addCliente(id, 1998, LocalDate.of(2023, 11, 5)));
        Cliente c = MyResturantController.findCliente(id);
        Assertions.assertNotNull(c);
        MyResturantController.addOrdine(id, o.getNumPiatti(), o.getTipoMenu());
        Assertions.assertTrue(c.containsOrdine(LocalDate.now()));
        Assertions.assertNotNull(c.getOrdine(LocalDate.now()));
        Assertions.assertEquals(o, c.getOrdine(LocalDate.now()));
    }
}
