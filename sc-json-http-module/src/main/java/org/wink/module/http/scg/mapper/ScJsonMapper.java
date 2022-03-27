package org.wink.module.http.scg.mapper;

import org.ostis.scmemory.model.element.edge.EdgeType;
import org.ostis.scmemory.model.element.link.LinkType;
import org.ostis.scmemory.model.element.node.NodeType;
import org.ostis.scmemory.websocketmemory.util.internal.ScTypesMap;
import org.springframework.stereotype.Component;
import org.wink.engine.model.graph.impl.DefaultWinkGraph;
import org.wink.engine.model.graph.impl.WinkEdge;
import org.wink.engine.model.graph.impl.WinkLink;
import org.wink.engine.model.graph.impl.WinkLinkFloat;
import org.wink.engine.model.graph.impl.WinkLinkInteger;
import org.wink.engine.model.graph.impl.WinkLinkString;
import org.wink.engine.model.graph.impl.WinkNode;
import org.wink.engine.model.graph.interfaces.WinkElement;
import org.wink.engine.model.graph.interfaces.WinkGraph;
import org.wink.engine.model.graph.interfaces.WinkGraphHeader;
import org.wink.module.http.scg.dto.ScElementDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Mikita Torap
 * @since 0.0.1
 */
@Component
public class ScJsonMapper {

    private static final String NODE = "node";
    ;
    private static final String EDGE = "edge";
    private static final String LINK = "link";
    private static final String UNKNOWN_WINK_LINK_CONTENT_TYPE = "Unknown wink link content type encountered. " +
            "Wink link cannot be created!";
    private final Map<Integer, Object> scElementTypes = ScTypesMap.INSTANCE.getTypes();

    public WinkGraph map(List<ScElementDto> scElements, WinkGraphHeader header) {
        List<WinkElement> winkElements = new ArrayList<>();
        WinkGraph resultGraph = new DefaultWinkGraph(header);
        for (ScElementDto scElement : scElements) {
            String element = scElement.getElement();
            Integer type = scElement.getType();
            Object content = scElement.getContent();

            switch (element) {
                case NODE -> {
                    WinkNode winkNode = getWinkNode(type);
                    winkElements.add(winkNode);
                }
                case LINK -> {
                    WinkLink winkLink = getWinkLink(type, content);
                    winkElements.add(winkLink);
                }
                case EDGE -> {
                    WinkEdge winkEdge = getWinkEdge(scElement, winkElements, type);
                    winkElements.add(winkEdge);
                    resultGraph.addEdge(winkEdge);
                }
            }
        }

        return resultGraph;
    }

    private WinkNode getWinkNode(Integer type) {
        NodeType nodeType = (NodeType) scElementTypes.get(type);
        return new WinkNode(nodeType);
    }

    private WinkLink getWinkLink(Integer type, Object content) {
        LinkType linkType = (LinkType) scElementTypes.get(type);

        if (content instanceof String) {
            return new WinkLinkString(linkType, (String) content);
        } else if (content instanceof Float) {
            return new WinkLinkFloat(linkType, (Float) content);
        } else if (content instanceof Integer) {
            return new WinkLinkInteger(linkType, (Integer) content);
        } else {
            throw new IllegalArgumentException(UNKNOWN_WINK_LINK_CONTENT_TYPE);
        }
    }

    private WinkEdge getWinkEdge(ScElementDto scElement, List<WinkElement> winkElements, Integer type) {
        EdgeType edgeType = (EdgeType) scElementTypes.get(type);

        int sourceValue = scElement.getSource().getValue();
        int targetValue = scElement.getTarget().getValue();

        WinkElement source = winkElements.get(sourceValue);
        WinkElement target = winkElements.get(targetValue);

        return new WinkEdge(edgeType, source, target);
    }
}
