package com.artfriendly.artfriendly.domain.member.dto;

import jakarta.validation.constraints.NotNull;

public record MemberUpdateReqDto(
    @NotNull
    String nickName
) {
}
