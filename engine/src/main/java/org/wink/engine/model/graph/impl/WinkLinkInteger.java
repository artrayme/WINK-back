package org.wink.engine.model.graph.impl;

import org.ostis.scmemory.model.element.link.LinkContentType;
import org.ostis.scmemory.model.element.link.LinkType;
import org.wink.engine.model.graph.util.WinkElementIdGenerator;

import java.util.Objects;

/**
 * @author artrayme
 * @since 0.0.1
 */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WinkLinkInteger that = (WinkLinkInteger) o;
        return content.equals(that.content) && super.getType().equals(that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
