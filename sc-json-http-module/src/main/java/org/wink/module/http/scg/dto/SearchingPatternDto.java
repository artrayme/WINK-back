package org.wink.module.http.scg.dto;

import java.util.List;

public class SearchingPatternDto {
    private List<ScPatternTripletDto> payload;

    public SearchingPatternDto() {
    }

    public SearchingPatternDto(List<ScPatternTripletDto> payload) {
        this.payload = payload;
    }

    public List<ScPatternTripletDto> getPayload() {
        return payload;
    }
}
