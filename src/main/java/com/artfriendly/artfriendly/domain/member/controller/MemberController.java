package com.artfriendly.artfriendly.domain.member.controller;

import com.artfriendly.artfriendly.domain.member.dto.MemberDetailsRspDto;
import com.artfriendly.artfriendly.domain.member.dto.MemberUpdateReqDto;
import com.artfriendly.artfriendly.domain.member.dto.ProfileDto;
import com.artfriendly.artfriendly.domain.member.service.MemberService;
import com.artfriendly.artfriendly.global.api.RspTemplate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private static final String MEMBER_ID = "memberId: ";

    @GetMapping
    public RspTemplate<MemberDetailsRspDto> getMember(@AuthenticationPrincipal long memberId) {
        MemberDetailsRspDto memberDetailsRspDto = memberService.getMemberDetailsRspDto(memberId);
        return new RspTemplate<>(HttpStatus.OK, MEMBER_ID + memberId + " 멤버 정보", memberDetailsRspDto);
    }

    @GetMapping("profiles")
    public RspTemplate<ProfileDto> getProfile(@AuthenticationPrincipal long memberId, long searchMemberId) {
        ProfileDto profileDto = memberService.getProfileDto(searchMemberId);
        return new RspTemplate<>(HttpStatus.OK, MEMBER_ID + memberId + " 프로필 정보", profileDto);
    }

    @PatchMapping
    public RspTemplate<Void> updateMember(@AuthenticationPrincipal long memberId, @Valid @RequestBody MemberUpdateReqDto updateReqDto) {
        memberService.updateMember(updateReqDto, memberId);
        return new RspTemplate<>(HttpStatus.OK, MEMBER_ID + memberId + " 멤버 정보 수정");
    }

    @DeleteMapping
    public RspTemplate<Void> deleteMember(@AuthenticationPrincipal long memberId) {
        memberService.accountDeletion(memberId);
        return new RspTemplate<>(HttpStatus.OK, MEMBER_ID + memberId + "멤버 회원 탈퇴");
    }

    @PatchMapping(path = "/images", consumes = "multipart/form-data")
    public RspTemplate<Void> updateMemberImage(@AuthenticationPrincipal long memberId,
                                               MultipartFile profileImage) throws IOException {
        memberService.updateMemberImage(profileImage, memberId);
        return new RspTemplate<>(HttpStatus.OK, MEMBER_ID + memberId + " 멤버 이미지 수정");
    }
}
