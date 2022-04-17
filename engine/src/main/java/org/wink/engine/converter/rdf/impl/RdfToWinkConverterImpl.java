package org.wink.engine.converter.rdf.impl;

import org.apache.commons.io.IOUtils;
import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.*;
import org.ostis.scmemory.model.element.edge.EdgeType;
import org.ostis.scmemory.model.element.link.LinkType;
import org.ostis.scmemory.model.element.node.NodeType;
import org.wink.engine.converter.rdf.RdfToWinkConverter;
import org.wink.engine.model.graph.impl.*;
import org.wink.engine.model.graph.interfaces.WinkElement;
import org.wink.engine.model.graph.interfaces.WinkGraph;
import org.wink.engine.model.graph.interfaces.WinkGraphHeader;

import java.util.HashMap;

public class RdfToWinkConverterImpl implements RdfToWinkConverter {
    private static final String UTF8_ENCODING = "UTF-8";
    private static final String RDF_FORMAT = "RDF/XML";

    @Override
    public WinkGraph convertRdf(String rdfFileContent, String rdfFileName) {
        Model model = ModelFactory.createDefaultModel();
        model.read(IOUtils.toInputStream(rdfFileContent, UTF8_ENCODING), null);

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
            WinkEdge subjectObjectEdge = new WinkEdge(EdgeType.ACCESS, subjectWink, objectWink);
            graph.addEdge(subjectObjectEdge);
            WinkEdge predicateEdge = new WinkEdge(EdgeType.ACCESS, predicateWink, subjectObjectEdge);
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
            WinkEdge subjectEdge = new WinkEdge(EdgeType.ACCESS, subjectLink, subjectNode);
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
            WinkElement objectNode = new WinkNode(NodeType.NODE);
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
            WinkEdge objectEdge = new WinkEdge(EdgeType.ACCESS, objectLink, objectNode);
            winkGraph.addEdge(objectEdge);
            return objectNode;
        }
    }
}