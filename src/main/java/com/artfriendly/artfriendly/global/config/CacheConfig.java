package com.artfriendly.artfriendly.global.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean(name = "endingExhibitionCache")
    public CacheManager endingExhibitionCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(15, TimeUnit.MINUTES)
                .initialCapacity(30)  // 초기 캐시 사이즈
                .maximumSize(1000));    // 최대 캐시 사이즈
        return cacheManager;
    }

    @Primary
    @Bean(name = "exhibitionCache")
    public CacheManager exhibitionCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(15, TimeUnit.MINUTES)
                .initialCapacity(40)  // 초기 캐시 사이즈
                .maximumSize(1000));    // 최대 캐시 사이즈
        return cacheManager;
    }
}
