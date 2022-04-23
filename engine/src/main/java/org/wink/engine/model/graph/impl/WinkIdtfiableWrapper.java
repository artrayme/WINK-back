package org.wink.engine.model.graph.impl;

import org.wink.engine.model.graph.interfaces.WinkElement;

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
}
