package org.wink.module.http.scg.controller;

import org.ostis.scmemory.model.exception.ScMemoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.wink.engine.analyser.autocompleter.Autocompleter;
import org.wink.engine.model.graph.impl.DefaultWinkGraphHeader;
import org.wink.engine.model.graph.interfaces.WinkGraph;
import org.wink.engine.scmemory.ScMemoryManager;
import org.wink.module.http.scg.dto.WinkGraphDto;
import org.wink.module.http.scg.mapper.ScJsonMapper;

import java.util.List;

import static org.wink.module.http.scg.controller.ControllerExceptionHandler.createExceptionResponseWithMessageAndCode;

/**
 * @author Mikita Torap
 * @since 0.0.1
 */
@RestController
@RequestMapping("/element")
public class ScElementController {

    private static final int INTERNAL_SERVER_ERROR_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();
    private final ScJsonMapper scJsonMapper;
    private final Autocompleter autocompleter;
    private final ScMemoryManager manager;

    @Autowired
    public ScElementController(ScJsonMapper scJsonMapper, Autocompleter autocompleter, ScMemoryManager manager) {
        this.scJsonMapper = scJsonMapper;
        this.autocompleter = autocompleter;
        this.manager = manager;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody WinkGraphDto winkGraphDto) {
        WinkGraph graph = scJsonMapper.map(winkGraphDto.getScElements(),
                new DefaultWinkGraphHeader(winkGraphDto.getFilename(), winkGraphDto.getNativeFormat()));

        ResponseEntity<?> responseEntity;
        try {
            responseEntity = new ResponseEntity<>(manager.uploadContour(winkGraphDto.getFilename(), graph), HttpStatus.CREATED);
        } catch (ScMemoryException e) {
            responseEntity = new ResponseEntity<>(
                    createExceptionResponseWithMessageAndCode(e.getMessage(), INTERNAL_SERVER_ERROR_CODE),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @GetMapping
    public ResponseEntity<?> autocomplete(@RequestParam String part, @RequestParam int limit) {
        List<String> autocompletionResult = autocompleter.search(part, limit);
        return new ResponseEntity<>(autocompletionResult, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam String name) {
        ResponseEntity<?> responseEntity;
        try {
            manager.unload(name);
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        } catch (ScMemoryException e) {
            responseEntity = new ResponseEntity<>(
                    createExceptionResponseWithMessageAndCode(e.getMessage(), INTERNAL_SERVER_ERROR_CODE),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }
}