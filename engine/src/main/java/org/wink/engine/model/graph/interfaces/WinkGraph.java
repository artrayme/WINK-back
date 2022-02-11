package org.wink.engine.model.graph.interfaces;

import org.ostis.scmemory.model.element.edge.ScEdge;

import java.util.List;

public interface WinkGraph {
    WinkGraphHeader getHeader();

    boolean addEdge(ScEdge edge);

    List<ScEdge> getEdges();
}
