package com.artfriendly.artfriendly.global.exception.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {
    private final int statusCode;
    private final HttpStatus httpStatus;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage()); // throwable 의 detailMessage 에 들어가며, throwable.getMessage()로 부를 수 있다.
        this.statusCode = errorCode.getStatusCode();
        this.httpStatus = HttpStatus.valueOf(errorCode.getStatusCode());
    }

    public BusinessException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause); // throwable 의 detailMessage 에 들어가며, throwable.getMessage()로 부를 수 있다.
        this.statusCode = errorCode.getStatusCode();
        this.httpStatus = HttpStatus.valueOf(errorCode.getStatusCode());
    }

    public BusinessException(String message, HttpStatus httpStatus) {
        super(message); // throwable 의 detailMessage 에 들어가며, throwable.getMessage()로 부를 수 있다.
        this.statusCode = httpStatus.value();
        this.httpStatus = httpStatus;
    }

    public BusinessException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, cause); // throwable 의 detailMessage 에 들어가며, throwable.getMessage()로 부를 수 있다.
        this.statusCode = httpStatus.value();
        this.httpStatus = httpStatus;
    }

}
