package org.wink.engine.model.graph.impl;

import org.ostis.scmemory.model.element.link.LinkContentType;
import org.ostis.scmemory.model.element.link.LinkType;
import org.wink.engine.model.graph.interfaces.WinkElement;

public abstract class WinkLink implements WinkElement {
    private final Long id;
    private final LinkType type;

    protected WinkLink(Long id, LinkType type) {
        this.id = id;
        this.type = type;
    }

    public LinkType getType() {
        return type;
    }

    public abstract LinkContentType getContentType();

    public Long getId() {
        return id;
    }
}
