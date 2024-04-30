package com.artfriendly.artfriendly.domain.member.dto;

public record ProfileDto(
        MemberDetailsRspDto memberDetailsRspDto,
        int dambyeolagCount,
        int StickerCount,
        int interestedExhibitionCount
) {
}
