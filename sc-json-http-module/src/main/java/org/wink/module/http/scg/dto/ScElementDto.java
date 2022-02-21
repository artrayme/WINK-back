package org.wink.module.http.scg.dto;

public class ScElementDto {

    private String element;
    private Integer type;
    private Object content;
    private ScAdjacentElementDto source;
    private ScAdjacentElementDto target;

    public ScElementDto() {
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
}
