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
import org.wink.module.http.scg.dto.ScElementDto;
import org.wink.module.http.scg.mapper.ScJsonMapper;

import java.util.List;


@RestController
@RequestMapping("/element")
public class ScElementController {

    private final ScJsonMapper scJsonMapper;
    private final Autocompleter autocompleter;

    //    ToDo Mikita
    //    Please, inject this dependency correctly.
    //    @artrayme just added it.
    //    At now OstisScMemoryManager implementation should be injected.
    //    I think, the best way to create this class is to read URI of the OSTIS from the config file.
    //    Default URI -- ws://localhost:8090/ws_json.
    //    You can find an example here -- OstisScMemoryManagerTest.
    private final ScMemoryManager manager;

    @Autowired
    public ScElementController(ScJsonMapper scJsonMapper, Autocompleter autocompleter, ScMemoryManager manager) {
        this.scJsonMapper = scJsonMapper;
        this.autocompleter = autocompleter;
        this.manager = manager;
    }

    @PostMapping
    public ResponseEntity<?> save(List<ScElementDto> scElements) {
        WinkGraph graph = scJsonMapper.map(scElements, new DefaultWinkGraphHeader("NOT_SPECIFIED", "NOT_SPECIFIED"));

        //        ToDo Mikita
        //        1) Graph can be loaded only with a unique name.
        //        For example, file path.
        //        And this name can be passed in the request (with List of dto).
        //
        //        2) It is better to return error explanation,
        //        because is not difficult for realisation but very useful for user.
        //        Please, try to return normal response according to caught exception (in blocks bellow).
        //
        try {
            manager.upload("TODO_unique_name", graph);
        } catch (ScMemoryException e) {
            e.printStackTrace();
        } catch (GraphWithThisNameAlreadyUploadedException e) {
            e.printStackTrace();
        } catch (CannotCreateNodeException e) {
            e.printStackTrace();
        } catch (CannotCreateLinkException e) {
            e.printStackTrace();
        } catch (CannotCreateEdgeException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> autocomplete(@RequestBody AutocompletionDto autocompletionDto) {
        List<String> autocompletionResult = autocompleter.search(autocompletionDto.getPart(), autocompletionDto.getLimit());
        return new ResponseEntity<>(autocompletionResult, HttpStatus.OK);
    }
}