package org.wink.engine.converter.rdf.impl;

import org.junit.jupiter.api.*;
import org.ostis.api.context.DefaultScContext;
import org.ostis.scmemory.model.ScMemory;
import org.ostis.scmemory.model.element.edge.EdgeType;
import org.ostis.scmemory.model.element.link.LinkType;
import org.ostis.scmemory.model.element.node.NodeType;
import org.ostis.scmemory.model.exception.ScMemoryException;
import org.ostis.scmemory.websocketmemory.memory.SyncOstisScMemory;
import org.wink.engine.converter.rdf.RdfToWinkConverter;
import org.wink.engine.model.graph.impl.*;
import org.wink.engine.model.graph.interfaces.WinkGraph;
import org.wink.engine.scmemory.OstisScMemoryManager;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RdfToWinkConverterBasicTest {
    private static String rdfContent;
    private static WinkGraph expectedGraph;
    private static WinkGraph actualGraph;
    private static String actualGraphName;
    private static OstisScMemoryManager manager;

    @BeforeAll
    static void setUp() throws Exception {
        ScMemory memory = new SyncOstisScMemory(new URI("ws://localhost:8090/ws_json"));
        DefaultScContext context = new DefaultScContext(memory);
        memory.open();
        manager = new OstisScMemoryManager(context);

        actualGraphName = "test";
        rdfContent = """
                <?xml version="1.0"?>
                                
                <rdf:RDF
                xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                xmlns:cd="http://www.recshop.fake/cd#">
                                
                <rdf:Description
                rdf:about="http://www.recshop.fake/cd/Beatles">
                  <cd:format>
                    <rdf:Alt>
                      <rdf:li>CD</rdf:li>
                    </rdf:Alt>
                  </cd:format>
                </rdf:Description>
                                
                </rdf:RDF>
                """;

        expectedGraph = new DefaultWinkGraph(new DefaultWinkGraphHeader("test.rdf", "RDF/XML"));
        WinkLink linkBeatles = new WinkLinkString(LinkType.LINK, "http://www.recshop.fake/cd/Beatles");
        WinkNode nodeBeatles = new WinkNode(NodeType.NODE);
        WinkEdge edgeBeatlesNodeLink = new WinkEdge(EdgeType.ACCESS, linkBeatles, nodeBeatles);
        expectedGraph.addEdge(edgeBeatlesNodeLink);
        WinkLink linkFormat = new WinkLinkString(LinkType.LINK, "http://www.recshop.fake/cd#format");
        WinkNode nodeCollection = new WinkNode(NodeType.NODE);
        WinkEdge edgeBeatlesNodeCollectionNode = new WinkEdge(EdgeType.ACCESS, nodeBeatles, nodeCollection);
        WinkEdge edgeBeatlesNodeCollectionNodeEdge = new WinkEdge(EdgeType.ACCESS, linkFormat, edgeBeatlesNodeCollectionNode);
        expectedGraph.addEdge(edgeBeatlesNodeCollectionNode);
        expectedGraph.addEdge(edgeBeatlesNodeCollectionNodeEdge);
        WinkNode nodeAlt = new WinkNode(NodeType.NODE);
        WinkLink linkAlt = new WinkLinkString(LinkType.LINK, "http://www.w3.org/1999/02/22-rdf-syntax-ns#Alt");
        WinkEdge edgeAltLinkAltNode = new WinkEdge(EdgeType.ACCESS, linkAlt, nodeAlt);
        expectedGraph.addEdge(edgeAltLinkAltNode);
        WinkEdge edgeCollectionNodeAltNode = new WinkEdge(EdgeType.ACCESS, nodeCollection, nodeAlt);
        WinkLink linkType = new WinkLinkString(LinkType.LINK, "http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        WinkEdge edgeLinkTypeEdgeCollectionNodeAltNode = new WinkEdge(EdgeType.ACCESS, linkType, edgeCollectionNodeAltNode);
        expectedGraph.addEdge(edgeLinkTypeEdgeCollectionNodeAltNode);
        expectedGraph.addEdge(edgeCollectionNodeAltNode);
        WinkNode nodeCd = new WinkNode(NodeType.NODE);
        WinkLink linkCd = new WinkLinkString(LinkType.LINK, "CD");
        WinkEdge edgeCd = new WinkEdge(EdgeType.ACCESS, linkCd, nodeCd);
        expectedGraph.addEdge(edgeCd);
        WinkEdge edgeCollectionNodeCDNode = new WinkEdge(EdgeType.ACCESS, nodeCollection, nodeCd);
        expectedGraph.addEdge(edgeCollectionNodeCDNode);
        WinkLink linkFirst = new WinkLinkString(LinkType.LINK, "http://www.w3.org/1999/02/22-rdf-syntax-ns#_1");
        WinkEdge edgeLinkFirstEdgeCollectionNodeCDNode = new WinkEdge(EdgeType.ACCESS, linkFirst, edgeCollectionNodeCDNode);
        expectedGraph.addEdge(edgeLinkFirstEdgeCollectionNodeCDNode);
    }

    @Test
    @Order(1)
    void convertRdf() {
        RdfToWinkConverter converter = new RdfToWinkConverterBasic();

        WinkGraph graph = converter.convertRdf(rdfContent, "test.rdf");
        actualGraph = graph;
        List<WinkEdge> edgesActual = graph.getEdges();
        List<WinkEdge> edgesExpected = expectedGraph.getEdges();
        int expectedEdgesCount = expectedGraph.getEdges().size();
        int actualEdgesCount = graph.getEdges().size();
        boolean actual = edgesActual.containsAll(edgesExpected) && edgesExpected.containsAll(edgesActual);

        assertEquals(expectedEdgesCount, actualEdgesCount);
        assertTrue(actual);
    }

    @Test
    @Order(2)
    void loadGraphToMemory() {
        manager.upload(actualGraphName, actualGraph);
    }

    @Test
    @Order(3)
    void unloadGraphFromMemory() throws ScMemoryException {
        manager.unload(actualGraphName);
    }
}