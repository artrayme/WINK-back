package org.wink.module.http.scg.controller;

import org.ostis.api.context.DefaultScContext;
import org.ostis.scmemory.model.element.ScElement;
import org.ostis.scmemory.model.element.edge.EdgeType;
import org.ostis.scmemory.model.element.node.NodeType;
import org.ostis.scmemory.model.exception.ScMemoryException;
import org.ostis.scmemory.model.pattern.ScPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wink.module.http.scg.dto.SearchingPatternDto;
import org.wink.module.http.scg.mapper.ScPatternJsonMapper;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

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
    private int constructionNumber = 0;
    private int mainConstructionNumber = 0;

    @Autowired
    public ScPatternTripletController(ScPatternJsonMapper scPatternJsonMapper, DefaultScContext context) {
        this.scPatternJsonMapper = scPatternJsonMapper;
        this.context = context;
    }

    @PostMapping
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
            List<Stream<? extends ScElement>> constructionsList = constructions.toList();
            if (constructionsList.isEmpty()) {
                responseEntity = new ResponseEntity<>("WINK-NOTHING-FOUND", HttpStatus.OK);
            } else {
                String mainStructIdtf = "wink-main-construction_" + mainConstructionNumber++;
                var mainStruct = context.resolveKeynode(mainStructIdtf, NodeType.STRUCT);
                for (Stream<? extends ScElement> stream : constructionsList) {
//                    var struct = context.resolveKeynode("wink-construction_" + constructionNumber++, NodeType.STRUCT);
                    List<? extends ScElement> singleConstructionElements = stream.toList();
                    for (ScElement singleConstructionElement : singleConstructionElements) {
//                        context.createEdge(EdgeType.ACCESS_CONST_POS_PERM, struct, singleConstructionElement);
                        context.createEdge(EdgeType.ACCESS_CONST_POS_PERM, mainStruct, singleConstructionElement);
                    }
//                    context.createEdge(EdgeType.ACCESS_CONST_POS_PERM, mainStruct, struct);
                }
                responseEntity = new ResponseEntity<>(mainStructIdtf, HttpStatus.OK);
            }
        } catch (ScMemoryException e) {
            responseEntity = new ResponseEntity<>(
                    createExceptionResponseWithMessageAndCode(e.getMessage(), INTERNAL_SERVER_ERROR_CODE),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }
}
