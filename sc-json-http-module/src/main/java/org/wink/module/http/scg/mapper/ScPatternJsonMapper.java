package org.wink.module.http.scg.mapper;

import org.ostis.scmemory.model.pattern.ScPatternTriplet;
import org.ostis.scmemory.model.pattern.element.ScAliasedElement;
import org.ostis.scmemory.model.pattern.element.ScPatternElement;
import org.ostis.scmemory.websocketmemory.memory.pattern.DefaultWebsocketScPattern;
import org.ostis.scmemory.websocketmemory.memory.pattern.SearchingPatternTriple;
import org.ostis.scmemory.websocketmemory.memory.pattern.element.AliasPatternElement;
import org.ostis.scmemory.websocketmemory.memory.pattern.element.TypePatternElement;
import org.springframework.stereotype.Component;
import org.wink.module.http.scg.dto.ScPatternTripletDto;
import org.wink.module.http.scg.dto.ScPatternTripletElementDto;

import java.util.List;

/**
 * @author Mikita Torap
 * @since 0.0.1
 */
@Component
public class ScPatternJsonMapper {

    public DefaultWebsocketScPattern map(List<ScPatternTripletDto> tripletsDto) {
        DefaultWebsocketScPattern websocketScPattern = new DefaultWebsocketScPattern();
        for (ScPatternTripletDto tripletDto : tripletsDto) {
            ScPatternTripletElementDto firstElementDto = tripletDto.getFirstElement();
            ScPatternTripletElementDto secondElementDto = tripletDto.getSecondElement();
            ScPatternTripletElementDto thirdElementDto = tripletDto.getThirdElement();

            ScPatternTriplet triplet = mapToTriplet(firstElementDto, secondElementDto, thirdElementDto);
            websocketScPattern.addElement(triplet);
        }

        return websocketScPattern;
    }

    private ScPatternTriplet mapToTriplet(ScPatternTripletElementDto firstElementDto,
                                          ScPatternTripletElementDto secondElementDto,
                                          ScPatternTripletElementDto thirdElementDto) {
        ScPatternElement firstElement = mapToTripletElement(firstElementDto);
        ScPatternElement secondElement = mapToTripletElement(secondElementDto);
        ScPatternElement thirdElement = mapToTripletElement(thirdElementDto);

        return new SearchingPatternTriple(firstElement, secondElement, thirdElement);
    }

    private ScPatternElement mapToTripletElement(ScPatternTripletElementDto tripletElementDto) {
        String type = tripletElementDto.getValue().toString();
        String alias = tripletElementDto.getAlias();
        ScAliasedElement scAliasedElement = new AliasPatternElement(alias);

        return new TypePatternElement<>(type, scAliasedElement);
    }
}
