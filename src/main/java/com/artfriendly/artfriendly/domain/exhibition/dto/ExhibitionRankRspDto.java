package com.artfriendly.artfriendly.domain.exhibition.dto;

public record ExhibitionRankRspDto(
        long exhibitionId,
        int rank,
        String title,
        String imageUrl,
        String rankShift
) {
}
