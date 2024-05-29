package com.artfriendly.artfriendly.domain.exhibition.dto;

import java.time.LocalDate;

public record ExhibitionRankRspDto(
        long exhibitionId,
        int rank,
        String title,
        String imageUrl,
        LocalDate startDate,
        LocalDate endDate,
        String place,
        String area,
        String rankShift
) {
}
