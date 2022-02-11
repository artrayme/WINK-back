package org.wink.engine.model.graph.impl;

import org.ostis.scmemory.model.element.edge.ScEdge;
import org.wink.engine.model.graph.interfaces.WinkGraph;
import org.wink.engine.model.graph.interfaces.WinkGraphHeader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultWinkGraph implements WinkGraph {
    private final WinkGraphHeader header;
    private final List<ScEdge> edges = new ArrayList<>();

    public DefaultWinkGraph(WinkGraphHeader header) {
        this.header = header;
    }

    @Override
    public WinkGraphHeader getHeader() {
        return header;
    }

    @Override
    public boolean addEdge(ScEdge edge) {
        return edges.add(edge);
    }

    @Override
    public List<ScEdge> getEdges() {
        return Collections.unmodifiableList(edges);
    }
}
