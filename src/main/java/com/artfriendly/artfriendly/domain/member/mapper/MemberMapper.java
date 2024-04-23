package com.artfriendly.artfriendly.domain.member.mapper;

import com.artfriendly.artfriendly.domain.mbti.mapper.MbtiMapper;
import com.artfriendly.artfriendly.domain.mbti.service.MbtiService;
import com.artfriendly.artfriendly.domain.member.dto.MemberDetailsRspDto;
import com.artfriendly.artfriendly.domain.member.dto.MemberResponseDto;
import com.artfriendly.artfriendly.domain.member.dto.MemberUpdateReqDto;
import com.artfriendly.artfriendly.domain.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { MbtiMapper.class, MbtiService.class })
public interface MemberMapper {
    @Mapping(source = "image.imageUrl", target = "imageUrl")
    MemberResponseDto memberToMemberResponseDto(Member member);
    @Mapping(source = "image.imageUrl", target = "imageUrl")
    @Mapping(source = "mbti", target = "mbtiSimpleRspDto")
    @Mapping(source = "artPreferenceTypeList", target = "artPreferenceTypeList")
    MemberDetailsRspDto memberToMemberDetailsRspDto(Member member);
    List<MemberResponseDto> membersToMemberResponseDtos(List<Member> memberList);
    @Mapping(target = "mbti", source = "mbtiId")
    Member memberUpdateReqDtoToMember(MemberUpdateReqDto memberUpdateReqDto);
}
