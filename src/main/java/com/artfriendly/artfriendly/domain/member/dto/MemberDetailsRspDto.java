package com.artfriendly.artfriendly.domain.member.dto;

import com.artfriendly.artfriendly.domain.mbti.dto.MbtiSimpleRspDto;

import java.util.List;

public record MemberDetailsRspDto(
        Long id,
        String email,
        String imageUrl,
        String nickName,
        MbtiSimpleRspDto mbtiSimpleRspDto,
        List<String> artPreferenceTypeList
) {
}
