package co.com.pragma.api.exceptions;

public class ExistUserException extends RuntimeException {
    public ExistUserException(String message) {
        super(message);
    }
}
