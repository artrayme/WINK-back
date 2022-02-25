package org.wink.engine.exceptions;

public class CannotCreateNodeException extends Exception{
    public CannotCreateNodeException(String message) {
        super(message);
    }

    public CannotCreateNodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotCreateNodeException(Throwable cause) {
        super(cause);
    }
}


