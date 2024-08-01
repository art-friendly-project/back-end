package com.artfriendly.artfriendly.global.scheduler;

import com.artfriendly.artfriendly.domain.exhibition.service.ExhibitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExhibitionScheduler {
    private final ExhibitionService exhibitionService;

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void integrationExhibition() {
        exhibitionService.updateTop10PopularExhibitionRankRspDto();
    }
}
