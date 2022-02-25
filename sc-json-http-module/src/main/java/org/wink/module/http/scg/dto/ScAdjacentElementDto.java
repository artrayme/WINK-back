package org.wink.module.http.scg.dto;


/**
 * @author Mikita Torap
 * @since 0.0.1
 */
public class ScAdjacentElementDto {

    private String type;
    private Integer value;

    public ScAdjacentElementDto() {
    }

    public ScAdjacentElementDto(String type, Integer value) {
        this.type = type;
        this.value = value;
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
}
