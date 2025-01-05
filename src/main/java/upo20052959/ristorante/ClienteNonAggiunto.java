package upo20052959.ristorante;

/**
 * Eccezione lanciata quando non è possibile aggiungere un cliente alla lista di clienti
 */
public class ClienteNonAggiunto extends RuntimeException {
    public ClienteNonAggiunto(String message) {
        super(message);
    }
}
