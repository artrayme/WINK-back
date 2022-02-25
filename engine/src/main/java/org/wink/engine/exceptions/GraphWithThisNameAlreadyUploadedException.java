package org.wink.engine.exceptions;

public class GraphWithThisNameAlreadyUploadedException extends Exception {
    public GraphWithThisNameAlreadyUploadedException(String message) {
        super(message);
    }

    public GraphWithThisNameAlreadyUploadedException(String message, Throwable cause) {
        super(message, cause);
    }

    public GraphWithThisNameAlreadyUploadedException(Throwable cause) {
        super(cause);
    }
}
