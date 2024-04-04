package com.artfriendly.artfriendly.domain.member.service;

import com.artfriendly.artfriendly.domain.auth.dto.OAuth2Attributes;
import com.artfriendly.artfriendly.domain.member.dto.MemberUpdateReqDto;
import com.artfriendly.artfriendly.domain.member.entity.Member;
import com.artfriendly.artfriendly.domain.member.mapper.MemberMapper;
import com.artfriendly.artfriendly.domain.member.repository.MemberRepository;
import com.artfriendly.artfriendly.global.exception.common.BusinessException;
import com.artfriendly.artfriendly.global.exception.common.ErrorCode;
import com.artfriendly.artfriendly.global.utils.CustomAuthorityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Primary
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final CustomAuthorityUtils customAuthorityUtils;
    private final MemberMapper memberMapper;

    @Override
    @Transactional
    public Member oauth2Login(String provider, Authentication authentication) {
        OAuth2Attributes oAuth2Attributes = OAuth2Attributes.of(provider, authentication);
        Optional<Member> member = findOptionalMemberByEmail(oAuth2Attributes.getEmail());
        // 멤버가 존재 하지 않을 때 회원 가입, 있을 경우 Member 객체 반환
        return member.orElseGet(() -> createMember(
                oAuth2Attributes.getEmail(),
                oAuth2Attributes.getNickName(),
                oAuth2Attributes.getImageUrl()));
    }

    @Override
    @Transactional
    public Member createMember(String email, String nickname, String imageUrl) {
        Member member = Member.builder()
                .email(email)
                .nickName(nickname)
                .imageUrl(imageUrl)
                .role(customAuthorityUtils.createUserRoles())
                .build();
        return memberRepository.save(member);
    }

    @Override
    public Member findById(Long memberId) {
        Optional<Member> member = memberRepository.findOptionalMemberById(memberId);
        return member.orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    @Transactional
    public void updateMember(MemberUpdateReqDto memberUpdateReqDto, long memberId) {
        Member member = findById(memberId);
        member.updateForm(memberMapper.memberUpdateReqDtoToMember(memberUpdateReqDto));
    }

    private Optional<Member> findOptionalMemberByEmail(String email) {
        return memberRepository.findOptionalMemberByEmail(email);
    }
}
