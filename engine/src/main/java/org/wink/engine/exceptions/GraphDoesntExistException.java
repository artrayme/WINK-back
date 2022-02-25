package org.wink.engine.exceptions;

public class GraphDoesntExistException extends Exception{
    public GraphDoesntExistException(String message) {
        super(message);
    }

    public GraphDoesntExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public GraphDoesntExistException(Throwable cause) {
        super(cause);
    }
}
