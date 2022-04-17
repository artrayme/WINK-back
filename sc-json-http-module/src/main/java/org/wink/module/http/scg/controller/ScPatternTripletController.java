package org.wink.module.http.scg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wink.module.http.scg.dto.AutocompletionDto;
import org.wink.module.http.scg.dto.ScPatternTripletDto;
import org.wink.module.http.scg.mapper.WebsocketScJsonMapper;

import java.util.List;

/**
 * @author Mikita Torap
 * @since 0.0.1
 */
@RestController
@RequestMapping("/element")
public class ScPatternTripletController {

    private final WebsocketScJsonMapper websocketScJsonMapper;

    @Autowired
    public ScPatternTripletController(WebsocketScJsonMapper websocketScJsonMapper) {
        this.websocketScJsonMapper = websocketScJsonMapper;
    }

    @GetMapping
    public ResponseEntity<?> find(@RequestBody List<ScPatternTripletDto> triplets) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
