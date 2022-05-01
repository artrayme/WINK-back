package org.wink.engine.converter.rdf.impl;

import org.junit.jupiter.api.*;
import org.ostis.api.context.DefaultScContext;
import org.ostis.scmemory.model.ScMemory;
import org.ostis.scmemory.model.exception.ScMemoryException;
import org.ostis.scmemory.websocketmemory.memory.SyncOstisScMemory;
import org.wink.engine.converter.rdf.RdfToWinkConverter;
import org.wink.engine.exceptions.RdfParseException;
import org.wink.engine.model.graph.interfaces.WinkGraph;
import org.wink.engine.scmemory.OstisScMemoryManager;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RdfToWinkConverterBasicTest {
    private static String rdfContent;
    private static String actualGraphName;
    private static int expectedGraphEdgeCount;
    private static OstisScMemoryManager manager;

    @BeforeAll
    static void setUp() throws Exception {
        ScMemory memory = new SyncOstisScMemory(new URI("ws://localhost:8090/ws_json"));
        DefaultScContext context = new DefaultScContext(memory);
        memory.open();
        manager = new OstisScMemoryManager(context);

        actualGraphName = "test";
        expectedGraphEdgeCount = 28;
        rdfContent = """
                <?xml version="1.0"?>
                                                
                <rdf:RDF
                  xmlns="http://www.metaphacts.com/resource/"
                  xmlns:rdfs="http://www.w3.org/2000/01/rdf-shema#"
                  xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#">
                  <rdf:Description rdf:about="http://test#a">
                   <name>Belarus</name>
                   <Coutry.Capital rdf:resource="http://test#c"/>
                   <rdf:type rdf:resource="http://test#Country"/>
                  </rdf:Description>
                  <rdf:Description rdf:about="http://test#c">
                   <rdf:type rdf:resource="http://test#City"/>
                   <name>Minsk</name>
                  </rdf:Description>
                </rdf:RDF>
                """;
    }

    @AfterAll
    static void tearDown() throws Exception {
        manager.getScContext().getMemory().close();
    }

    @Test
    void convertRdf() throws RdfParseException {
        RdfToWinkConverter converter = new RdfToWinkConverterBasic();

        WinkGraph graph = converter.convertRdf(rdfContent, "test.rdf");
        int actualEdgesCount = graph.getEdges().size();

        assertEquals(expectedGraphEdgeCount, actualEdgesCount);
    }

    @Test
    @Order(1)
    void loadGraphToMemory() throws RdfParseException {
        RdfToWinkConverter converter = new RdfToWinkConverterBasic();
        WinkGraph graph = converter.convertRdf(rdfContent, "test.rdf");
        manager.uploadContour(actualGraphName, graph);
    }

    @Test
    @Order(2)
    void unloadGraphFromMemory() throws ScMemoryException {
        manager.unload(actualGraphName);
    }
}