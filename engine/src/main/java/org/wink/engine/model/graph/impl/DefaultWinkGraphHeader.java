package org.wink.engine.model.graph.impl;

import org.wink.engine.model.graph.interfaces.WinkGraphHeader;

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
}
