package com.artfriendly.artfriendly.global.scheduler;

import com.artfriendly.artfriendly.domain.userlog.service.UserLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserLogScheduler {
    private final UserLogService userLogService;

    @Scheduled(cron = "23 59 0 * * *", zone = "Asia/Seoul")
    public void saveUserLogs() {
        userLogService.saveDailyUserCount();
        userLogService.resetDailyUserCountCache();
    }
}
