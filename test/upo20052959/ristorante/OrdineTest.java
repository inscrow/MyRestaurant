package upo20052959.ristorante;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

/**
 * Test per la classe Ordine
 */
public class OrdineTest {

    @Test
    public void creaOrdineTest() {
        Ordine o = new Ordine(3, TipoMenu.VEGETARIANO, LocalDate.of(2024, 12, 5));
        Assertions.assertNotNull(o);
        Assertions.assertEquals(3, o.getNumPiatti());
        Assertions.assertEquals(TipoMenu.VEGETARIANO, o.getTipoMenu());
        Assertions.assertEquals(LocalDate.of(2024, 12, 5), o.getData());
    }

    @Test
    public void setNumPiattiTest() {
        Ordine o = new Ordine(3, TipoMenu.VEGETARIANO, LocalDate.of(2024, 12, 5));
        o.setNumPiatti(100);
        Assertions.assertEquals(100, o.getNumPiatti());
    }

    @Test
    public void setTipoMenuTest() {
        Ordine o = new Ordine(3, TipoMenu.VEGETARIANO, LocalDate.of(2024, 12, 5));
        o.setTipoMenu(TipoMenu.CARNE);
        Assertions.assertEquals(TipoMenu.CARNE, o.getTipoMenu());
    }

    @Test
    public void toStringTest() {
        Ordine o = new Ordine(5, TipoMenu.PESCE, LocalDate.of(2024, 12, 5));
        String expected = "5 Pesce 2024-12-05";
        Assertions.assertEquals(expected, o.toString());
    }

    @Test
    public void equalsTest() {
        Ordine o = new Ordine(5, TipoMenu.PESCE, LocalDate.of(2024, 12, 5));
        Ordine p = new Ordine(5, TipoMenu.PESCE, LocalDate.of(2024, 12, 5));
        Assertions.assertTrue(o.equals(p));
    }
}
