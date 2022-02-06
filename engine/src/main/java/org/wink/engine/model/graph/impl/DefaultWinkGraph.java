package org.wink.engine.model.graph.impl;

import org.wink.engine.model.graph.interfaces.WinkGraphHeader;
import org.wink.engine.model.graph.interfaces.WinkEdge;
import org.wink.engine.model.graph.interfaces.WinkGraph;
import org.wink.engine.model.graph.interfaces.WinkNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultWinkGraph implements WinkGraph {
    private final WinkGraphHeader header;
    private final List<WinkNode> nodes = new ArrayList<>();
    private final List<WinkEdge> edges = new ArrayList<>();

    public DefaultWinkGraph(WinkGraphHeader header) {
        this.header = header;
    }

    @Override
    public WinkGraphHeader getHeader() {
        return header;
    }

    @Override
    public boolean addNode(WinkNode node) {
        return nodes.add(node);
    }

    @Override
    public boolean addEdge(WinkEdge edge) {
        return edges.add(edge);
    }

    @Override
    public List<WinkNode> getNodes() {
        return Collections.unmodifiableList(nodes);
    }

    @Override
    public List<WinkEdge> getEdges() {
        return Collections.unmodifiableList(edges);
    }
}
