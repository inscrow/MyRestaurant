package upo20052959.ristorante;

/**
 * Classe per i tipi di menù, costringe l'utente a scegliere un menù valido
 */
public enum TipoMenu {
  CARTA(0, "Alla carta"),
  CARNE(1, "Carne"),
  PESCE(2, "Pesce"),
  VEGETARIANO(3, "Vegetariano"),
  VEGANO(4, "Vegano"),
  GLUTEN_FREE(5, "Gluten free");

  private final int codice;
  private final String nome;

  TipoMenu(int codice, String nome) {
    this.codice = codice;
    this.nome = nome;
  }

  public int codice() {
    return this.codice;
  }

  public String nome() {
    return this.nome;
  }

  /**
   * Funzione di utilità per la selezione del menù da parte dell'utente
   * @param codice codice del menù inserito dall'utente
   * @return il tipo di menù col codice corrispondente a quello inserito,
   * oppure menù alla carta se non c'è corrispondenza con nessun valore
   */
  public static TipoMenu selezione(int codice) {
    for (TipoMenu t : TipoMenu.values()) {
      if (t.codice == codice)
        return t;
    }

    return TipoMenu.CARTA;
  }
}
