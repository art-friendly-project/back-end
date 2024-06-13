package com.artfriendly.artfriendly.domain.exhibition.dto;

import java.time.LocalDate;

public record ExhibitionInfoRspDto(
        Long id,
        int seq,
        String title,
        String detailInfoUrl,
        LocalDate startDate,
        LocalDate endDate,
        String place,
        String realmName,
        String area,
        String imageUrl,
        double gpsX,
        double gpsY,
        String ticketingUrl,
        String phone,
        String price,
        String placeAddr
) {
}
