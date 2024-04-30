package com.artfriendly.artfriendly.domain.member.dto;

import java.util.List;

public record MemberUpdateReqDto(
    String nickName,
    String selfIntro,
    List<String> artPreferenceTypeList,
    Long mbtiId
) {
}
