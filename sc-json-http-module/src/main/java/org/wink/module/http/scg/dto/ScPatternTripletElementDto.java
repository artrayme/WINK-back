package org.wink.module.http.scg.dto;

public class ScPatternTripletElementDto<T extends Object> {

    private String type;
    private T value;
    private String alias;

    public ScPatternTripletElementDto() {
    }

    public ScPatternTripletElementDto(String type, T value) {
        this.type = type;
        this.value = value;
    }

    public ScPatternTripletElementDto(String type, T value, String alias) {
        this(type, value);
        this.alias = alias;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
