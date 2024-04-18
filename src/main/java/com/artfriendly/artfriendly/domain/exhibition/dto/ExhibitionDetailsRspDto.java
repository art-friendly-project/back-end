package com.artfriendly.artfriendly.domain.exhibition.dto;

public record ExhibitionDetailsRspDto(
        long id,
        Double temperature,
        String checkTemperature,
        boolean isLike,
        ExhibitionInfoRspDto exhibitionInfoRspDto
) {
}
