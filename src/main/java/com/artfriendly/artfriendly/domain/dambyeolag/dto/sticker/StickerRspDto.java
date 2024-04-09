package com.artfriendly.artfriendly.domain.dambyeolag.dto.sticker;

public record StickerRspDto(
        long id,
        int xCoordinate,
        int yCoordinate,
        String body,
        long memberId,
        long dambyeolagId
) {

}
