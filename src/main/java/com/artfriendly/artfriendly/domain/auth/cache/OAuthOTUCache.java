package com.artfriendly.artfriendly.domain.auth.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class OAuthOTUCache {

    private final Cache<String, Long> codeExpirationCache = Caffeine.newBuilder()
            .expireAfterWrite(30, TimeUnit.SECONDS)
            .build();

    public String putVerificationCodeInCache(long targetMemberId) {
        String verificationCode = UUID.randomUUID().toString();
        codeExpirationCache.put(verificationCode, targetMemberId);

        return verificationCode;
    }

    public long getMemberId(String verificationCode) {
        if (!StringUtils.hasText(verificationCode)) {
            throw new IllegalArgumentException("verificationCode must not be null or empty");
        }
        Long memberId = codeExpirationCache.getIfPresent(verificationCode);
        codeExpirationCache.invalidate(verificationCode);
        return Objects.requireNonNull(memberId, "verificationCode is invalid");
    }
}
