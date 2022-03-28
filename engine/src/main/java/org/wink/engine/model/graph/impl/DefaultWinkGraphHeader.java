package org.wink.engine.model.graph.impl;

import org.wink.engine.model.graph.interfaces.WinkGraphHeader;

import java.util.Objects;

/**
 * @author artrayme
 * @since 0.0.1
 */
public class DefaultWinkGraphHeader implements WinkGraphHeader {
    private final String filename;
    private final String nativeFormat;

    public DefaultWinkGraphHeader(String filename, String nativeFormat) {
        this.filename = filename;
        this.nativeFormat = nativeFormat;
    }

    @Override
    public String getFileName() {
        return filename;
    }

    @Override
    public String getNativeFormat() {
        return nativeFormat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DefaultWinkGraphHeader that = (DefaultWinkGraphHeader) o;
        return filename.equals(that.filename) && nativeFormat.equals(that.nativeFormat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filename, nativeFormat);
    }
}
