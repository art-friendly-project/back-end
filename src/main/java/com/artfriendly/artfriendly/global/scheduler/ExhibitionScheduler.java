package com.artfriendly.artfriendly.global.scheduler;

import com.artfriendly.artfriendly.domain.exhibition.service.ApiIntegrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ExhibitionScheduler {
    private final ApiIntegrationService apiIntegrationService;
    @Value("${exhibition.code.art}")
    String artRealmCode;

    // 매일 24:00 마다 오늘로 부터 6개월의 미술 전시 데이터를 연동한다.
    @Scheduled(cron = "0 0 0 * * *")
    public void integrationExhibition() {
        apiIntegrationService.integrateExhibitionInfo(LocalDate.now(), 6, artRealmCode);
    }
}
