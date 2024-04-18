package com.artfriendly.artfriendly.domain.exhibition.controller;

import com.artfriendly.artfriendly.domain.exhibition.service.ApiIntegrationService;
import com.artfriendly.artfriendly.global.api.RspTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("apis")
@RequiredArgsConstructor
public class ApiIntegrationController {
    private final ApiIntegrationService apiIntegrationService;
    @GetMapping
    public RspTemplate<Void> integrationExhibition() {
        apiIntegrationService.integrateExhibitionInfo(LocalDate.now(), 1, "D000");
        return new RspTemplate<>(HttpStatus.OK, "현재 날짜로 부터 1개월 내로 끝나는 전시 정보 연동");
    }
}
