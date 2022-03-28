package org.wink.engine.model.graph.impl;

import org.ostis.scmemory.model.element.link.LinkContentType;
import org.ostis.scmemory.model.element.link.LinkType;
import org.wink.engine.model.graph.util.WinkElementIdGenerator;

import java.util.Objects;

/**
 * @author artrayme
 * @since 0.0.1
 */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WinkLinkFloat that = (WinkLinkFloat) o;
        return content.equals(that.content) && super.getType().equals(that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
