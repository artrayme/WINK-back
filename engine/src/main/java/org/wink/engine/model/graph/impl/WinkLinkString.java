package org.wink.engine.model.graph.impl;

import org.ostis.scmemory.model.element.link.LinkContentType;
import org.ostis.scmemory.model.element.link.LinkType;
import org.wink.engine.model.graph.util.WinkElementIdGenerator;

public class WinkLinkString extends WinkLink {
    private final String content;

    public WinkLinkString(Long id, LinkType type, String content) {
        super(id, type);
        this.content = content;
    }

    public WinkLinkString(LinkType type, String content) {
        this(WinkElementIdGenerator.getNextId(), type, content);
    }

    @Override
    public LinkContentType getContentType() {
        return LinkContentType.STRING;
    }

    public String getContent() {
        return content;
    }

}
