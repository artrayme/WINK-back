package org.wink.engine.model.graph.impl;

import org.ostis.scmemory.model.element.link.LinkContentType;
import org.ostis.scmemory.model.element.link.LinkType;
import org.wink.engine.model.graph.util.WinkElementIdGenerator;

public class WinkLinkInteger extends WinkLink {
    private final Integer content;

    public WinkLinkInteger(Long id, LinkType type, Integer content) {
        super(id, type);
        this.content = content;
    }

    public WinkLinkInteger(LinkType type, Integer content) {
        this(WinkElementIdGenerator.getNextId(), type, content);
    }

    public Integer getContent() {
        return content;
    }

    @Override
    public LinkContentType getContentType() {
        return LinkContentType.INT;
    }
}
