package org.wink.module.http.scg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wink.engine.model.graph.interfaces.WinkElement;
import org.wink.module.http.scg.mapper.ScJsonMapper;

import java.util.List;

@RestController
@RequestMapping("/element")
public class ScElementController {

    private final ScJsonMapper scJsonMapper;

    @Autowired
    public ScElementController(ScJsonMapper scJsonMapper) {
        this.scJsonMapper = scJsonMapper;
    }

    @PostMapping
    public ResponseEntity<?> save(List<Object> scElements) {
        List<WinkElement> winkElements = scJsonMapper.map(scElements);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
