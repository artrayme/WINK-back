package org.wink.module.http.scg.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.wink.engine.exceptions.CannotCreateEdgeException;
import org.wink.engine.exceptions.CannotCreateLinkException;
import org.wink.engine.exceptions.CannotCreateNodeException;
import org.wink.engine.exceptions.GraphWithThisNameAlreadyUploadedException;
import org.wink.module.http.scg.dto.ExceptionResponseDto;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String GRAPH_WITH_THIS_NAME_ALREADY_UPLOADED = "Graph with this name has already been uploaded.";
    private static final String CANNOT_CREATE_EDGE = "Unfortunately, the edge couldn't be created.";
    private static final String CANNOT_CREATE_LINK = "Unfortunately, the link couldn't be created.";
    private static final String CANNOT_CREATE_NODE = "Unfortunately, the node couldn't be created.";

    private static final int INTERNAL_SERVER_ERROR_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();
    private static final int CONFLICT_CODE = HttpStatus.CONFLICT.value();

    @ExceptionHandler(GraphWithThisNameAlreadyUploadedException.class)
    public ResponseEntity<ExceptionResponseDto> handleGraphWithThisNameAlreadyUploadedException() {
        ExceptionResponseDto exceptionResponse = new ExceptionResponseDto(GRAPH_WITH_THIS_NAME_ALREADY_UPLOADED, CONFLICT_CODE);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CannotCreateEdgeException.class)
    public ResponseEntity<ExceptionResponseDto> handleCannotCreateEdgeException() {
        ExceptionResponseDto exceptionResponse = new ExceptionResponseDto(CANNOT_CREATE_EDGE, INTERNAL_SERVER_ERROR_CODE);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CannotCreateLinkException.class)
    public ResponseEntity<ExceptionResponseDto> handleCannotCreateLinkException() {
        ExceptionResponseDto exceptionResponse = new ExceptionResponseDto(CANNOT_CREATE_LINK, INTERNAL_SERVER_ERROR_CODE);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CannotCreateNodeException.class)
    public ResponseEntity<ExceptionResponseDto> handleCannotCreateNodeException() {
        ExceptionResponseDto exceptionResponse = new ExceptionResponseDto(CANNOT_CREATE_NODE, INTERNAL_SERVER_ERROR_CODE);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponseDto> handleIllegalArgumentException(IllegalArgumentException e) {
        ExceptionResponseDto exceptionResponse = new ExceptionResponseDto(e.getMessage(), INTERNAL_SERVER_ERROR_CODE);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ExceptionResponseDto createExceptionResponseWithMessageAndCode(String exceptionMessage, int code) {
        return new ExceptionResponseDto(exceptionMessage, code);
    }
}
