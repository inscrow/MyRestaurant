package upo20052959.ristorante;

/**
 * Eccezione lanciata quando fallisce l'inserimento di un cliente
 */
public class NoAddClienteException extends Exception {
    public NoAddClienteException(String message) {
        super(message);
    }
}