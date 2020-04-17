package andrew.korzhov.ecommerce.service.errors;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message, Object... args) {
        super(String.format(message, args));
    }

    public NotFoundException(String message, Throwable cause, Object... args) {
        super(String.format(message, args), cause);
    }
}
