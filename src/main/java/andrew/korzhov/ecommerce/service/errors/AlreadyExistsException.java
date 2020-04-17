package andrew.korzhov.ecommerce.service.errors;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String message, Object... args) {
        super(String.format(message, args));
    }

    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
