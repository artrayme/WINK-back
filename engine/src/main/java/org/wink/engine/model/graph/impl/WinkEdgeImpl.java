package org.wink.engine.model.graph.impl;

import org.wink.engine.model.graph.interfaces.WinkContent;
import org.wink.engine.model.graph.interfaces.WinkEdge;
import org.wink.engine.model.graph.interfaces.WinkNode;
import org.ostis.scmemory.model.element.edge.EdgeType;

public class WinkEdgeImpl implements WinkEdge {
    private final Long id;
    private final EdgeType edgeType;
    private final WinkNode sourceNode;
    private final WinkNode targetNode;
    private final WinkContent content;

    public WinkEdgeImpl(Long id, EdgeType edgeType, WinkNode sourceNode, WinkNode targetNode) {
        this(id, edgeType, sourceNode, targetNode, null);
    }

    public WinkEdgeImpl(Long id, EdgeType edgeType, WinkNode sourceNode, WinkNode targetNode, WinkContent content) {
        this.id = id;
        this.edgeType = edgeType;
        this.sourceNode = sourceNode;
        this.targetNode = targetNode;
        this.content = content;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public EdgeType getType() {
        return edgeType;
    }

    @Override
    public WinkNode getSource() {
        return sourceNode;
    }

    @Override
    public WinkNode getTarget() {
        return targetNode;
    }

    @Override
    public WinkContent getContent() {
        return content;
    }
}
