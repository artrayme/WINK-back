package org.wink.module.http.scg.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ostis.scmemory.model.element.edge.EdgeType;
import org.ostis.scmemory.model.element.link.LinkType;
import org.ostis.scmemory.model.element.node.NodeType;
import org.wink.engine.model.graph.impl.DefaultWinkGraph;
import org.wink.engine.model.graph.impl.DefaultWinkGraphHeader;
import org.wink.engine.model.graph.impl.WinkEdge;
import org.wink.engine.model.graph.impl.WinkLinkFloat;
import org.wink.engine.model.graph.impl.WinkLinkInteger;
import org.wink.engine.model.graph.impl.WinkLinkString;
import org.wink.engine.model.graph.impl.WinkNode;
import org.wink.engine.model.graph.interfaces.WinkElement;
import org.wink.engine.model.graph.interfaces.WinkGraph;
import org.wink.engine.model.graph.interfaces.WinkGraphHeader;
import org.wink.module.http.scg.dto.ScAdjacentElementDto;
import org.wink.module.http.scg.dto.ScElementDto;

import java.util.Arrays;
import java.util.List;

public class ScJsonMapperTest {

    private static ScJsonMapper scJsonMapper;

    private static WinkGraph expectedGraph;
    private static WinkGraphHeader winkGraphHeader;
    private static ScAdjacentElementDto firstElementReference;
    private static ScAdjacentElementDto secondElementReference;

    @BeforeAll
    public static void setUp() {
        scJsonMapper = new ScJsonMapper();
        winkGraphHeader = new DefaultWinkGraphHeader("graph1", "txt");
        firstElementReference = new ScAdjacentElementDto("ref", 1);
        secondElementReference = new ScAdjacentElementDto("ref", 2);
    }

    @BeforeEach
    public void setEachUp() {
        expectedGraph = new DefaultWinkGraph(winkGraphHeader);
    }

    @Test
    public void mapNodesConnectedByOneEdge() {
        ScElementDto firstNode = new ScElementDto("node", 1);
        ScElementDto secondNode = new ScElementDto("node", 1);
        ScElementDto edge = new ScElementDto("edge", 4);

        edge.setSource(firstElementReference);
        edge.setTarget(secondElementReference);

        WinkElement winkNode = new WinkNode(NodeType.NODE);
        WinkEdge winkEdge = new WinkEdge(EdgeType.U_COMMON, winkNode, winkNode);

        expectedGraph.addEdge(winkEdge);

        List<ScElementDto> elementsToMap = Arrays.asList(firstNode, secondNode, edge);

        WinkGraph actualGraph = scJsonMapper.map(elementsToMap, winkGraphHeader);

        assert(expectedGraph).equals(actualGraph);
    }

    @Test
    public void mapStringLinksConnectedByOneEdge() {
        ScElementDto firstStringLink = new ScElementDto("link", 2, "first string link");
        ScElementDto secondStringLink = new ScElementDto("link", 2, "second string link");
        ScElementDto edge = new ScElementDto("edge", 8);

        edge.setSource(firstElementReference);
        edge.setTarget(secondElementReference);

        WinkElement source = new WinkLinkString(LinkType.LINK, "first string link");
        WinkElement target = new WinkLinkString(LinkType.LINK, "second string link");
        WinkEdge winkEdge = new WinkEdge(EdgeType.D_COMMON, source, target);

        expectedGraph.addEdge(winkEdge);

        List<ScElementDto> elementsToMap = Arrays.asList(firstStringLink, secondStringLink, edge);

        WinkGraph actualGraph = scJsonMapper.map(elementsToMap, winkGraphHeader);

        assert(expectedGraph).equals(actualGraph);
    }

    @Test
    public void mapFloatLinksConnectedByOneEdge() {
        ScElementDto firstFloatLink = new ScElementDto("link", 2, 45.5f);
        ScElementDto secondFloatLink = new ScElementDto("link", 2, 10.1f);
        ScElementDto edge = new ScElementDto("edge", 36);

        edge.setSource(firstElementReference);
        edge.setTarget(secondElementReference);

        WinkElement source = new WinkLinkFloat(LinkType.LINK, 45.5f);
        WinkElement target = new WinkLinkFloat(LinkType.LINK, 10.1f);
        WinkEdge winkEdge = new WinkEdge(EdgeType.U_COMMON_CONST, source, target);

        expectedGraph.addEdge(winkEdge);

        List<ScElementDto> elementsToMap = Arrays.asList(firstFloatLink, secondFloatLink, edge);

        WinkGraph actualGraph = scJsonMapper.map(elementsToMap, winkGraphHeader);

        assert(expectedGraph).equals(actualGraph);
    }

    @Test
    public void mapIntegerLinksConnectedByOneEdge() {
        ScElementDto firstIntegerLink = new ScElementDto("link", 2, 59);
        ScElementDto secondIntegerLink = new ScElementDto("link", 2, 95);
        ScElementDto edge = new ScElementDto("edge", 40);

        edge.setSource(firstElementReference);
        edge.setTarget(secondElementReference);

        WinkElement source = new WinkLinkInteger(LinkType.LINK, 59);
        WinkElement target = new WinkLinkInteger(LinkType.LINK, 95);
        WinkEdge winkEdge = new WinkEdge(EdgeType.D_COMMON_CONST, source, target);

        expectedGraph.addEdge(winkEdge);

        List<ScElementDto> elementsToMap = Arrays.asList(firstIntegerLink, secondIntegerLink, edge);

        WinkGraph actualGraph = scJsonMapper.map(elementsToMap, winkGraphHeader);

        assert(expectedGraph).equals(actualGraph);
    }

    @Test
    public void mapUnknownElement() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
           ScElementDto unknownElement = new ScElementDto("unknown", 1);
           List<ScElementDto> elementsToMap = List.of(unknownElement);

           scJsonMapper.map(elementsToMap, winkGraphHeader);
        });
    }

    @Test
    public void mapLinkWithUnknownContentType() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Object unknownContent = new Object();
            ScElementDto linkWithUnknownContentType = new ScElementDto("link", 2, unknownContent);
            List<ScElementDto> elementsToMap = List.of(linkWithUnknownContentType);

            scJsonMapper.map(elementsToMap, winkGraphHeader);
        });
    }
}
