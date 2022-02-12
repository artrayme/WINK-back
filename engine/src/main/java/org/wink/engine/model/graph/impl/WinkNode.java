package org.wink.engine.model.graph.impl;

import org.ostis.scmemory.model.element.node.NodeType;
import org.wink.engine.model.graph.interfaces.WinkElement;
import org.wink.engine.model.graph.util.WinkElementIdGenerator;

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
}
