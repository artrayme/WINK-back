package org.wink.engine.scmemory;

import org.ostis.api.context.DefaultScContext;
import org.ostis.scmemory.model.exception.ScMemoryException;
import org.wink.engine.exceptions.CannotCreateEdgeException;
import org.wink.engine.exceptions.CannotCreateLinkException;
import org.wink.engine.exceptions.CannotCreateNodeException;
import org.wink.engine.exceptions.GraphDoesntExistException;
import org.wink.engine.exceptions.GraphWithThisNameAlreadyUploadedException;
import org.wink.engine.model.graph.interfaces.WinkGraph;

import java.util.List;

public interface ScMemoryManager {

    DefaultScContext getScContext();

    void upload(String name, WinkGraph graph) throws ScMemoryException, GraphWithThisNameAlreadyUploadedException, CannotCreateNodeException, CannotCreateLinkException, CannotCreateEdgeException;

    String uploadContour(String name, WinkGraph graph) throws ScMemoryException, GraphWithThisNameAlreadyUploadedException, CannotCreateNodeException, CannotCreateLinkException, CannotCreateEdgeException;

    void unload(String name) throws GraphDoesntExistException, ScMemoryException;

    List<String> unloadAll() throws ScMemoryException;

}
