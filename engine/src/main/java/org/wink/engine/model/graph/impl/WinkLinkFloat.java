package org.wink.engine.model.graph.impl;

import org.ostis.scmemory.model.element.link.LinkContentType;
import org.ostis.scmemory.model.element.link.LinkType;
import org.wink.engine.model.graph.util.WinkElementIdGenerator;

public class WinkLinkFloat extends WinkLink {
    private final Float content;

    public WinkLinkFloat(Long id, LinkType type, Float content) {
        super(id, type);
        this.content = content;
    }

    public WinkLinkFloat(LinkType type, Float content) {
        this(WinkElementIdGenerator.getNextId(), type, content);
    }

    public Float getContent() {
        return content;
    }

    @Override
    public LinkContentType getContentType() {
        return LinkContentType.FLOAT;
    }
}
