package com.artfriendly.artfriendly.domain.dambyeolag.controller;

import com.artfriendly.artfriendly.domain.dambyeolag.dto.sticker.StickerReqDto;
import com.artfriendly.artfriendly.domain.dambyeolag.dto.sticker.StickerRspDto;
import com.artfriendly.artfriendly.domain.dambyeolag.service.sticker.StickerService;
import com.artfriendly.artfriendly.global.api.RspTemplate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("stickers")
@RequiredArgsConstructor
public class StickerController {
    private final StickerService stickerService;

    @GetMapping
    public RspTemplate<StickerRspDto> getStickerById(@NotNull int stickerId) {
        return new RspTemplate<>(HttpStatus.OK, "스티커 id: "+stickerId+" 조회", stickerService.getStickerById(stickerId));
    }

    @DeleteMapping
    public RspTemplate<Void> deleteSticker(@AuthenticationPrincipal long memberId, @NotNull long stickerId) {
        stickerService.deleteSticker(memberId, stickerId);
        return new RspTemplate<>(HttpStatus.OK, "스티커 id: "+stickerId+" 삭제");
    }

    @PostMapping
    public RspTemplate<Void> addSticker(@AuthenticationPrincipal long memberId, @Valid @RequestBody StickerReqDto stickerReqDto) {
        stickerService.addSticker(stickerReqDto, memberId);
        return new RspTemplate<>(HttpStatus.CREATED, "스티커 추가");
    }
}
