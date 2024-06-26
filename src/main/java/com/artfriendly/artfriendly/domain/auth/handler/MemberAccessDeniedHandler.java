package com.artfriendly.artfriendly.domain.auth.handler;

import com.artfriendly.artfriendly.global.exception.common.ErrorCode;
import com.artfriendly.artfriendly.global.utils.ErrorResponder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class MemberAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.warn("Forbidden error happened: {}", accessDeniedException.getMessage());
        ErrorResponder.sendErrorResponse(response, request, ErrorCode.ACCESS_DENIED, accessDeniedException); // 권한이 없을 시
    }
}
