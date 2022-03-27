package org.wink.engine.exceptions;

public class GraphWithThisNameAlreadyUploadedException extends RuntimeException {
    public GraphWithThisNameAlreadyUploadedException(String message) {
        super(message);
    }
}
