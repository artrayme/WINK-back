package org.wink.engine.converter.rdf.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RdfValidatorTest {
    private static String correct;
    private static String incorrect;

    @BeforeAll
    static void setUp() {
        correct = """
                <?xml version="1.0"?>
                                
                <rdf:RDF
                xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                xmlns:cd="http://www.recshop.fake/cd#">
                                
                <rdf:Description
                rdf:about="http://www.recshop.fake/cd/Beatles">
                  <cd:format>
                    <rdf:Alt>
                      <rdf:li>CD</rdf:li>
                    </rdf:Alt>
                  </cd:format>
                </rdf:Description>
                                
                </rdf:RDF>
                """;
        incorrect = """
                <?xml version="1.0"?>
                                
                <rdf:RDF
                xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                xmlns:cd="http://www.recshop.fake/cd#">
                                
                <rdf:Description
                rdf:about="http://www.recshop.fake/cd/Beatles">
                  <cd:format>
                    <rdf:Alt>
                      <rdfli>CD</rdf:li>
                    </rdf:Alt>
                  </cd:format>
                </rdf:Description>
                                
                </rdf:RDF>
                """;
    }

    @Test
    void validatePositiveCase() {
        boolean actual = RdfValidator.validate(correct);
        assertTrue(actual);
    }

    @Test
    void validateNegativeCase() {
        boolean actual = RdfValidator.validate(incorrect);
        assertFalse(actual);
    }
}