package com.artfriendly.artfriendly.domain.mbti.controller;

import com.artfriendly.artfriendly.domain.mbti.dto.MbtiRspDto;
import com.artfriendly.artfriendly.domain.mbti.service.MbtiService;
import com.artfriendly.artfriendly.global.api.RspTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mbti")
@RequiredArgsConstructor
public class MbtiController {
    private final MbtiService mbtiService;

    @GetMapping
    public RspTemplate<MbtiRspDto> getMbtiRspDtoByMbtiType(String mbtiType) {
        MbtiRspDto mbtiRspDto = mbtiService.getMbtiRspDtoByMbtiType(mbtiType);
        return new RspTemplate<>(HttpStatus.OK, mbtiType+"타입 조회", mbtiRspDto);
    }

}
