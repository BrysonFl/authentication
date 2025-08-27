package co.com.pragma.api.exceptions;

public class RequiredFieldsException extends RuntimeException {
    public RequiredFieldsException(String message) {
        super(message);
    }
}
