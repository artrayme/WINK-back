package org.wink.module.http.scg.dto;

public class ScPatternTripletDto<T> {

    private ScPatternTripletElementDto<T> firstElement;
    private ScPatternTripletElementDto<T> secondElement;
    private ScPatternTripletElementDto<T> thirdElement;

    public ScPatternTripletDto() {
    }

    public ScPatternTripletDto(ScPatternTripletElementDto<T> firstElement,
                               ScPatternTripletElementDto<T> secondElement,
                               ScPatternTripletElementDto<T> thirdElement) {
        this.firstElement = firstElement;
        this.secondElement = secondElement;
        this.thirdElement = thirdElement;
    }

    public ScPatternTripletElementDto<T> getFirstElement() {
        return firstElement;
    }

    public void setFirstElement(ScPatternTripletElementDto<T> firstElement) {
        this.firstElement = firstElement;
    }

    public ScPatternTripletElementDto<T> getSecondElement() {
        return secondElement;
    }

    public void setSecondElement(ScPatternTripletElementDto<T> secondElement) {
        this.secondElement = secondElement;
    }

    public ScPatternTripletElementDto<T> getThirdElement() {
        return thirdElement;
    }

    public void setThirdElement(ScPatternTripletElementDto<T> thirdElement) {
        this.thirdElement = thirdElement;
    }
}
