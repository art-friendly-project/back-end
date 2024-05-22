package com.artfriendly.artfriendly.domain.exhibition.dto;

import java.time.LocalDate;

public record ExhibitionRspDto(
        long id,
        String title,
        String imageUrl,
        Double temperature,
        LocalDate startDate,
        LocalDate endDate,
        String area,
        Boolean isLike
) {
}
