package com.artfriendly.artfriendly.domain.auth.dto;

import com.artfriendly.artfriendly.global.exception.common.BusinessException;
import com.artfriendly.artfriendly.global.exception.common.ErrorCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuth2Attributes {
    private Map<String, Object> attributes;
    private String provider;
    private String email;
    private String nickName;
    private String imageUrl;

    @Builder
    public OAuth2Attributes(Map<String, Object> attributes, String provider, String email, String nickName, String imageUrl) {
        this.attributes = attributes;
        this.provider = provider;
        this.email = email;
        this.nickName = nickName;
        this.imageUrl = imageUrl;
    }

    public static OAuth2Attributes of(String provider, Authentication authentication) {
        if (!provider.equals("kakao")) {
            throw new BusinessException(ErrorCode.OAUTH2_PLATFORM_MISMATCH);
        }
        return ofKakao(authentication, provider);
    }

    private static OAuth2Attributes ofKakao(Authentication authentication, String provider) {
        var oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, String> properties = (Map<String, String>) oAuth2User.getAttributes().get("properties");
        Map<String, String> kakaoAccount = (Map<String, String>) oAuth2User.getAttributes().get("kakao_account");

        return OAuth2Attributes.builder()
                .attributes(oAuth2User.getAttributes())
                .provider(provider)
                .email(kakaoAccount.get("email"))
                .nickName(properties.get("nickname"))
                .imageUrl(properties.get("thumbnail_image"))
                .build();
    }
}
