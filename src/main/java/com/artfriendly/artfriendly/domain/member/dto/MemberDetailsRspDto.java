package com.artfriendly.artfriendly.domain.member.dto;

import com.artfriendly.artfriendly.domain.mbti.dto.MbtiSimpleRspDto;
import com.artfriendly.artfriendly.domain.member.entity.Member;

import java.util.List;

public record MemberDetailsRspDto(
        Long id,
        String email,
        String imageUrl,
        String nickName,
        String selfIntro,
        Member.MemberStatus status,
        MbtiSimpleRspDto mbtiSimpleRspDto,
        List<String> artPreferenceTypeList
) {
}
