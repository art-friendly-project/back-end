package com.artfriendly.artfriendly.domain.auth.jwt;

import com.artfriendly.artfriendly.domain.auth.dto.TokenResponse;
import com.artfriendly.artfriendly.domain.auth.service.JwtService;
import com.artfriendly.artfriendly.domain.member.entity.Member;
import com.artfriendly.artfriendly.domain.member.entity.RefreshToken;
import com.artfriendly.artfriendly.global.exception.common.BusinessException;
import com.artfriendly.artfriendly.global.exception.common.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenizer {
    private final JwtService jwtService;

    @Getter
    @Value("${jwt.key}")
    private String secretKey;       // 토큰 시크릿 키

    @Getter
    @Value("${jwt.access-token-expiration-minutes}")
    private int accessTokenExpirationMinutes;     // accessToken 만료 시간

    @Getter
    @Value("${jwt.refresh-token-expiration-minutes}")
    private int refreshTokenExpirationMinutes;    // refreshToken 만료 시간

    public TokenResponse generateTokens(Member member) {
        String accessToken = generateAccessToken(member);
        String refreshToken = generateRefreshToken();

        jwtService.updateRefreshToken(refreshToken, member);

        return TokenResponse.builder()
                .authScheme("Bearer")
                .accessToken(accessToken)
                .accessTokenExp(getTokenExpiration(this.accessTokenExpirationMinutes))
                .refreshToken(refreshToken)
                .refreshTokenExp(getTokenExpiration(this.refreshTokenExpirationMinutes))
                .role(member.getRole().toString())
                .username(member.getNickName())
                .build();
    }

    public String generateAccessToken(Map<String, Object> claims,
                                      String audience) {
        Key key = createHmacShaKeyFromSecretKey(this.secretKey);
        Date expiration = getTokenExpiration(this.accessTokenExpirationMinutes);

        return Jwts.builder()
                .setClaims(claims)
                .setAudience(audience)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    public String generateAccessToken(Member member) {
        Map<String, Object> map = new HashMap<>();
        map.put("roles", member.getRole());
        return generateAccessToken(map, member.getId().toString());
    }

    public String generateRefreshToken() {
        Key key = createHmacShaKeyFromSecretKey(this.secretKey);
        Date expiration = getTokenExpiration(this.refreshTokenExpirationMinutes);

        return Jwts.builder()
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    public Member verifyRefreshToken(String token) {
        RefreshToken refreshToken = jwtService.findRefreshToken(token);

        try {
            verifySignature(token, getSecretKey());
        } catch (SignatureException e) {
            throw new BusinessException(ErrorCode.INVALID_SIGNATURE);
        } catch (ExpiredJwtException e) {
            throw new BusinessException(ErrorCode.ACCESS_TOKEN_EXPIRED);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.NOT_VALID_TOKEN);
        }

        return refreshToken.getMember();
    }

    public TokenResponse renewTokens(String refreshToken) {
        Member member = verifyRefreshToken(refreshToken);
        return generateTokens(member);
    }

    public Jws<Claims> getClaims(String jws, String secretKey) {
        Key key = createHmacShaKeyFromSecretKey(secretKey);

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);
    }

    // refreshToken 검증 할 때 사용되는 것
    public void verifySignature(String jws, String secretKey) {
        Key key = createHmacShaKeyFromSecretKey(secretKey);

        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);
    }

    private Date getTokenExpiration(int expirationMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expirationMinutes);

        return calendar.getTime();
    }

    private Key createHmacShaKeyFromSecretKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
