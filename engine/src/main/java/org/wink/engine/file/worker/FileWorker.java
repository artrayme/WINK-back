package org.wink.engine.file.worker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author Inejka
 * @since 0.0.1
 */
public interface FileWorker {
    void write(String fileName, ByteArrayOutputStream outputStream) throws IOException;

    byte[] read(String fileName) throws IOException;

    List<String> getFilesList();
}
