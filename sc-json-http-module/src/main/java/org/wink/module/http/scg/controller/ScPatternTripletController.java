package org.wink.module.http.scg.controller;

import org.ostis.scmemory.model.pattern.ScPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wink.module.http.scg.dto.ScPatternTripletDto;
import org.wink.module.http.scg.mapper.ScPatternJsonMapper;

import java.util.List;

/**
 * @author Mikita Torap
 * @since 0.0.1
 */
@RestController
@RequestMapping("/triplet")
public class ScPatternTripletController<T> {

    private final ScPatternJsonMapper<T> scPatternJsonMapper;

    @Autowired
    public ScPatternTripletController(ScPatternJsonMapper<T> scPatternJsonMapper) {
        this.scPatternJsonMapper = scPatternJsonMapper;
    }

    @GetMapping
    public ResponseEntity<?> save(@RequestBody List<ScPatternTripletDto<T>> triplets) {
        ScPattern scPattern = scPatternJsonMapper.map(triplets);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
