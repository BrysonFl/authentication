package co.com.pragma.usecase.user.exceptions;

public class ExistUserException extends RuntimeException {
    public ExistUserException(String message) {
        super(message);
    }
}
