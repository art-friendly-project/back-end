package com.artfriendly.artfriendly.domain.member.service;

import com.artfriendly.artfriendly.domain.auth.dto.OAuth2LoginDto;
import com.artfriendly.artfriendly.domain.member.dto.MemberDetailsRspDto;
import com.artfriendly.artfriendly.domain.member.dto.MemberUpdateReqDto;
import com.artfriendly.artfriendly.domain.member.dto.ProfileDto;
import com.artfriendly.artfriendly.domain.member.entity.Member;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MemberService {
    OAuth2LoginDto oauth2Login(String provider, Authentication authentication);
    Member createMember(String email, String nickName, String imageUrl);
    Member findById(Long id);
    MemberDetailsRspDto getMemberDetailsRspDto(long memberId);
    ProfileDto getProfileDto(long memberId);
    void updateMember(MemberUpdateReqDto memberUpdateReqDto, long memberId);
    void updateMemberImage(MultipartFile image, long memberId) throws IOException;
    void deleteMemberImage(Member member);
    void accountDeletion(long memberId);
}
