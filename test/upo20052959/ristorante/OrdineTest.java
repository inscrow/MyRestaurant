package upo20052959.ristorante;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class OrdineTest {

    @Test
    public void creaOrdineTest() {
        Ordine o = new Ordine(3, "vegetariano", LocalDate.of(2024, 12, 5));
        Assertions.assertNotNull(o);
        Assertions.assertEquals(3, o.getNumPiatti());
        Assertions.assertEquals("vegetariano", o.getTipoMenu());
        Assertions.assertEquals(LocalDate.of(2024, 12, 5), o.getData());
    }

    @Test
    public void setNumPiattiTest() {
        Ordine o = new Ordine(3, "vegetariano", LocalDate.of(2024, 12, 5));
        o.setNumPiatti(100);
        Assertions.assertEquals(100, o.getNumPiatti());
    }

    @Test
    public void setTipoMenuTest() {
        Ordine o = new Ordine(3, "vegetariano", LocalDate.of(2024, 12, 5));
        o.setTipoMenu("carne");
        Assertions.assertEquals("carne", o.getTipoMenu());
    }
}
