package com.artfriendly.artfriendly.domain.auth.controller;

import com.artfriendly.artfriendly.domain.auth.dto.RefreshTokenRequest;
import com.artfriendly.artfriendly.domain.member.service.MemberService;
import com.artfriendly.artfriendly.domain.member.entity.Member;
import com.artfriendly.artfriendly.domain.auth.cache.OAuthOTUCache;
import com.artfriendly.artfriendly.domain.auth.dto.TokenResponse;
import com.artfriendly.artfriendly.domain.auth.jwt.JwtTokenizer;
import com.artfriendly.artfriendly.global.api.RspTemplate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/oauth")
public class OAuthMemberController {
    private final OAuthOTUCache oAuthOTUCache;
    private final JwtTokenizer jwtTokenizer;
    private final MemberService memberService;

    @GetMapping("/token")
    public RspTemplate<TokenResponse> getToken(String code) {
        long memberId = oAuthOTUCache.getMemberId(code);
        Member member = memberService.findById(memberId);

        TokenResponse tokenResponse = jwtTokenizer.generateTokens(member);
        return new RspTemplate<>(HttpStatus.OK, "토큰 발급 성공", tokenResponse);
    }

    @PostMapping("/token/renew")
    public RspTemplate<TokenResponse> renewTokens(@RequestBody @Valid RefreshTokenRequest tokenRequest) {
        TokenResponse tokenResponse = jwtTokenizer.renewTokens(tokenRequest.refreshToken());
        return new RspTemplate<>(HttpStatus.OK, "토큰 재발급 성공", tokenResponse);
    }

}