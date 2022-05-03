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
import org.wink.engine.model.graph.impl.*;
import org.wink.engine.model.graph.interfaces.WinkElement;
import org.wink.engine.model.graph.interfaces.WinkGraph;
import org.wink.engine.model.graph.interfaces.WinkGraphHeader;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Mikhail Krautsou
 * @since 0.0.1
 */
public class RdfToWinkConverterBasic implements RdfToWinkConverter {
    public static final String RDF_FORMAT;
    private static final String RDF_SYNTAX_URI;
    private static final String RDF_SYNTAX_IDTF;
    private static final WinkIdtfiableWrapper literalContent;
    private static final WinkIdtfiableWrapper lri;

    static {
        RDF_FORMAT = "RDF/XML";
        RDF_SYNTAX_URI = "http://www.w3.org/1999/02/22-rdf-syntax-ns#([a-zA-Z]+)";
        RDF_SYNTAX_IDTF = "rdf_nrel_";
        literalContent = new WinkIdtfiableWrapper(new WinkNode(NodeType.CONST_NO_ROLE), "nrel_literal_content");
        lri = new WinkIdtfiableWrapper(new WinkNode(NodeType.CONST_NO_ROLE), "nrel_lri");
    }

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
            WinkElement predicateWink = processPredicate(predicate, rdfElements, graph);
            WinkElement objectWink = processObject(object, rdfElements, graph);
            WinkEdge subjectObjectEdge = new WinkEdge(EdgeType.D_COMMON_CONST, subjectWink, objectWink);
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
        WinkElement subjectNode = new WinkNode(NodeType.CONST);
        rdfElements.put(subject, subjectNode);
        String subjectURI = subject.getURI();
        if (subjectURI != null) {
            WinkElement subjectLink = new WinkLinkString(LinkType.LINK, subjectURI);
            WinkEdge subjectEdge = new WinkEdge(EdgeType.D_COMMON_CONST, subjectNode, subjectLink);
            WinkEdge idtfSubjectEdge = new WinkEdge(EdgeType.ACCESS_CONST_POS_PERM, lri, subjectEdge);
            winkGraph.addEdge(subjectEdge);
            winkGraph.addEdge(idtfSubjectEdge);
        }
        return subjectNode;
    }

    private WinkElement processPredicate(Property predicate, HashMap<Object, WinkElement> rdfElements, WinkGraph winkGraph) {
        if (rdfElements.containsKey(predicate)) {
            return rdfElements.get(predicate);
        }
        String predicateUri = predicate.getURI();
        WinkElement predicateNode = new WinkNode(NodeType.CONST_NO_ROLE);
        Matcher rdfSyntaxMatcher = Pattern.compile(RDF_SYNTAX_URI).matcher(predicateUri);
        if (rdfSyntaxMatcher.matches()) {
            String idtf = RDF_SYNTAX_IDTF + rdfSyntaxMatcher.group(1);
            predicateNode = new WinkIdtfiableWrapper(predicateNode, idtf);
        }
        WinkElement predicateLink = new WinkLinkString(LinkType.LINK, predicateUri);
        WinkEdge predicateEdge = new WinkEdge(EdgeType.D_COMMON_CONST, predicateNode, predicateLink);
        WinkEdge idtfPredicateEdge = new WinkEdge(EdgeType.ACCESS_CONST_POS_PERM, lri, predicateEdge);
        winkGraph.addEdge(predicateEdge);
        winkGraph.addEdge(idtfPredicateEdge);
        rdfElements.put(predicate, predicateNode);
        return predicateNode;
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
            WinkEdge objectEdge = new WinkEdge(EdgeType.D_COMMON_CONST, objectNode, objectLink);
            WinkEdge idtfObjectEdge = new WinkEdge(EdgeType.ACCESS_CONST_POS_PERM, literalContent, objectEdge);
            winkGraph.addEdge(objectEdge);
            winkGraph.addEdge(idtfObjectEdge);
            return objectNode;
        }
    }
}