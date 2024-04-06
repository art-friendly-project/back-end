package com.artfriendly.artfriendly.domain.exhibition.controller;

import com.artfriendly.artfriendly.domain.exhibition.service.ApiIntegrationService;
import com.artfriendly.artfriendly.global.api.RspTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("apis")
@RequiredArgsConstructor
public class ApiIntegrationController {
    private final ApiIntegrationService apiIntegrationService;
    @GetMapping("exhibition/duration")
    public RspTemplate<String> integrationExhibition() {
        String result = apiIntegrationService.integrateExhibitionInfo("20240101", "20240725");
        return new RspTemplate<>(HttpStatus.OK, "연동", result);
    }
}
