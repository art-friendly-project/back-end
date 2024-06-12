package com.artfriendly.artfriendly.domain.userlog.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Component;

@Component
public class DailyUserCountCache {
    private final Cache<String, Integer> userCountCache = Caffeine.newBuilder()
            .initialCapacity(1)
            .maximumSize(1)
            .build();

    public void initCache() {
        userCountCache.put("daily_user_count", 0);
    }

    public void clearDailyUserCountCache() {
        userCountCache.invalidateAll();
    }

    public void upDailyUserCount() {
        userCountCache.put("daily_user_count", userCountCache.getIfPresent("daily_user_count")+1);
    }

    public int getDailyUserCount() {
        return userCountCache.getIfPresent("daily_user_count");
    }
}
