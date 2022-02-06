package org.wink.engine.model.graph.interfaces;

import org.ostis.scmemory.model.element.edge.EdgeType;

public interface WinkEdge {
    Long getId();

    EdgeType getType();

    WinkNode getSource();

    WinkNode getTarget();

    WinkContent getContent();
}
