package com.artfriendly.artfriendly.domain.member.dto;

import com.artfriendly.artfriendly.domain.member.entity.Member;

public record MemberResponseDto(
    Long id,
    String email,
    String imageUrl,
    String nickName,
    Member.MemberStatus status
) {
}
