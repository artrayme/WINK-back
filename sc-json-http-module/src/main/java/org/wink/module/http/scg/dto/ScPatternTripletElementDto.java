package org.wink.module.http.scg.dto;

public class ScPatternTripletElementDto {

    private String type;
    private Integer value;
    private String alias;

    public ScPatternTripletElementDto() {
    }

    public ScPatternTripletElementDto(String type, Integer value) {
        this.type = type;
        this.value = value;
    }

    public ScPatternTripletElementDto(String type, Integer value, String alias) {
        this(type, value);
        this.alias = alias;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
