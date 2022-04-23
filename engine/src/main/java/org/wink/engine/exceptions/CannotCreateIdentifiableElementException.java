package org.wink.engine.exceptions;

import org.ostis.scmemory.model.exception.ScMemoryException;

public class CannotCreateIdentifiableElementException extends RuntimeException {
    public CannotCreateIdentifiableElementException(ScMemoryException e) {
        super(e);
    }
}
