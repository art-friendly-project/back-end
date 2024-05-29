package com.artfriendly.artfriendly.domain.auth.service;

import com.artfriendly.artfriendly.domain.member.entity.Member;
import com.artfriendly.artfriendly.domain.member.entity.RefreshToken;
import com.artfriendly.artfriendly.domain.member.repository.RefreshTokenRepository;
import com.artfriendly.artfriendly.global.exception.common.BusinessException;
import com.artfriendly.artfriendly.global.exception.common.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JwtService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void updateRefreshToken(String refreshToken, Member member) {
        RefreshToken refreshTokenEntity = member.getRefreshToken();
        refreshTokenEntity.updateRefreshToken(refreshToken);

        refreshTokenRepository.save(refreshTokenEntity);
    }

    public RefreshToken findRefreshToken(String token) {
      return refreshTokenRepository.findByToken(token).orElseThrow(() -> new BusinessException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));
    }
}
