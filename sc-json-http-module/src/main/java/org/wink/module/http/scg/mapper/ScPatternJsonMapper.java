package org.wink.module.http.scg.mapper;

import org.ostis.api.context.DefaultScContext;
import org.ostis.scmemory.model.element.edge.EdgeType;
import org.ostis.scmemory.model.element.link.LinkType;
import org.ostis.scmemory.model.element.node.NodeType;
import org.ostis.scmemory.model.exception.ScMemoryException;
import org.ostis.scmemory.model.pattern.ScPatternTriplet;
import org.ostis.scmemory.model.pattern.element.ScPatternElement;
import org.ostis.scmemory.websocketmemory.memory.pattern.DefaultWebsocketScPattern;
import org.ostis.scmemory.websocketmemory.memory.pattern.SearchingPatternTriple;
import org.ostis.scmemory.websocketmemory.memory.pattern.element.AliasPatternElement;
import org.ostis.scmemory.websocketmemory.memory.pattern.element.FixedPatternElement;
import org.ostis.scmemory.websocketmemory.memory.pattern.element.TypePatternElement;
import org.ostis.scmemory.websocketmemory.util.internal.ScTypesMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wink.module.http.scg.dto.ScPatternTripletElementDto;

import java.util.List;

/**
 * @author Mikita Torap
 * @since 0.0.1
 */
@Component
public class ScPatternJsonMapper {

    private final DefaultScContext context;

    @Autowired
    public ScPatternJsonMapper(DefaultScContext context) {
        this.context = context;
    }

    public DefaultWebsocketScPattern map(List<List<ScPatternTripletElementDto>> tripletsDto) throws ScMemoryException {
        DefaultWebsocketScPattern websocketScPattern = new DefaultWebsocketScPattern();
        for (var tripletDto : tripletsDto) {
            ScPatternTripletElementDto firstElementDto = tripletDto.get(0);
            ScPatternTripletElementDto secondElementDto = tripletDto.get(1);
            ScPatternTripletElementDto thirdElementDto = tripletDto.get(2);

            ScPatternTriplet triplet = mapToTriplet(firstElementDto, secondElementDto, thirdElementDto);
            websocketScPattern.addElement(triplet);
        }

        return websocketScPattern;
    }

    private ScPatternTriplet mapToTriplet(ScPatternTripletElementDto firstElementDto,
                                          ScPatternTripletElementDto secondElementDto,
                                          ScPatternTripletElementDto thirdElementDto) throws ScMemoryException {
        ScPatternElement firstElement = mapToTripletElement(firstElementDto);
        ScPatternElement secondElement = mapToTripletElement(secondElementDto);
        ScPatternElement thirdElement = mapToTripletElement(thirdElementDto);

        return new SearchingPatternTriple(firstElement, secondElement, thirdElement);
    }

    private ScPatternElement mapToTripletElement(ScPatternTripletElementDto tripletElementDto) throws ScMemoryException {
        return switch (tripletElementDto.getType()) {
            case "addr" -> {
                var element = context.findKeynode(tripletElementDto.getValue());
                if (element.isPresent()) {
                    yield new FixedPatternElement(element.get());
                } else {
                    throw new ScMemoryException("Keynode with name " + tripletElementDto.getValue() + " doesn't exist");
                }
            }
            case "type" -> {
                var type = ScTypesMap.INSTANCE.getTypes().get(Integer.parseInt(tripletElementDto.getValue()));
                if (type != null) {
                    if (type instanceof NodeType node) {
                        yield new TypePatternElement<>(node, new AliasPatternElement(tripletElementDto.getAlias()));
                    } else if (type instanceof EdgeType edge) {
                        yield new TypePatternElement<>(edge, new AliasPatternElement(tripletElementDto.getAlias()));
                    } else {
                        yield new TypePatternElement<>((LinkType) type, new AliasPatternElement(tripletElementDto.getAlias()));
                    }
                } else {
                    throw new ScMemoryException("Invalid sc-element type " + tripletElementDto.getValue());
                }
            }
            case "alias" -> new AliasPatternElement(tripletElementDto.getValue());
            default -> throw new IllegalArgumentException();
        };
    }
}
