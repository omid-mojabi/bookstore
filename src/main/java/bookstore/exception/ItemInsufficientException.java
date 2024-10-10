package bookstore.exception;

public class ItemInsufficientException extends RuntimeException {
    public ItemInsufficientException(String message) {
        super(message);
    }
}
