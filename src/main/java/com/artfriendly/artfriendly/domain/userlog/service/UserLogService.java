package com.artfriendly.artfriendly.domain.userlog.service;

import com.artfriendly.artfriendly.domain.userlog.cache.DailyUserCountCache;
import com.artfriendly.artfriendly.domain.userlog.entity.DailyUserCount;
import com.artfriendly.artfriendly.domain.userlog.repository.DailyUserCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserLogService {
    private final DailyUserCountCache dailyUserCountCache;
    private final DailyUserCountRepository dailyUserCountRepository;

    public void upUserCount() {
        dailyUserCountCache.upDailyUserCount();

    }

    public void resetDailyUserCountCache() {
        dailyUserCountCache.clearDailyUserCountCache();
        dailyUserCountCache.initCache();
    }

    @Transactional
    public void saveDailyUserCount() {
        DailyUserCount dailyUserCount = DailyUserCount.builder()
                .userCount(dailyUserCountCache.getDailyUserCount())
                .build();

        dailyUserCountRepository.save(dailyUserCount);
    }
}
