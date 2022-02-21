package org.wink.module.http.scg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wink.engine.analyser.autocompleter.Autocompleter;
import org.wink.engine.model.graph.interfaces.WinkElement;
import org.wink.module.http.scg.dto.AutocompletionDto;
import org.wink.module.http.scg.dto.ScElementDto;
import org.wink.module.http.scg.mapper.ScJsonMapper;

import java.util.List;

@RestController
@RequestMapping("/element")
public class ScElementController {

    private final ScJsonMapper scJsonMapper;
    private final Autocompleter autocompleter;

    @Autowired
    public ScElementController(ScJsonMapper scJsonMapper, Autocompleter autocompleter) {
        this.scJsonMapper = scJsonMapper;
        this.autocompleter = autocompleter;
    }

    @PostMapping
    public ResponseEntity<?> save(List<ScElementDto> scElements) {
        List<WinkElement> winkElements = scJsonMapper.map(scElements);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> autocomplete(@RequestBody AutocompletionDto autocompletionDto) {
        List<String> autocompletionResult = autocompleter.search(autocompletionDto.getPart(), autocompletionDto.getLimit());
        return new ResponseEntity<>(autocompletionResult, HttpStatus.OK);
    }
}