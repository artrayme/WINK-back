package org.wink.module.http.scg.dto;

import java.util.List;

public class WinkGraphDto {

    private String filename;
    private String nativeFormat;
    private List<ScElementDto> scElements;

    public WinkGraphDto() {
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getNativeFormat() {
        return nativeFormat;
    }

    public void setNativeFormat(String nativeFormat) {
        this.nativeFormat = nativeFormat;
    }

    public List<ScElementDto> getScElements() {
        return scElements;
    }

    public void setScElements(List<ScElementDto> scElements) {
        this.scElements = scElements;
    }
}
