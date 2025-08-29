package co.com.pragma.api.exceptions;

import org.springframework.http.HttpStatus;

public class RequiredFieldsException extends RuntimeException {

    private final HttpStatus status;

    public RequiredFieldsException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

}
