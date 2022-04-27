package org.wink.module.http.scg.dto;

import java.util.List;

public class SearchingPatternDto {

    private List<List<ScPatternTripletElementDto>> payload;

    public SearchingPatternDto() {
    }

    public SearchingPatternDto(List<List<ScPatternTripletElementDto>> payload) {
        this.payload = payload;
    }

    public List<List<ScPatternTripletElementDto>> getPayload() {
        return payload;
    }

    public void setPayload(List<List<ScPatternTripletElementDto>> payload) {
        this.payload = payload;
    }
}
