package com.artfriendly.artfriendly.global.exception.handler;

import com.artfriendly.artfriendly.global.exception.common.AppServiceException;
import com.artfriendly.artfriendly.global.exception.common.BusinessException;
import com.artfriendly.artfriendly.global.exception.common.ErrorCode;
import com.artfriendly.artfriendly.global.exception.dto.ErrorResponseDto;
import com.artfriendly.artfriendly.global.utils.LoggingUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * @ModelAttribute 으로 binding error 발생시 BindException 발생한다.
     * @RequestBody @Valid 바인딩 오류(HttpMessageConverter binding) 시 발생하는 MethodArgumentNotValidException 도 BindException 을 확장한다.
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponseDto<Map<String, String>>> handleBindException(BindException e, HttpServletRequest request) {
        printLog(e, request);

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        StringBuilder sb = new StringBuilder();
        Map<String, String> errorInfoMap = new HashMap<>();
        for (FieldError fieldError : fieldErrors) {
            String errorMsg = sb
                    .append(fieldError.getDefaultMessage())
                    .append(". 요청받은 값: ")
                    .append(fieldError.getRejectedValue())
                    .toString();

            errorInfoMap.put(fieldError.getField(), errorMsg);

            sb.setLength(0);
        }

        return createErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, errorInfoMap);
    }

    /** @RequestParam 파라미터 누락*/
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponseDto<String>> handleMissingServletRequestParameterException(MissingServletRequestParameterException e, HttpServletRequest request) {
        printLog(e, request);
        String message = "파라미터 '" + e.getParameterName() + "'이 누락되었습니다.";
        return createErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class, NoSuchElementException.class})
    public ResponseEntity<ErrorResponseDto<String>> handleBusinessException(RuntimeException e, HttpServletRequest request){
        printLog(e, request);
        return createErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, e.getMessage());
    }

    // ENUM 변환실패, 날짜타입에 2999-15-99 와 같은 잘못된 값이 들어올 때 발생
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponseDto<String>> handleInvalidFormatException(HttpMessageNotReadableException e, HttpServletRequest request){
        printLog(e, request);
        return createErrorResponse(ErrorCode.HTTP_MESSAGE_NOT_READABLE);
    }

    // BusinessException 을 상속한 다른 Custom Exception 에도 적용된다.
    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<ErrorResponseDto<String>> handleBusinessException(BusinessException e, HttpServletRequest request){
        printLog(e, request);
        return createErrorResponse(e.getStatusCode(), e.getHttpStatus(), e.getMessage());
    }

    // 비즈니스 로직이 아닌 애플리케이션 서비스 로직상 예외
    @ExceptionHandler({AppServiceException.class})
    public ResponseEntity<ErrorResponseDto<String>> handleAppServiceException(AppServiceException e, HttpServletRequest request){
        printLog(e, request);
        return createErrorResponse(e.getStatusCode(), e.getHttpStatus(), e.getMessage());
    }

    // 예상하지 못한 예외 발생 시 500 에러와 함께 기본 에러메시지를 넘겨준다.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto<String>> handleException(Exception e, HttpServletRequest request){
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        log.error("예외처리 범위 외의 오류 발생.");
        printLog(e, request);
        String fullStackTrace = LoggingUtil.stackTraceToString(e);

        return createErrorResponse(httpStatus.value(), httpStatus, e.getMessage() +", " + fullStackTrace);
    }

    private <T> ResponseEntity<ErrorResponseDto<T>> createErrorResponse(int statusCode, HttpStatus httpStatus, T errorMessage) {
        ErrorResponseDto<T> errDto = new ErrorResponseDto<>(statusCode, httpStatus, errorMessage);
        return ResponseEntity.status(httpStatus).body(errDto);
    }

    private ResponseEntity<ErrorResponseDto<String>> createErrorResponse(ErrorCode errorCode) {
        int statusCode = errorCode.getStatusCode();
        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);

        ErrorResponseDto<String> errDto = new ErrorResponseDto<>(
                statusCode
                , httpStatus
                , errorCode.getMessage());
        return ResponseEntity.status(httpStatus).body(errDto);
    }

    private void printLog(Exception e, HttpServletRequest request) {
        log.error("발생 예외: {}, 에러 메시지: {}, 요청 Method: {}, 요청 url: {}",
                e.getClass().getSimpleName(), e.getMessage(), request.getMethod(), request.getRequestURI(),
                e);
    }


}
