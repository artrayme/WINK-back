package org.wink.engine.model.graph.impl;

import org.ostis.scmemory.model.element.edge.ScEdge;
import org.wink.engine.model.graph.interfaces.WinkGraph;
import org.wink.engine.model.graph.interfaces.WinkGraphHeader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author artrayme
 * @since 0.0.1
 */
public class DefaultWinkGraph implements WinkGraph {
    private final WinkGraphHeader header;
    private final List<WinkEdge> edges = new ArrayList<>();

    public DefaultWinkGraph(WinkGraphHeader header) {
        this.header = header;
    }

    @Override
    public WinkGraphHeader getHeader() {
        return header;
    }

    @Override
    public boolean addEdge(WinkEdge edge) {
        return edges.add(edge);
    }

    @Override
    public List<WinkEdge> getEdges() {
        return Collections.unmodifiableList(edges);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DefaultWinkGraph that = (DefaultWinkGraph) o;
        return header.equals(that.header) && edges.equals(that.edges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, edges);
    }
}
