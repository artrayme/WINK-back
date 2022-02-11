package org.wink.engine.model.graph.interfaces;

import org.ostis.scmemory.model.element.edge.ScEdge;
import org.wink.engine.model.graph.impl.WinkEdge;

import java.util.List;

public interface WinkGraph {
    WinkGraphHeader getHeader();

    boolean addEdge(WinkEdge edge);

    List<WinkEdge> getEdges();
}
