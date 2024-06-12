package com.artfriendly.artfriendly.global.init.controller;

import com.artfriendly.artfriendly.domain.exhibition.service.ExhibitionService;
import com.artfriendly.artfriendly.domain.mbti.service.MbtiService;
import com.artfriendly.artfriendly.domain.member.service.MemberService;
import com.artfriendly.artfriendly.domain.term.service.TermService;
import com.artfriendly.artfriendly.global.api.RspTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("init")
public class InitController {
    private final MemberService memberService;
    private final MbtiService mbtiService;
    private final ExhibitionService exhibitionService;
    private final TermService termService;

    @PostMapping
    public RspTemplate<Void> initDefaultData() {
        memberService.initWithdrawalReason();
        mbtiService.initMbit();
        termService.initTerms();
        exhibitionService.clearPopularExhibitionCache();
        return new RspTemplate<>(HttpStatus.OK, "데이터베이스 초기화 완료");
    }
}
