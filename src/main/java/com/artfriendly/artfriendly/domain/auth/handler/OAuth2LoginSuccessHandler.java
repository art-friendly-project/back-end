package com.artfriendly.artfriendly.domain.auth.handler;

import com.artfriendly.artfriendly.domain.auth.cache.OAuthOTUCache;
import com.artfriendly.artfriendly.domain.member.entity.Member;
import com.artfriendly.artfriendly.domain.member.service.MemberService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final MemberService memberService;
    private final OAuthOTUCache oAuthOTUCache;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        String provider = oauthToken.getAuthorizedClientRegistrationId();

        Member member = memberService.oauth2Login(provider, authentication);

        redirect(request, response, member);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, Member member) throws IOException {
        log.info("OAuth2 Login Success!!");
        String verificationCode = oAuthOTUCache.putVerificationCodeInCache(member.getId());
        String uri = createURI(verificationCode).toString();

        getRedirectStrategy().sendRedirect(request, response, uri);
    }

    // OAuth2 로그인 성공 시 토큰값과 함께 반환될 URL 설정하는 부분
    private URI createURI(String verificationCode) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("oneTimeUseCode", verificationCode);

        return UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("localhost")
                //.port(80)
                .queryParams(queryParams)
                .path("/receive-token")
                .build()
                .toUri();
    }

}
