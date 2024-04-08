package com.artfriendly.artfriendly.domain.member.mapper;

import com.artfriendly.artfriendly.domain.member.dto.MemberResponseDto;
import com.artfriendly.artfriendly.domain.member.dto.MemberUpdateReqDto;
import com.artfriendly.artfriendly.domain.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    @Mapping(source = "member.image.imageUrl", target = "imageUrl")
    MemberResponseDto memberToMemberResponseDto(Member member);
    List<MemberResponseDto> membersToMemberResponseDtos(List<Member> memberList);
    Member memberUpdateReqDtoToMember(MemberUpdateReqDto memberUpdateReqDto);
}
