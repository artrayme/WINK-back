package org.wink.engine.model.graph.impl;

import org.ostis.scmemory.model.element.link.LinkContentType;
import org.ostis.scmemory.model.element.link.LinkType;
import org.wink.engine.model.graph.util.WinkElementIdGenerator;

import java.util.Objects;

/**
 * @author artrayme
 * @since 0.0.1
 */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WinkLinkString that = (WinkLinkString) o;
        return content.equals(that.content) && super.getType().equals(that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
