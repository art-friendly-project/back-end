package com.artfriendly.artfriendly.domain.mbti.service;

import com.artfriendly.artfriendly.domain.mbti.dto.MbtiReqDto;
import com.artfriendly.artfriendly.domain.mbti.dto.MbtiRspDto;
import com.artfriendly.artfriendly.domain.mbti.entity.Mbti;
import com.artfriendly.artfriendly.domain.mbti.mapper.MbtiMapper;
import com.artfriendly.artfriendly.domain.mbti.repository.MbtiRepository;
import com.artfriendly.artfriendly.global.exception.common.BusinessException;
import com.artfriendly.artfriendly.global.exception.common.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MbtiService {
    private final MbtiRepository mbtiRepository;
    private final MbtiMapper mbtiMapper;

    public MbtiRspDto getMbtiRspDtoByMbtiType(String mbtiType) {
        Mbti mbti = mbtiRepository.findByMbtiType(mbtiType);
        return mbtiMapper.mbtiToMbtiRspDto(mbti);
    }

    public Mbti findMbtiById(long mbtiId) {
        Optional<Mbti> mbti = mbtiRepository.findById(mbtiId);
        return mbti.orElseThrow(() -> new BusinessException(ErrorCode.MBTI_NOT_FOUND));
    }

    @Transactional
    public void createMbti(MbtiReqDto mbtiReqDto) {
        Mbti matchType = mbtiRepository.findByMbtiType(mbtiReqDto.mbtiType());
        Mbti missMatchType = mbtiRepository.findByMbtiType(mbtiReqDto.missMatchType());
        Mbti mbti = mbtiMapper.mbtiReqDtoToMbti(mbtiReqDto, matchType, missMatchType);

        mbtiRepository.save(mbti);
    }
}
