package org.wink.module.http.scg.controller;

import org.ostis.scmemory.model.exception.ScMemoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wink.engine.analyser.autocompleter.Autocompleter;
import org.wink.engine.exceptions.CannotCreateEdgeException;
import org.wink.engine.exceptions.CannotCreateLinkException;
import org.wink.engine.exceptions.CannotCreateNodeException;
import org.wink.engine.exceptions.GraphWithThisNameAlreadyUploadedException;
import org.wink.engine.model.graph.impl.DefaultWinkGraphHeader;
import org.wink.engine.model.graph.interfaces.WinkGraph;
import org.wink.engine.scmemory.ScMemoryManager;
import org.wink.module.http.scg.dto.AutocompletionDto;
import org.wink.module.http.scg.dto.ExceptionResponseDto;
import org.wink.module.http.scg.dto.ScElementDto;
import org.wink.module.http.scg.dto.WinkGraphDto;
import org.wink.module.http.scg.mapper.ScJsonMapper;

import java.util.List;

/**
 * @author Mikita Torap
 * @since 0.0.1
 */
@RestController
@RequestMapping("/element")
public class ScElementController {

    private final ScJsonMapper scJsonMapper;
    private final Autocompleter autocompleter;
    private final ScMemoryManager manager;

    private static final String SC_MEMORY_EXCEPTION = "The request couldn't be processed due to the problems with ScMemory";
    private static final int INTERNAL_SERVER_ERROR_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();

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

        try {
            manager.upload(winkGraphDto.getFilename(), graph);
        } catch (ScMemoryException e) {
            ExceptionResponseDto exceptionResponse = new ExceptionResponseDto(SC_MEMORY_EXCEPTION, INTERNAL_SERVER_ERROR_CODE);
            return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> autocomplete(@RequestBody AutocompletionDto autocompletionDto) {
        List<String> autocompletionResult = autocompleter.search(autocompletionDto.getPart(), autocompletionDto.getLimit());
        return new ResponseEntity<>(autocompletionResult, HttpStatus.OK);
    }
}