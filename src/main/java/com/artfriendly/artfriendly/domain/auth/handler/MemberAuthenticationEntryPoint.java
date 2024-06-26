package com.artfriendly.artfriendly.domain.auth.handler;

import com.artfriendly.artfriendly.global.exception.common.ErrorCode;
import com.artfriendly.artfriendly.global.utils.ErrorResponder;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class MemberAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Exception exception = (Exception) request.getAttribute("exception");

        logExceptionMessage(authException, exception);

        if(exception instanceof ExpiredJwtException)
            ErrorResponder.sendErrorResponse(response, request, ErrorCode.ACCESS_TOKEN_EXPIRED, exception); // 토큰 만료시
        else if(exception instanceof MalformedJwtException)
            ErrorResponder.sendErrorResponse(response, request, ErrorCode.MALFORMED_TOKEN, exception); // 잘못된 형식의 토큰일 시
        else if(exception instanceof SignatureException)
            ErrorResponder.sendErrorResponse(response, request, ErrorCode.INVALID_SIGNATURE, exception); // 서명이 잘못된 토큰일 시
        else if(exception == null)
            ErrorResponder.sendErrorResponse(response, request, ErrorCode.NOT_EXISTS_AUTH_HEADER, null); // 헤더에 토큰이 없을 시
        else
            ErrorResponder.sendErrorResponse(response, request, ErrorCode.NOT_VALID_TOKEN, exception);

    }

    private void logExceptionMessage(AuthenticationException authException, Exception exception) {
        String message = exception != null ? exception.getMessage() : authException.getMessage();
        log.warn("Unauthorized error happened: {}", message, exception);
    }

}
