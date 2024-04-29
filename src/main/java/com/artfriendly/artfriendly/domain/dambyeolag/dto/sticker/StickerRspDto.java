package com.artfriendly.artfriendly.domain.dambyeolag.dto.sticker;

import com.artfriendly.artfriendly.domain.dambyeolag.entity.StickerType;

public record StickerRspDto(
        long id,
        StickerType stickerType,
        String body,
        long memberId,
        long dambyeolagId
) {

}
