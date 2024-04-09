package com.artfriendly.artfriendly.domain.mbti.dto;

public record MbtiReqDto(
        String mbtiType,
        String subTitle,
        String title,
        String percentage,
        String body,
        String imageUrl,
        String matchType,
        String missMatchType
) {
}
