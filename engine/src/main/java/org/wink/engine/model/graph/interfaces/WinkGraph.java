package org.wink.engine.model.graph.interfaces;

import java.util.List;

public interface WinkGraph {
    WinkGraphHeader getHeader();

    boolean addNode(WinkNode node);

    boolean addEdge(WinkEdge edge);

    List<WinkNode> getNodes();

    List<WinkEdge> getEdges();
}
