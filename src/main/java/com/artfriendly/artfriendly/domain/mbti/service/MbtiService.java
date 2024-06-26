package com.artfriendly.artfriendly.domain.mbti.service;

import com.artfriendly.artfriendly.domain.mbti.dto.MbtiReqDto;
import com.artfriendly.artfriendly.domain.mbti.dto.MbtiRspDto;
import com.artfriendly.artfriendly.domain.mbti.entity.Mbti;

public interface MbtiService {
    MbtiRspDto getMbtiRspDtoByMbtiType(String mbtiType);
    Mbti findMbtiById(long mbtiId);
    void createMbti(MbtiReqDto mbtiReqDto);
    void initMbit();
}
