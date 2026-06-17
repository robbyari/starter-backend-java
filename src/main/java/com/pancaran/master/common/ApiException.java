package com.pancaran.master.common;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private final int code;
    private final Object errorDetails;

    public ApiException(int code, Object errorDetails) {
        super(errorDetails instanceof String ? (String) errorDetails : "Validation error occurred");
        this.code = code;
        this.errorDetails = errorDetails;
    }

    public ApiException(Object errorDetails) {
        super(errorDetails instanceof String ? (String) errorDetails : "Validation error occurred");
        this.code = 400; // Default bad request
        this.errorDetails = errorDetails;
    }
}
