package org.ostis.graph;

public interface WinkGraph {
    WinkGraphHeader getHeader();

    boolean addNode(WinkNode node);

    boolean addEdge(WinkEdge edge);
}
