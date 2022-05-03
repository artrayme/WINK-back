package org.wink.engine.model.graph.impl;

import org.wink.engine.model.graph.interfaces.WinkElement;

import java.util.Objects;

public class WinkIdtfiableWrapper implements WinkElement {
    private final String idtf;
    private final WinkElement element;

    public WinkIdtfiableWrapper(WinkElement element, String idtf) {
        this.idtf = idtf;
        this.element = element;
    }

    public String getIdtf() {
        return idtf;
    }

    public WinkElement getElement() {
        return element;
    }

    @Override
    public Long getId() {
        return element.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WinkIdtfiableWrapper that = (WinkIdtfiableWrapper) o;
        return Objects.equals(idtf, that.idtf) && Objects.equals(element, that.element);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idtf, element);
    }
}
