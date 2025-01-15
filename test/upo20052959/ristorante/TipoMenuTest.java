package upo20052959.ristorante;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

public class TipoMenuTest {
  @Test
  public void selezioneTest() {
    TipoMenu t = TipoMenu.CARNE;
    TipoMenu s = TipoMenu.tipoMenu(t.id());
    Assertions.assertEquals(t, s);
  }
}
