package org.wink.module.http.scg.controller;

import org.ostis.scmemory.model.exception.ScMemoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.wink.engine.converter.rdf.RdfToWinkConverter;
import org.wink.engine.exceptions.RdfParseException;
import org.wink.engine.model.graph.interfaces.WinkGraph;
import org.wink.engine.scmemory.ScMemoryManager;
import org.wink.module.http.scg.dto.ExceptionResponseDto;
import org.wink.module.http.scg.dto.RdfDto;

import java.util.Base64;

import static org.wink.module.http.scg.controller.ControllerExceptionHandler.createExceptionResponseWithMessageAndCode;

/**
 * @author Mikhail Krautsou
 * @since 0.0.1
 */
@RestController
@RequestMapping("/rdf")
public class RdfController {

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
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedBytes = decoder.decode(rdfDto.getRdfContent());
        String content = new String(decodedBytes);
        System.out.println(content);
        try {
            WinkGraph graph = rdfToWinkConverter.convertRdf(content, fileName);
            return new ResponseEntity<>(scMemoryManager.uploadContour(fileName, graph), HttpStatus.CREATED);
        } catch (ScMemoryException exception) {
            return new ResponseEntity<>(
                    createExceptionResponseWithMessageAndCode(exception.getMessage(), INTERNAL_SERVER_ERROR_CODE),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RdfParseException exception) {
            return new ResponseEntity<>(
                    createExceptionResponseWithMessageAndCode(exception.getMessage(), BAD_REQUEST_CODE),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam String name) {
        try {
            scMemoryManager.unload(name);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ScMemoryException e) {
            return new ResponseEntity<>(
                    createExceptionResponseWithMessageAndCode(e.getMessage(), INTERNAL_SERVER_ERROR_CODE),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
