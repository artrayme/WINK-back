package org.wink.engine.scmemory;

import org.ostis.api.context.DefaultScContext;
import org.ostis.scmemory.model.element.ScElement;
import org.ostis.scmemory.model.element.edge.EdgeType;
import org.ostis.scmemory.model.element.edge.ScEdge;
import org.ostis.scmemory.model.element.link.ScLink;
import org.ostis.scmemory.model.element.node.NodeType;
import org.ostis.scmemory.model.element.node.ScNode;
import org.ostis.scmemory.model.exception.ScMemoryException;
import org.wink.engine.exceptions.CannotCreateEdgeException;
import org.wink.engine.exceptions.CannotCreateIdentifiableElementException;
import org.wink.engine.exceptions.CannotCreateLinkException;
import org.wink.engine.exceptions.CannotCreateNodeException;
import org.wink.engine.exceptions.GraphDoesntExistException;
import org.wink.engine.exceptions.GraphWithThisNameAlreadyUploadedException;
import org.wink.engine.model.graph.impl.WinkEdge;
import org.wink.engine.model.graph.impl.WinkIdtfiableWrapper;
import org.wink.engine.model.graph.impl.WinkLink;
import org.wink.engine.model.graph.impl.WinkLinkFloat;
import org.wink.engine.model.graph.impl.WinkLinkInteger;
import org.wink.engine.model.graph.impl.WinkLinkString;
import org.wink.engine.model.graph.impl.WinkNode;
import org.wink.engine.model.graph.interfaces.WinkElement;
import org.wink.engine.model.graph.interfaces.WinkGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class OstisScMemoryManager implements ScMemoryManager {
    private final static String NREL_MAIN_IDTF = "nrel_main_idtf";
    private final static String STRUCT_POSTFIX = "_struct";
    private final DefaultScContext context;
    private final ScNode NREL_MAIN_IDTF_NODE;
    private final Map<String, List<? extends ScEdge>> loaded = new HashMap<>();

    public OstisScMemoryManager(DefaultScContext context) throws Exception {
        this.context = context;
        NREL_MAIN_IDTF_NODE = context.findKeynode(NREL_MAIN_IDTF).get();
    }

    @Override
    public DefaultScContext getScContext() {
        return context;
    }

    @Override
    public void upload(String name, WinkGraph graph) throws GraphWithThisNameAlreadyUploadedException, CannotCreateNodeException, CannotCreateLinkException, CannotCreateEdgeException {
        if (loaded.containsKey(name))
            throw new GraphWithThisNameAlreadyUploadedException("You cannot upload 2 graphs with same name");

        Map<WinkElement, ScElement> winkToSc = new HashMap<>();

        /*
              ToDo performance
              This realisation is not the best one:
              each method call creates and sends a small query to the sc-machine.
              It is better to aggregate nodes and links in one query (createNodes and createLinks methods).
              But this problems has minor priority cause has effect only for big graphs (100+ nodes).
        */
        graph.getEdges()
                .stream()
                .flatMap(e -> Stream.of(e.getSource(), e.getTarget()))
                .forEach(element -> {
                    createElement(element).forEach(scElement -> winkToSc.putIfAbsent(element, scElement));
                });

        List<ScEdge> edges = new ArrayList<>();
        for (WinkEdge edge : graph.getEdges()) {
            try {
                ScElement source = winkToSc.get(edge.getSource());
                ScElement target = winkToSc.get(edge.getTarget());
                ScEdge resultEdge = context.createEdge(edge.getType(), source, target);
                edges.add(resultEdge);
                winkToSc.put(edge, resultEdge);
                if (source instanceof ScLink || target instanceof ScLink) {
                    context.createEdge(EdgeType.ACCESS, NREL_MAIN_IDTF_NODE, resultEdge);
                }
            } catch (ScMemoryException e) {
                throw new CannotCreateEdgeException(e);
            }
        }
        loaded.put(name, edges);

    }

    @Override
    public String uploadContour(String name, WinkGraph graph) throws GraphWithThisNameAlreadyUploadedException, CannotCreateNodeException, CannotCreateLinkException, CannotCreateEdgeException {
        String structIdtf = name + STRUCT_POSTFIX;
        WinkIdtfiableWrapper structNode = new WinkIdtfiableWrapper(new WinkNode(NodeType.STRUCT), structIdtf);
        List<WinkEdge> contourEdges = new ArrayList<>(graph.getEdges().size() * 3);
        graph.getEdges().forEach(e -> {
                    contourEdges.add(new WinkEdge(EdgeType.ACCESS_CONST_POS_PERM, structNode, e.getSource()));
                    contourEdges.add(new WinkEdge(EdgeType.ACCESS_CONST_POS_PERM, structNode, e.getTarget()));
                    contourEdges.add(new WinkEdge(EdgeType.ACCESS_CONST_POS_PERM, structNode, e));
                }
        );

        contourEdges.forEach(graph::addEdge);
        upload(name, graph);
        return structIdtf;
    }

    @Override
    public void unload(String name) throws GraphDoesntExistException, ScMemoryException {
        if (!loaded.containsKey(name)) {
            throw new GraphDoesntExistException("The graph with this name does not exist");
        } else {
            context.deleteElements(loaded.get(name).stream().flatMap(e -> Stream.of(e, e.getSource(), e.getTarget())));
            loaded.remove(name);
        }

    }

    @Override
    public List<String> unloadAll() throws ScMemoryException {
        context.deleteElements(loaded.values().stream().flatMap(Collection::stream).flatMap(e -> Stream.of(e.getSource(), e.getTarget(), e)));
        List<String> graphs = loaded.keySet().stream().toList();
        loaded.clear();
        return graphs;
    }

    private List<ScElement> createElement(WinkElement element) {
        if (element instanceof WinkNode node) {
            try {
                return Collections.singletonList(context.createNode(node.getType()));
            } catch (ScMemoryException e) {
                throw new CannotCreateNodeException(e);
            }
        } else if (element instanceof WinkLink link) {
            try {
                return Collections.singletonList(createWinkLink(link));
            } catch (ScMemoryException e) {
                throw new CannotCreateLinkException(e);
            }
        } else if (element instanceof WinkIdtfiableWrapper wrapper) {
            try {
                return Collections.singletonList(context.resolveKeynode(wrapper.getIdtf(), NodeType.STRUCT));
            } catch (ScMemoryException e) {
                throw new CannotCreateIdentifiableElementException(e);
            }
        } else if (element instanceof WinkEdge edge) {
            var source = createElement(edge.getSource());
            var target = createElement(edge.getTarget());
            try {
                var resultEdge = context.createEdge(edge.getType(), source.get(0), target.get(0));
                List<ScElement> result = new ArrayList<>();
                result.add(resultEdge);
                result.addAll(source);
                result.addAll(target);
                return result;
            } catch (ScMemoryException e) {
                e.printStackTrace();
            }
        }
        throw new IllegalArgumentException(String.valueOf(element));
    }

    private ScElement createWinkLink(WinkLink element) throws ScMemoryException {
        if (element instanceof WinkLinkInteger linkInteger) {
            return context.createIntegerLink(linkInteger.getType(), linkInteger.getContent());
        } else if (element instanceof WinkLinkFloat linkFloat) {
            return context.createFloatLink(linkFloat.getType(), linkFloat.getContent());
        } else if (element instanceof WinkLinkString stringLink) {
            return context.createStringLink(stringLink.getType(), stringLink.getContent());
        } else {
            throw new RuntimeException("Unsupported WinkElement type");
        }
    }
}
