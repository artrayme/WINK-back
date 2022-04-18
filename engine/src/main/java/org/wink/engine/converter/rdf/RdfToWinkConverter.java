package org.wink.engine.converter.rdf;

import org.wink.engine.exceptions.RdfParseException;
import org.wink.engine.model.graph.interfaces.WinkGraph;

/**
 * @author Mikhail Krautsou
 * @since 0.0.1
 */
public interface RdfToWinkConverter {
    WinkGraph convertRdf(String rdfFileContent, String rdfFileName) throws RdfParseException;
}
