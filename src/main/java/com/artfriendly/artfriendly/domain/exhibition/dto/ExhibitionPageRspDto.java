package com.artfriendly.artfriendly.domain.exhibition.dto;

import java.time.LocalDate;

public record ExhibitionPageRspDto(
        long id,
        String title,
        Double temperature,
        LocalDate startDate,
        LocalDate endDate,
        String area,
        Boolean isLike
) {
}
