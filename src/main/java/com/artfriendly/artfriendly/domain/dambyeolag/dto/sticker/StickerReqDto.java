package com.artfriendly.artfriendly.domain.dambyeolag.dto.sticker;

import jakarta.validation.constraints.NotNull;

public record StickerReqDto(
        @NotNull
        int xCoordinate,
        @NotNull
        int yCoordinate,
        String body,
        @NotNull
        long dambyeolagId
) {
}
