package com.inova.error.Handelars;

import com.inova.dto.ErrorResponsePayload;
import com.inova.error.exceptions.InovaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {


    public ResponseEntity<ErrorResponsePayload> error(HttpStatus status, Exception e, String msg) {
        log.error("error message :" + e.getMessage());
        return ResponseEntity.status(status).body(
                ErrorResponsePayload.builder()
                        .error(Map.of("errorMessage", msg))
                        .build()
        );
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<ErrorResponsePayload> handleException(Exception e) {
        return error(INTERNAL_SERVER_ERROR, e, e.getMessage());
    }


    @ExceptionHandler({InovaException.class})
    public ResponseEntity<ErrorResponsePayload> handleInovaCustomException(InovaException e) {
        return error(INTERNAL_SERVER_ERROR, e,
                e.getMessage());
    }



}
