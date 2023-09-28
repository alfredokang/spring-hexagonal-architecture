package dev.alfredokang.hexagonalarchitecture.domain.exceptions;

public class UserExceptionHandler extends RuntimeException {
    public UserExceptionHandler(String message) {
        super(message);
    }

    public UserExceptionHandler(String message, Throwable cause) {
        super(message, cause);
    }
}
