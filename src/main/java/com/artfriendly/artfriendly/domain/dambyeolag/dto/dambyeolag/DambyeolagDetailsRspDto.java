package com.artfriendly.artfriendly.domain.dambyeolag.dto.dambyeolag;

import com.artfriendly.artfriendly.domain.dambyeolag.dto.sticker.StickerRspDto;
import com.artfriendly.artfriendly.domain.member.dto.MemberResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record DambyeolagDetailsRspDto(
    Long id,
    String title,
    String body,
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    LocalDateTime lastModifiedTime,
    MemberResponseDto memberResponseDto,
    List<StickerRspDto> stickerRspDtos,
    int bookmarkCounts,
    int stickerCounts,
    boolean isBookmark,
    boolean isSticker
) {
}
