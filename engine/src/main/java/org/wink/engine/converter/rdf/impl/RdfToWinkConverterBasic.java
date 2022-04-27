package org.wink.engine.converter.rdf.impl;

import org.apache.commons.io.IOUtils;
import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.ostis.scmemory.model.element.edge.EdgeType;
import org.ostis.scmemory.model.element.link.LinkType;
import org.ostis.scmemory.model.element.node.NodeType;
import org.wink.engine.converter.rdf.RdfToWinkConverter;
import org.wink.engine.converter.rdf.util.RdfValidator;
import org.wink.engine.exceptions.RdfParseException;
import org.wink.engine.model.graph.impl.DefaultWinkGraph;
import org.wink.engine.model.graph.impl.DefaultWinkGraphHeader;
import org.wink.engine.model.graph.impl.WinkEdge;
import org.wink.engine.model.graph.impl.WinkLink;
import org.wink.engine.model.graph.impl.WinkLinkFloat;
import org.wink.engine.model.graph.impl.WinkLinkInteger;
import org.wink.engine.model.graph.impl.WinkLinkString;
import org.wink.engine.model.graph.impl.WinkNode;
import org.wink.engine.model.graph.interfaces.WinkElement;
import org.wink.engine.model.graph.interfaces.WinkGraph;
import org.wink.engine.model.graph.interfaces.WinkGraphHeader;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * @author Mikhail Krautsou
 * @since 0.0.1
 */
public class RdfToWinkConverterBasic implements RdfToWinkConverter {
    public static final String RDF_FORMAT = "RDF/XML";

    @Override
    public WinkGraph convertRdf(String rdfFileContent, String rdfFileName) throws RdfParseException {
        if (!RdfValidator.validate(rdfFileContent)) {
            throw new RdfParseException("incorrect rdf/xml data");
        }

        Model model = ModelFactory.createDefaultModel();
        model.read(IOUtils.toInputStream(rdfFileContent, StandardCharsets.UTF_8), null);

        WinkGraphHeader graphHeader = new DefaultWinkGraphHeader(rdfFileName, RDF_FORMAT);
        WinkGraph graph = new DefaultWinkGraph(graphHeader);
        HashMap<Object, WinkElement> rdfElements = new HashMap<>();

        StmtIterator iterator = model.listStatements();
        while (iterator.hasNext()) {
            Statement statement = iterator.nextStatement();
            Resource subject = statement.getSubject();
            Property predicate = statement.getPredicate();
            RDFNode object = statement.getObject();

            WinkElement subjectWink = processSubject(subject, rdfElements, graph);
            WinkElement predicateWink = processPredicate(predicate, rdfElements);
            WinkElement objectWink = processObject(object, rdfElements, graph);
            WinkEdge subjectObjectEdge = new WinkEdge(EdgeType.ACCESS_CONST_POS_PERM, subjectWink, objectWink);
            graph.addEdge(subjectObjectEdge);
            WinkEdge predicateEdge = new WinkEdge(EdgeType.ACCESS_CONST_POS_PERM, predicateWink, subjectObjectEdge);
            graph.addEdge(predicateEdge);
        }

        return graph;
    }

    private WinkElement processSubject(Resource subject, HashMap<Object, WinkElement> rdfElements, WinkGraph winkGraph) {
        if (rdfElements.containsKey(subject)) {
            return rdfElements.get(subject);
        }
        WinkElement subjectNode = new WinkNode(NodeType.NODE);
        rdfElements.put(subject, subjectNode);
        String subjectURI = subject.getURI();
        if (subjectURI != null) {
            WinkElement subjectLink = new WinkLinkString(LinkType.LINK, subjectURI);
            WinkEdge subjectEdge = new WinkEdge(EdgeType.ACCESS_CONST_POS_PERM, subjectLink, subjectNode);
            winkGraph.addEdge(subjectEdge);
        }
        return subjectNode;
    }

    private WinkElement processPredicate(Property predicate, HashMap<Object, WinkElement> rdfElements) {
        if (rdfElements.containsKey(predicate)) {
            return rdfElements.get(predicate);
        }
        WinkElement predicateLink = new WinkLinkString(LinkType.LINK, predicate.getURI());
        rdfElements.put(predicate, predicateLink);
        return predicateLink;
    }

    private WinkElement processObject(RDFNode object, HashMap<Object, WinkElement> rdfElements, WinkGraph winkGraph) {
        if (rdfElements.containsKey(object)) {
            return rdfElements.get(object);
        }
        if (object instanceof Resource) {
            return processSubject((Resource) object, rdfElements, winkGraph);
        } else {
            WinkElement objectNode = new WinkNode(NodeType.CONST);
            rdfElements.put(object, objectNode);
            Literal literal = object.asLiteral();
            RDFDatatype datatype = literal.getDatatype();
            WinkLink objectLink;
            if (datatype.equals(XSDDatatype.XSDint)) {
                objectLink = new WinkLinkInteger(LinkType.LINK, literal.getInt());
            } else if (datatype.equals(XSDDatatype.XSDfloat)) {
                objectLink = new WinkLinkFloat(LinkType.LINK, literal.getFloat());
            } else {
                objectLink = new WinkLinkString(LinkType.LINK, literal.getString());
            }
            WinkEdge objectEdge = new WinkEdge(EdgeType.ACCESS_CONST_POS_PERM, objectLink, objectNode);
            winkGraph.addEdge(objectEdge);
            return objectNode;
        }
    }
}