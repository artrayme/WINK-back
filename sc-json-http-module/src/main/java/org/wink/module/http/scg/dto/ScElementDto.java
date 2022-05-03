package org.wink.module.http.scg.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikita Torap
 * @since 0.0.1
 */
public class ScElementDto {
    @JsonProperty("el")
    private String element;
    private Integer type;
    @JsonProperty("value")
    private Object content;
    @JsonProperty("src")
    private ScAdjacentElementDto source;
    @JsonProperty("trg")
    private ScAdjacentElementDto target;
    private String id;

    public ScElementDto() {
    }

    public ScElementDto(String element, Integer type) {
        this.element = element;
        this.type = type;
    }

    public ScElementDto(String element, Integer type, Object content) {
        this.element = element;
        this.type = type;
        this.content = content;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public ScAdjacentElementDto getSource() {
        return source;
    }

    public void setSource(ScAdjacentElementDto source) {
        this.source = source;
    }

    public ScAdjacentElementDto getTarget() {
        return target;
    }

    public void setTarget(ScAdjacentElementDto target) {
        this.target = target;
    }

    public String getId() {
        return id;
    }
}
