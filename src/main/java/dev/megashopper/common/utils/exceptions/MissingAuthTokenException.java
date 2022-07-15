package dev.megashopper.common.utils.exceptions;

public class MissingAuthTokenException extends AuthenticationException {
    public MissingAuthTokenException() {
        super("No authorization found on the request.");
    }
}
