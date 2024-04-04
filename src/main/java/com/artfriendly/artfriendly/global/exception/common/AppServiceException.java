package com.artfriendly.artfriendly.global.exception.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppServiceException extends RuntimeException{
    private final int statusCode;
    private final HttpStatus httpStatus;

    public AppServiceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.statusCode = errorCode.getStatusCode();
        this.httpStatus = HttpStatus.valueOf(errorCode.getStatusCode());
    }

    public AppServiceException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.statusCode = errorCode.getStatusCode();
        this.httpStatus = HttpStatus.valueOf(errorCode.getStatusCode());
    }
}
