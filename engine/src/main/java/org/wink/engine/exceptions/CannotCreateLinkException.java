package org.wink.engine.exceptions;

public class CannotCreateLinkException extends Exception {

    public CannotCreateLinkException(String message) {
        super(message);
    }

    public CannotCreateLinkException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotCreateLinkException(Throwable cause) {
        super(cause);
    }
}
