package org.wink.engine.scmemory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ostis.api.context.DefaultScContext;
import org.ostis.scmemory.model.ScMemory;
import org.ostis.scmemory.model.element.edge.EdgeType;
import org.ostis.scmemory.model.element.link.LinkType;
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
import org.wink.engine.model.graph.impl.WinkIdtfiableWrapper;
import org.wink.engine.model.graph.impl.WinkLinkFloat;
import org.wink.engine.model.graph.impl.WinkLinkInteger;
import org.wink.engine.model.graph.impl.WinkLinkString;
import org.wink.engine.model.graph.impl.WinkNode;
import org.wink.engine.model.graph.interfaces.WinkGraph;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void simpleTripletWithIdentifiableElementsUploadingAndUnloading() throws CannotCreateEdgeException, GraphWithThisNameAlreadyUploadedException, CannotCreateNodeException, CannotCreateLinkException, ScMemoryException, GraphDoesntExistException {
        String name = "some_graph_1";
        WinkGraph graph = new DefaultWinkGraph(new DefaultWinkGraphHeader(name, ""));
        String my_node_1 = "my_node_1";
        String my_node_2 = "my_node_2";
        graph.addEdge(new WinkEdge(1L, EdgeType.ACCESS, new WinkIdtfiableWrapper(new WinkNode(NodeType.NODE), my_node_1), new WinkIdtfiableWrapper(new WinkNode(NodeType.NODE), my_node_2)));
        manager.upload(name, graph);
        assertTrue(manager.getScContext().findKeynode(my_node_1).isPresent());
        assertTrue(manager.getScContext().findKeynode(my_node_2).isPresent());
        manager.unload(name);
        assertTrue(manager.getScContext().findKeynode(my_node_1).isEmpty());
        assertTrue(manager.getScContext().findKeynode(my_node_2).isEmpty());
    }

    @Test
    void contourTest() throws CannotCreateEdgeException, GraphWithThisNameAlreadyUploadedException, CannotCreateNodeException, CannotCreateLinkException, ScMemoryException, GraphDoesntExistException {
        String name = "some_graph_1";
        WinkGraph graph = new DefaultWinkGraph(new DefaultWinkGraphHeader(name, ""));
        String my_node_1 = "my_node_1";
        String my_node_2 = "my_node_2";
        graph.addEdge(new WinkEdge(1L, EdgeType.ACCESS, new WinkIdtfiableWrapper(new WinkNode(NodeType.NODE), my_node_1), new WinkIdtfiableWrapper(new WinkNode(NodeType.NODE), my_node_2)));
        String contourName = manager.uploadContour(name, graph);
        assertTrue(manager.getScContext().findKeynode(my_node_1).isPresent());
        assertTrue(manager.getScContext().findKeynode(my_node_2).isPresent());
        assertTrue(manager.getScContext().findKeynode(contourName).isPresent());
        manager.unload(name);
        assertTrue(manager.getScContext().findKeynode(my_node_1).isEmpty());
        assertTrue(manager.getScContext().findKeynode(my_node_2).isEmpty());
        assertTrue(manager.getScContext().findKeynode(contourName).isEmpty());
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


    @Test
    void tripletWithIntegerLinkUploadingAndUnloading() throws CannotCreateEdgeException, GraphWithThisNameAlreadyUploadedException, CannotCreateNodeException, CannotCreateLinkException, ScMemoryException, GraphDoesntExistException {
        String name = "some_graph_1";
        WinkGraph graph = new DefaultWinkGraph(new DefaultWinkGraphHeader(name, ""));
        graph.addEdge(new WinkEdge(EdgeType.ACCESS, new WinkLinkInteger(LinkType.LINK, 123), new WinkNode(NodeType.NODE)))
        ;
        manager.upload(name, graph);
        manager.unload(name);
    }

    @Test
    void tripletWithStringLinkUploadingAndUnloading() throws CannotCreateEdgeException, GraphWithThisNameAlreadyUploadedException, CannotCreateNodeException, CannotCreateLinkException, ScMemoryException, GraphDoesntExistException {
        String name = "some_graph_1";
        WinkGraph graph = new DefaultWinkGraph(new DefaultWinkGraphHeader(name, ""));
        graph.addEdge(new WinkEdge(EdgeType.ACCESS, new WinkLinkString(LinkType.LINK, "some_link"), new WinkNode(NodeType.NODE)));
        manager.upload(name, graph);
        manager.unload(name);
    }

    @Test
    void tripletWithFloatLinkUploadingAndUnloading() throws CannotCreateEdgeException, GraphWithThisNameAlreadyUploadedException, CannotCreateNodeException, CannotCreateLinkException, ScMemoryException, GraphDoesntExistException {
        String name = "some_graph_1";
        WinkGraph graph = new DefaultWinkGraph(new DefaultWinkGraphHeader(name, ""));
        graph.addEdge(new WinkEdge(EdgeType.ACCESS, new WinkLinkFloat(LinkType.LINK, 12.3f), new WinkNode(NodeType.NODE)));
        manager.upload(name, graph);
        manager.unload(name);
    }

    @Test
    void checkGraphDuplicateException() throws CannotCreateEdgeException, GraphWithThisNameAlreadyUploadedException, CannotCreateNodeException, CannotCreateLinkException, ScMemoryException, GraphDoesntExistException {
        String name = "some_graph_1";
        WinkGraph graph = new DefaultWinkGraph(new DefaultWinkGraphHeader(name, ""));
        graph.addEdge(new WinkEdge(EdgeType.ACCESS, new WinkLinkFloat(LinkType.LINK, 12.3f), new WinkNode(NodeType.NODE)));
        manager.upload(name, graph);
        assertThrows(GraphWithThisNameAlreadyUploadedException.class, () -> {
            String duplicateName = "some_graph_1";
            WinkGraph duplicateGraph = new DefaultWinkGraph(new DefaultWinkGraphHeader(name, ""));
            graph.addEdge(new WinkEdge(EdgeType.ACCESS, new WinkLinkFloat(LinkType.LINK, 12.3f), new WinkNode(NodeType.NODE)));
            manager.upload(name, graph);
        });
        manager.unload(name);
    }

    @Test
    void checkGraphUnloadingException() throws CannotCreateEdgeException, GraphWithThisNameAlreadyUploadedException, CannotCreateNodeException, CannotCreateLinkException, ScMemoryException, GraphDoesntExistException {
        String name = "some_graph_1";
        WinkGraph graph = new DefaultWinkGraph(new DefaultWinkGraphHeader(name, ""));
        graph.addEdge(new WinkEdge(EdgeType.ACCESS, new WinkLinkFloat(LinkType.LINK, 12.3f), new WinkNode(NodeType.NODE)));
        assertThrows(GraphDoesntExistException.class, () -> {
            manager.unload(name);
        });
    }
}