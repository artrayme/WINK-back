package org.wink.engine.scmemory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ostis.api.context.DefaultScContext;
import org.ostis.scmemory.model.ScMemory;
import org.ostis.scmemory.model.element.edge.EdgeType;
import org.ostis.scmemory.model.element.node.NodeType;
import org.ostis.scmemory.model.exception.ScMemoryException;
import org.ostis.scmemory.websocketmemory.memory.SyncOstisScMemory;
import org.wink.engine.exceptions.CannotCreateEdgeException;
import org.wink.engine.exceptions.CannotCreateLinkException;
import org.wink.engine.exceptions.CannotCreateNodeException;
import org.wink.engine.exceptions.GraphDoesntExistException;
import org.wink.engine.exceptions.GraphWithThisNameAlreadyUploadedException;
import org.wink.engine.model.graph.impl.DefaultWinkGraph;
import org.wink.engine.model.graph.impl.DefaultWinkGraphHeader;
import org.wink.engine.model.graph.impl.WinkEdge;
import org.wink.engine.model.graph.impl.WinkNode;
import org.wink.engine.model.graph.interfaces.WinkGraph;

import java.net.URI;

/**
 * @author artrayme
 * @since 0.0.1
 */
class OstisScMemoryManagerTest {
    private OstisScMemoryManager manager;

    @BeforeEach
    void setUp() throws Exception {
        ScMemory memory = new SyncOstisScMemory(new URI("ws://localhost:8090/ws_json"));
        DefaultScContext context = new DefaultScContext(memory);
        memory.open();
        manager = new OstisScMemoryManager(context);
    }

    @AfterEach
    void tearDown() throws Exception {
        manager.getScContext().getMemory().close();
    }

    @Test
    void simpleTripletUploadingAndUnloading() throws CannotCreateEdgeException, GraphWithThisNameAlreadyUploadedException, CannotCreateNodeException, CannotCreateLinkException, ScMemoryException, GraphDoesntExistException {
        String name = "some_graph_1";
        WinkGraph graph = new DefaultWinkGraph(new DefaultWinkGraphHeader(name, ""));
        graph.addEdge(new WinkEdge(1L, EdgeType.ACCESS, new WinkNode(NodeType.NODE), new WinkNode(NodeType.NODE)));
        manager.upload(name, graph);
        manager.unload(name);
    }

    @Test
    void unloadAll() throws CannotCreateEdgeException, GraphWithThisNameAlreadyUploadedException, CannotCreateNodeException, CannotCreateLinkException, ScMemoryException {
        String name1 = "some_graph_1";
        String name2 = "some_graph_2";
        WinkGraph graph1 = new DefaultWinkGraph(new DefaultWinkGraphHeader(name1, ""));
        graph1.addEdge(new WinkEdge(1L, EdgeType.ACCESS, new WinkNode(NodeType.NODE), new WinkNode(NodeType.NODE)));
        WinkGraph graph2 = new DefaultWinkGraph(new DefaultWinkGraphHeader(name2, ""));
        graph2.addEdge(new WinkEdge(2L, EdgeType.ACCESS, new WinkNode(NodeType.NODE), new WinkNode(NodeType.NODE)));
        manager.upload(name1, graph1);
        manager.upload(name2, graph2);
        manager.unloadAll();
    }
}