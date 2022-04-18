package org.wink.engine.converter.rdf.util;

import org.apache.commons.io.IOUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import java.nio.charset.StandardCharsets;

public class RdfValidator {

    public static boolean validate(String rdfContent) {
        try {
            Model model = ModelFactory.createDefaultModel();
            model.read(IOUtils.toInputStream(rdfContent, StandardCharsets.UTF_8), null);
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }
}
