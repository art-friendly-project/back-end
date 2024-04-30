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
    public RspTemplate<ProfileDto> getProfile(@AuthenticationPrincipal long memberId) {
        ProfileDto profileDto = memberService.getProfileDto(memberId);
        return new RspTemplate<>(HttpStatus.OK, MEMBER_ID + memberId + " 프로필 정보", profileDto);
    }

    @PatchMapping
    public RspTemplate<Void> updateMember(@AuthenticationPrincipal long memberId, @Valid @RequestBody MemberUpdateReqDto updateReqDto) {
        memberService.updateMember(updateReqDto, memberId);
        return new RspTemplate<>(HttpStatus.OK, MEMBER_ID + memberId + " 멤버 정보 수정");
    }

    @PatchMapping("/images")
    public RspTemplate<Void> updateMemberImage(@AuthenticationPrincipal long memberId,
                                               @RequestPart(value = "profileImage") MultipartFile image) throws IOException {
        memberService.updateMemberImage(image, memberId);
        return new RspTemplate<>(HttpStatus.OK, MEMBER_ID + memberId + " 멤버 이미지 수정");
    }
}
