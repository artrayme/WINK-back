package org.wink.module.http.scg.dto;

public class ScPatternTripletDto {

    private ScPatternTripletElementDto firstElement;
    private ScPatternTripletElementDto secondElement;
    private ScPatternTripletElementDto thirdElement;

    public ScPatternTripletDto() {
    }

    public ScPatternTripletDto(ScPatternTripletElementDto firstElement,
                               ScPatternTripletElementDto secondElement,
                               ScPatternTripletElementDto thirdElement) {
        this.firstElement = firstElement;
        this.secondElement = secondElement;
        this.thirdElement = thirdElement;
    }

    public ScPatternTripletElementDto getFirstElement() {
        return firstElement;
    }

    public void setFirstElement(ScPatternTripletElementDto firstElement) {
        this.firstElement = firstElement;
    }

    public ScPatternTripletElementDto getSecondElement() {
        return secondElement;
    }

    public void setSecondElement(ScPatternTripletElementDto secondElement) {
        this.secondElement = secondElement;
    }

    public ScPatternTripletElementDto getThirdElement() {
        return thirdElement;
    }

    public void setThirdElement(ScPatternTripletElementDto thirdElement) {
        this.thirdElement = thirdElement;
    }
}
