package com.artfriendly.artfriendly.domain.mbti.dto;

public record MbtiRspDto(
        Long id,
        String mbtiType,
        String subTitle,
        String title,
        String percentage,
        String body,
        String imageUrl,
        MbtiSimpleRspDto matchType,
        MbtiSimpleRspDto missMatchType
) {
}
