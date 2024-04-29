package com.artfriendly.artfriendly.domain.dambyeolag.dto.sticker;

import com.artfriendly.artfriendly.domain.dambyeolag.entity.StickerType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record StickerReqDto(
        @NotNull
        StickerType stickerType,
        @NotEmpty
        String body,
        @NotNull
        long dambyeolagId
) {
}
