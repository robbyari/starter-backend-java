package com.pancaran.master.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<APIResponse<Void>> handleApiException(ApiException ex) {
        log.warn("API Exception occurred: code={}, message={}", ex.getCode(), ex.getErrorDetails());
        
        HttpStatus status;
        try {
            status = HttpStatus.valueOf(ex.getCode());
        } catch (IllegalArgumentException e) {
            status = HttpStatus.BAD_REQUEST;
        }

        APIResponse<Void> response = APIResponse.error(ex.getCode(), ex.getErrorDetails());
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<Void>> handleGeneralException(Exception ex) {
        log.error("Unexpected error occurred: ", ex);
        
        APIResponse<Void> response = APIResponse.error(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred on our server. Please try again later."
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
