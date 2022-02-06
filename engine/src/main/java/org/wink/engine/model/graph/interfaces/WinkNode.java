package org.wink.engine.model.graph.interfaces;

import org.ostis.scmemory.model.element.node.NodeType;

public interface WinkNode {
    Long getId();

    NodeType getType();

    String getIdtf();

    WinkContent getContent();
}
