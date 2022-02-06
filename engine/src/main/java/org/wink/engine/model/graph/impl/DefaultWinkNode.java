package org.wink.engine.model.graph.impl;

import org.wink.engine.model.graph.interfaces.WinkContent;
import org.wink.engine.model.graph.interfaces.WinkNode;
import org.ostis.scmemory.model.element.node.NodeType;

public class DefaultWinkNode implements WinkNode {
    private final Long id;
    private final NodeType nodeType;
    private final String idtf;
    private final WinkContent content;

    public DefaultWinkNode(Long id, NodeType nodeType, String idtf) {
        this(id, nodeType, idtf, null);
    }

    public DefaultWinkNode(Long id, NodeType nodeType, String idtf, WinkContent content) {
        this.id = id;
        this.nodeType = nodeType;
        this.idtf = idtf;
        this.content = content;
    }


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public NodeType getType() {
        return nodeType;
    }

    @Override
    public String getIdtf() {
        return idtf;
    }

    @Override
    public WinkContent getContent() {
        return content;
    }
}
