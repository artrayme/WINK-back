package org.wink.engine.model.graph.interfaces;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface WinkContent {
    ByteArrayOutputStream toByteArray() throws IOException;

    @Override
    String toString();
}
