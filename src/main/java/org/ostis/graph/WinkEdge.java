package org.ostis.graph;

import org.ostis.scmemory.model.element.edge.EdgeType;

public interface WinkEdge {
    EdgeType getType();

    WinkNode getSource();

    WinkNode getTarget();

    WinkContent getContent();
}
