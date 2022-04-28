package org.wink.module.http.scg.dto;

public class ScPatternTripletElementDto {

    private String type;
    private String value;
    private String alias;

    public ScPatternTripletElementDto() {
    }

    public ScPatternTripletElementDto(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public ScPatternTripletElementDto(String type, String value, String alias) {
        this(type, value);
        this.alias = alias;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
