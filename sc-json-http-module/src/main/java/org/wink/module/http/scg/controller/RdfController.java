package org.wink.module.http.scg.controller;

import org.ostis.scmemory.model.exception.ScMemoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wink.engine.converter.rdf.RdfToWinkConverter;
import org.wink.engine.exceptions.RdfParseException;
import org.wink.engine.model.graph.interfaces.WinkGraph;
import org.wink.engine.scmemory.ScMemoryManager;
import org.wink.module.http.scg.dto.ExceptionResponseDto;
import org.wink.module.http.scg.dto.RdfDto;

/**
 * @author Mikhail Krautsou
 * @since 0.0.1
 */
@RestController
@RequestMapping("/rdf")
public class RdfController {
    private static final String SC_MEMORY_EXCEPTION = "The request couldn't be processed due to the problems with ScMemory";
    private static final int INTERNAL_SERVER_ERROR_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();
    private static final int BAD_REQUEST_CODE = HttpStatus.BAD_REQUEST.value();
    private final ScMemoryManager scMemoryManager;
    private final RdfToWinkConverter rdfToWinkConverter;

    @Autowired
    public RdfController(ScMemoryManager scMemoryManager, RdfToWinkConverter rdfToWinkConverter) {
        this.scMemoryManager = scMemoryManager;
        this.rdfToWinkConverter = rdfToWinkConverter;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody RdfDto rdfDto) {
        String fileName = rdfDto.getRdfFileName();
        String content = rdfDto.getRdfContent();
        try {
            WinkGraph graph = rdfToWinkConverter.convertRdf(content, fileName);
            scMemoryManager.upload(fileName, graph);
        } catch (ScMemoryException exception) {
            ExceptionResponseDto exceptionResponse = new ExceptionResponseDto(SC_MEMORY_EXCEPTION, INTERNAL_SERVER_ERROR_CODE);
            return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RdfParseException exception) {
            ExceptionResponseDto exceptionResponse = new ExceptionResponseDto(exception.getMessage(), BAD_REQUEST_CODE);
            return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
