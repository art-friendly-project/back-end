package com.artfriendly.artfriendly.domain.auth.dto;

import com.artfriendly.artfriendly.domain.member.entity.Member;

public record OAuth2LoginDto(
        Member member,
        Boolean isSignUp
) {
}
