package org.wink.module.http.scg.controller;

import org.ostis.api.context.DefaultScContext;
import org.ostis.scmemory.model.exception.ScMemoryException;
import org.ostis.scmemory.model.pattern.ScPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wink.module.http.scg.dto.SearchingPatternDto;
import org.wink.module.http.scg.mapper.ScPatternJsonMapper;

import static org.wink.module.http.scg.controller.ControllerExceptionHandler.createExceptionResponseWithMessageAndCode;

/**
 * @author Mikita Torap
 * @since 0.0.1
 */
@RestController
@RequestMapping("/searcher")
public class ScPatternTripletController {

    private static final int INTERNAL_SERVER_ERROR_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();
    private final ScPatternJsonMapper scPatternJsonMapper;
    private final DefaultScContext context;

    @Autowired
    public ScPatternTripletController(ScPatternJsonMapper scPatternJsonMapper, DefaultScContext context) {
        this.scPatternJsonMapper = scPatternJsonMapper;
        this.context = context;
    }

    // todo properly return constructions
    @GetMapping
    public ResponseEntity<?> search(@RequestBody SearchingPatternDto triplets) {
        ScPattern scPattern;
        try {
            scPattern = scPatternJsonMapper.map(triplets.getPayload());
        } catch (ScMemoryException e) {
            return new ResponseEntity<>(
                    createExceptionResponseWithMessageAndCode(e.getMessage(), INTERNAL_SERVER_ERROR_CODE),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        ResponseEntity<?> responseEntity;
        try {
            var constructions = context.find(scPattern);
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ScMemoryException e) {
            responseEntity = new ResponseEntity<>(
                    createExceptionResponseWithMessageAndCode(e.getMessage(), INTERNAL_SERVER_ERROR_CODE),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }
}
