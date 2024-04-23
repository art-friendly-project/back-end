package com.artfriendly.artfriendly.domain.member.dto;

import java.util.List;

public record MemberUpdateReqDto(
    String nickName,
    List<String> artPreferenceTypeList,
    Long mbtiId
) {
}
