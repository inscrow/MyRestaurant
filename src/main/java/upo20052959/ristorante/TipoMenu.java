package upo20052959.ristorante;

public enum TipoMenu {
  CARTA(0, "Alla carta"),
  CARNE(1, "Carne"),
  GLUTEN(2, "Senza glutine"),
  PESCE(3, "Pesce"),
  VEGANO(4, "Vegano"),
  VEGETARIANO(5, "Vegetariano");

  private final int id;
  private final String nome;

  TipoMenu(int id, String nome) {
    this.id = id;
    this.nome = nome;
  }

  public int id() {
    return this.id;
  }

  public String nome() {
    return this.nome;
  }

  public static String tipiMenu() {
    String tipiMenu = "";
    for (TipoMenu t : TipoMenu.values()) {
      tipiMenu = tipiMenu.concat(t.id + ". " + t.nome + "\n");
    }
    return tipiMenu;
  }

  public static TipoMenu tipoMenu(int codice) {
      return switch (codice) {
          case 1 -> TipoMenu.CARNE;
          case 2 -> TipoMenu.GLUTEN;
          case 3 -> TipoMenu.PESCE;
          case 4 -> TipoMenu.VEGANO;
          case 5 -> TipoMenu.VEGETARIANO;
          default -> TipoMenu.CARTA;
      };
  }

  @Override
  public String toString() {
    return this.nome;
  }
}
