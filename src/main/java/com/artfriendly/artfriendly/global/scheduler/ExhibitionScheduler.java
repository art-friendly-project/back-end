package com.artfriendly.artfriendly.global.scheduler;

import com.artfriendly.artfriendly.domain.exhibition.service.ApiIntegrationService;
import com.artfriendly.artfriendly.domain.exhibition.service.ExhibitionInfoService;
import com.artfriendly.artfriendly.domain.exhibition.service.ExhibitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ExhibitionScheduler {
    private final ApiIntegrationService apiIntegrationService;
    private final ExhibitionService exhibitionService;
    private final ExhibitionInfoService exhibitionInfoService;
    @Value("${exhibition.code.art}")
    String artRealmCode;

    // 매일 24:00 마다 오늘로 부터 6개월의 미술 전시 데이터를 연동한다.
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void integrationExhibition() {
        apiIntegrationService.integrateExhibitionInfo(LocalDate.now(), 6, artRealmCode);
        exhibitionInfoService.updateExhibitionInfoProgress(LocalDate.now());
        exhibitionService.updateTop10PopularExhibitionRankRspDto();
    }
}
