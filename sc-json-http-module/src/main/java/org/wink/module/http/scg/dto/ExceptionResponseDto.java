package org.wink.module.http.scg.dto;

public class ExceptionResponseDto {

    private final String exceptionMessage;
    private final Integer exceptionInternalCode;

    public ExceptionResponseDto(String exceptionMessage, Integer exceptionInternalCode) {
        this.exceptionMessage = exceptionMessage;
        this.exceptionInternalCode = exceptionInternalCode;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public Integer getExceptionInternalCode() {
        return exceptionInternalCode;
    }


}
