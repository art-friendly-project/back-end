package com.artfriendly.artfriendly.global.utils;

import com.artfriendly.artfriendly.global.exception.common.ErrorCode;
import com.artfriendly.artfriendly.global.exception.dto.ErrorResponseDto;
import com.nimbusds.jose.shaded.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponder {
    public static void sendErrorResponse(HttpServletResponse response, HttpServletRequest request, ErrorCode errorCode, Exception exception) throws IOException {
        printLog(exception, request);
        Gson gson = new Gson();
        ErrorResponseDto<String> errorResponse = createErrorResponseDto(errorCode);
        response.setCharacterEncoding("UTF-8"); // 에러 메세지가 한글로 출력되게 하려면 인코딩을 명시해줘야한다
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // 반환 타입은 JSON
        response.setStatus(errorCode.getStatusCode());
        response.getWriter().write(gson.toJson(errorResponse, ErrorResponseDto.class));
    }

    private static void printLog(Exception e, HttpServletRequest request) {
        if(e == null)
            log.error("발생 예외: {}, 에러 메시지: {}, 요청 Method: {}, 요청 url: {}",
                    "NullAuthorizationException", "Authorization Header가 비어있습니다.", request.getMethod(), request.getRequestURI());
        else
            log.error("발생 예외: {}, 에러 메시지: {}, 요청 Method: {}, 요청 url: {}",
                    e.getClass().getSimpleName(), e.getMessage(), request.getMethod(), request.getRequestURI());
    }

    public static void printLog(Exception e) {
        log.error("발생 예외: {}, 에러 메시지: {}", e.getClass().getSimpleName(), e.getMessage());
    }

    private static ErrorResponseDto<String> createErrorResponseDto(ErrorCode errorCode) {
        int statusCode = errorCode.getStatusCode();
        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);

        return new ErrorResponseDto<>(
                statusCode
                , httpStatus
                , errorCode.getMessage());
    }
}
