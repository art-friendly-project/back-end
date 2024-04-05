package com.artfriendly.artfriendly.domain.member.service;

import com.artfriendly.artfriendly.domain.member.dto.MemberUpdateReqDto;
import com.artfriendly.artfriendly.domain.member.entity.Member;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MemberService {
    Member oauth2Login(String provider, Authentication authentication);
    Member createMember(String email, String nickName, String imageUrl);
    Member findById(Long id);
    void updateMember(MemberUpdateReqDto memberUpdateReqDto, long memberId);
    void updateMemberImage(MultipartFile image, long memberId) throws IOException;

}
