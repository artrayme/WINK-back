package org.wink.module.http.scg.dto;

public class AutocompletionDto {

    private String part;
    private int limit;

    public AutocompletionDto() {
    }

    public AutocompletionDto(String part, int limit) {
        this.part = part;
        this.limit = limit;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
