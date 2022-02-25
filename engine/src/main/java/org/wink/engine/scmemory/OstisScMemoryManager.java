package org.wink.engine.scmemory;

import org.ostis.api.context.DefaultScContext;
import org.ostis.scmemory.model.element.ScElement;
import org.ostis.scmemory.model.element.edge.ScEdge;
import org.ostis.scmemory.model.exception.ScMemoryException;
import org.wink.engine.exceptions.CannotCreateEdgeException;
import org.wink.engine.exceptions.CannotCreateLinkException;
import org.wink.engine.exceptions.CannotCreateNodeException;
import org.wink.engine.exceptions.GraphDoesntExistException;
import org.wink.engine.exceptions.GraphWithThisNameAlreadyUploadedException;
import org.wink.engine.model.graph.impl.WinkEdge;
import org.wink.engine.model.graph.impl.WinkLink;
import org.wink.engine.model.graph.impl.WinkLinkFloat;
import org.wink.engine.model.graph.impl.WinkLinkInteger;
import org.wink.engine.model.graph.impl.WinkLinkString;
import org.wink.engine.model.graph.impl.WinkNode;
import org.wink.engine.model.graph.interfaces.WinkElement;
import org.wink.engine.model.graph.interfaces.WinkGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class OstisScMemoryManager implements ScMemoryManager {
    private final DefaultScContext context;
    private final Map<String, List<? extends ScEdge>> loaded = new HashMap<>();

    public OstisScMemoryManager(DefaultScContext context) throws Exception {
        this.context = context;
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

        //      ToDo performance
        //      This realisation is not the best one:
        //      each method call creates and sends a small query to the sc-machine.
        //      Is better to aggregate nodes and links in one query (createNodes and createLinks methods).
        //      But this problems has minor priority cause has effect only for big graphs (100+ nodes).
        List<WinkElement> elements = graph.getEdges().stream().flatMap(e -> Stream.of(e.getSource(), e.getTarget())).toList();
        for (WinkElement element : elements) {
            if (element instanceof WinkNode node) {
                try {
                    winkToSc.put(node, context.createNode(node.getType()));
                } catch (ScMemoryException e) {
                    throw new CannotCreateNodeException(e);
                }
            } else if (element instanceof WinkLink link) {
                try {
                    winkToSc.put(link, createWinkLink(link));
                } catch (ScMemoryException e) {
                    throw new CannotCreateLinkException(e);
                }
            }
        }

        List<ScEdge> edges = new ArrayList<>();
        for (WinkEdge edge : graph.getEdges()) {
            try {
                ScEdge resultEdge = context.createEdge(edge.getType(), winkToSc.get(edge.getSource()), winkToSc.get(edge.getTarget()));
                edges.add(resultEdge);
                winkToSc.put(edge, resultEdge);
            } catch (ScMemoryException e) {
                throw new CannotCreateEdgeException(e);
            }
        }
        loaded.put(name, edges);

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
