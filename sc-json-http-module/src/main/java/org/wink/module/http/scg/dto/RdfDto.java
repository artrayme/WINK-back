package org.wink.module.http.scg.dto;

public class RdfDto {
    private String rdfFileName;
    private String rdfContent;

    public RdfDto() {
    }

    public RdfDto(String rdfFileName, String rdfContent) {
        this.rdfFileName = rdfFileName;
        this.rdfContent = rdfContent;
    }

    public String getRdfFileName() {
        return rdfFileName;
    }

    public void setRdfFileName(String rdfFileName) {
        this.rdfFileName = rdfFileName;
    }

    public String getRdfContent() {
        return rdfContent;
    }

    public void setRdfContent(String rdfContent) {
        this.rdfContent = rdfContent;
    }
}
