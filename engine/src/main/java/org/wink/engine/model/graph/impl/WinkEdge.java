package org.wink.engine.model.graph.impl;

import org.ostis.scmemory.model.element.edge.EdgeType;
import org.wink.engine.model.graph.interfaces.WinkElement;
import org.wink.engine.model.graph.util.WinkElementIdGenerator;

import java.util.Objects;

/**
 * @author artrayme
 * @since 0.0.1
 */
public class WinkEdge implements WinkElement {
    private final Long id;
    private final EdgeType type;
    private final WinkElement source;
    private final WinkElement target;

    public WinkEdge(Long id, EdgeType type, WinkElement source, WinkElement target) {
        this.id = id;
        this.type = type;
        this.source = source;
        this.target = target;
    }

    public WinkEdge(EdgeType type, WinkElement source, WinkElement target) {
        this(WinkElementIdGenerator.getNextId(), type, source, target);
    }

    public EdgeType getType() {
        return type;
    }

    public WinkElement getSource() {
        return source;
    }

    public WinkElement getTarget() {
        return target;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, source, target);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WinkEdge winkEdge = (WinkEdge) o;
        return type == winkEdge.type && source.equals(winkEdge.source) && target.equals(winkEdge.target);
    }
}
