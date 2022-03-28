package org.wink.engine.model.graph.impl;

import org.ostis.scmemory.model.element.node.NodeType;
import org.wink.engine.model.graph.interfaces.WinkElement;
import org.wink.engine.model.graph.util.WinkElementIdGenerator;

import java.util.Objects;

/**
 * @author artrayme
 * @since 0.0.1
 */
public class WinkNode implements WinkElement {
    private final Long id;
    private final NodeType type;

    public WinkNode(Long id, NodeType type) {
        this.id = id;
        this.type = type;
    }

    public WinkNode(NodeType type) {
        this(WinkElementIdGenerator.getNextId(), type);
    }

    public NodeType getType() {
        return type;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WinkNode winkNode = (WinkNode) o;
        return type == winkNode.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }
}
