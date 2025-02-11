package upo20052959.ristorante;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Test per la classe Cliente
 */
public class ClienteTest {

    @Test
    void creaClienteConIdTest() {
        Assertions.assertDoesNotThrow(() -> {
            Cliente c = new Cliente("test01", 1998, LocalDate.of(2020, 12, 31));
            Assertions.assertNotNull(c);
            Assertions.assertEquals("test01", c.getId());
            Assertions.assertEquals(1998, c.getNascita());
            Assertions.assertEquals(LocalDate.of(2020, 12, 31), c.getRegistrazione());
        });
    }

    @Test
    void creaClienteSenzaIdTest() {
        Assertions.assertDoesNotThrow(() -> {
            Cliente c = new Cliente(1998, LocalDate.of(2024, 12, 5));
            Assertions.assertNotNull(c);
            Assertions.assertEquals(1998, c.getNascita());
            Assertions.assertEquals(LocalDate.of(2024, 12, 5), c.getRegistrazione());
        });
    }

    @Test
    void generaIdTest() {
        String newId = Cliente.generaId();
        Assertions.assertNotNull(newId);
        // Assertions.assertFalse(Cliente.listaIds.contains(newId));
    }

    @Test
    void getNumClientiTest() {
        Assertions.assertDoesNotThrow(() -> {
            int numClienti = Cliente.getNumClienti();
            Cliente c = new Cliente(1998, LocalDate.of(2020, 12, 31));
            Assertions.assertEquals(numClienti + 1, Cliente.getNumClienti());
        });
    }

    @Test
    void addOrdineTest() {
        Assertions.assertDoesNotThrow(() -> {
            Cliente c = new Cliente(1998, LocalDate.of(2020, 12, 31));
            c.addOrdine(3, TipoMenu.CARNE, LocalDate.of(2024, 12, 5));
            Assertions.assertEquals(1, c.getNumOrdini());
        });
    }

    @Test
    void containsOrdineTest() {
        Assertions.assertDoesNotThrow(() -> {
            Cliente c = new Cliente(1998, LocalDate.of(2020, 12, 31));
            c.addOrdine(3, TipoMenu.CARNE, LocalDate.of(2024, 12, 5));
            Assertions.assertTrue(c.containsOrdine(LocalDate.of(2024, 12, 5)));
            Assertions.assertFalse(c.containsOrdine(LocalDate.of(2020, 12, 31)));
        });
    }

    @Test
    void getOrdineTest() {
        Assertions.assertDoesNotThrow(() -> {
            Cliente c = new Cliente(1998, LocalDate.of(2020, 12, 31));
            Ordine o = new Ordine(3, TipoMenu.CARNE, LocalDate.of(2024, 12, 5));
            c.addOrdine(3, TipoMenu.CARNE, LocalDate.of(2024, 12, 5));
            Assertions.assertEquals(o, c.getOrdine(LocalDate.of(2024, 12, 5)));
        });
    }

    @Test
    void deleteOrdineTest() {
        Assertions.assertDoesNotThrow(() -> {
            Cliente c = new Cliente(1998, LocalDate.of(2020, 12, 31));
            c.addOrdine(3, TipoMenu.CARNE, LocalDate.of(2024, 12, 5));
            c.deleteOrdine(LocalDate.of(2024, 12, 5));
            Assertions.assertNull(c.getOrdine(LocalDate.of(2024, 12, 5)));
        });
    }

    @Test
    void toStringTest() {
        Assertions.assertDoesNotThrow(() -> {
            Cliente c = new Cliente("TestToString", 1998, LocalDate.of(2020, 12, 31));
            String expected = "Cliente [id=TestToString, nascita=1998, registrazione=2020-12-31]";
            Assertions.assertEquals(expected, c.toString());
        });
    }

    @Test
    void getListNumPiattiTest() {
        ArrayList<Integer> numPiatti = new ArrayList<>();
        numPiatti.add(4);
        numPiatti.add(3);
        numPiatti.add(5);

        Cliente c = new Cliente(1998, LocalDate.of(2020, 12, 31));
        c.addOrdine(numPiatti.get(0), TipoMenu.CARNE, LocalDate.of(2024, 12, 5));
        c.addOrdine(numPiatti.get(1), TipoMenu.PESCE, LocalDate.of(2023, 12, 6));
        c.addOrdine(numPiatti.get(2), TipoMenu.VEGANO, LocalDate.of(2021, 8, 5));

        ArrayList<Integer> results = c.getListNumPiatti();

        Assertions.assertArrayEquals(numPiatti.toArray(), results.toArray());
    }

    @Test
    void getListTipoMenuTest() {
        ArrayList<TipoMenu> tipoMenu = new ArrayList<>();
        tipoMenu.add(TipoMenu.CARNE);
        tipoMenu.add(TipoMenu.PESCE);
        tipoMenu.add(TipoMenu.VEGANO);

        Cliente c = new Cliente(1998, LocalDate.of(2020, 12, 31));

        c.addOrdine(3, tipoMenu.get(0), LocalDate.of(2024, 12, 5));
        c.addOrdine(4, tipoMenu.get(1), LocalDate.of(2023, 12, 6));
        c.addOrdine(5, tipoMenu.get(2), LocalDate.of(2021, 8, 5));

        ArrayList<TipoMenu> results = c.getListTipoMenu();
        Assertions.assertArrayEquals(tipoMenu.toArray(), results.toArray());
    }
}
