package upo20052959.ristorante;

/**
 * Eccezione lanciata quando si prova a creare un cliente con un id già utilizzato da un altro cliente
 */
public class IdAlreadyUsed extends RuntimeException {
    public IdAlreadyUsed(String message) {
        super(message);
    }
}
