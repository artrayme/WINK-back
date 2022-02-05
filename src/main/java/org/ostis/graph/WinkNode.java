package org.ostis.graph;

import org.ostis.scmemory.model.element.node.NodeType;

public interface WinkNode {
    NodeType getType();

    String getIdtf();

    WinkContent getContent();
}
