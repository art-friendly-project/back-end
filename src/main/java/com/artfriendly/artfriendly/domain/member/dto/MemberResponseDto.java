package com.artfriendly.artfriendly.domain.member.dto;

public record MemberResponseDto(
    Long id,
    String email,
    String imageUrl,
    String nickName
) {
}
