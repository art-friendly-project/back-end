package com.artfriendly.artfriendly.domain.mbti.service;

import com.artfriendly.artfriendly.domain.mbti.dto.MbtiReqDto;
import com.artfriendly.artfriendly.domain.mbti.dto.MbtiRspDto;
import com.artfriendly.artfriendly.domain.mbti.entity.Mbti;
import org.springframework.transaction.annotation.Transactional;

public interface MbtiService {
    MbtiRspDto getMbtiRspDtoByMbtiType(String mbtiType);

    Mbti findMbtiById(long mbtiId);

    @Transactional
    void createMbti(MbtiReqDto mbtiReqDto);

    @Transactional
    void initMbit();
}
