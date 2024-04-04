package com.artfriendly.artfriendly.domain.member.controller;

import com.artfriendly.artfriendly.domain.member.dto.MemberResponseDto;
import com.artfriendly.artfriendly.domain.member.dto.MemberUpdateReqDto;
import com.artfriendly.artfriendly.domain.member.mapper.MemberMapper;
import com.artfriendly.artfriendly.domain.member.service.MemberService;
import com.artfriendly.artfriendly.global.api.RspTemplate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberMapper memberMapper;
    private final MemberService memberService;

    @GetMapping
    public RspTemplate<MemberResponseDto> getMember(@AuthenticationPrincipal long memberId) {
        MemberResponseDto memberResponseDto = memberMapper.memberToMemberResponseDto(memberService.findById(memberId));
        return new RspTemplate<>(HttpStatus.OK, "멤버 정보", memberResponseDto);
    }

    @PatchMapping
    public RspTemplate<Void> updateMember(@AuthenticationPrincipal long memberId, @Valid @RequestBody MemberUpdateReqDto updateReqDto) {
        memberService.updateMember(updateReqDto, memberId);
        return new RspTemplate<>(HttpStatus.OK, "멤버 정보 수정");
    }
}
