package org.wink.engine.exceptions;

public class CannotCreateEdgeException extends Exception{
    public CannotCreateEdgeException(String message) {
        super(message);
    }

    public CannotCreateEdgeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotCreateEdgeException(Throwable cause) {
        super(cause);
    }
}
