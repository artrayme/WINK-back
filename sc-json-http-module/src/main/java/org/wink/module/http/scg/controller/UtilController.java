package org.wink.module.http.scg.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wink.engine.converter.GwfToScsConverter;
import org.wink.engine.exceptions.InvalidGwfException;
import org.wink.module.http.scg.dto.GwfFileDto;

import java.io.IOException;

/**
 * @author Diana
 * @since 0.0.1
 */
@RestController
@RequestMapping("/util")
public class UtilController {

    @PostMapping
    public ResponseEntity<?> convertGwfToScs(@RequestBody GwfFileDto gwfFileDto) {
        try {
            String result = GwfToScsConverter.convertToScs(gwfFileDto.getGwfText());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IOException | InterruptedException | InvalidGwfException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
