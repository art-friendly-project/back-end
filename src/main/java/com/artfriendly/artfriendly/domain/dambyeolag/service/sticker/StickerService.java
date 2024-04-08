package com.artfriendly.artfriendly.domain.dambyeolag.service.sticker;

import com.artfriendly.artfriendly.domain.dambyeolag.dto.sticker.StickerReqDto;
import com.artfriendly.artfriendly.domain.dambyeolag.dto.sticker.StickerRspDto;
import com.artfriendly.artfriendly.domain.dambyeolag.entity.Sticker;

public interface StickerService {
    StickerRspDto getStickerById(long stickerId);
    void deleteSticker(long memberId, long stickerId);
    void addSticker(StickerReqDto stickerReqDto, long memberId);
    Sticker findStickerById(long stickerId);
}
