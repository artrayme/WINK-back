package org.wink.engine.converter.rdf;

import org.wink.engine.model.graph.interfaces.WinkGraph;

public interface RdfToWinkConverter {
    WinkGraph convertRdf(String rdfFileContent, String rdfFileName);
}
