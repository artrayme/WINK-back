package org.wink.engine.model.graph.impl;

import org.ostis.scmemory.model.element.node.NodeType;
import org.wink.engine.model.graph.util.WinkElementIdGenerator;

public class WinkNode {
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

    public Long getId() {
        return id;
    }
}
