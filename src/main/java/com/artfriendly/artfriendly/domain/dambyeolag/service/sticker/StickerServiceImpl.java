package com.artfriendly.artfriendly.domain.dambyeolag.service.sticker;

import com.artfriendly.artfriendly.domain.dambyeolag.dto.sticker.StickerReqDto;
import com.artfriendly.artfriendly.domain.dambyeolag.dto.sticker.StickerRspDto;
import com.artfriendly.artfriendly.domain.dambyeolag.entity.Dambyeolag;
import com.artfriendly.artfriendly.domain.dambyeolag.entity.Sticker;
import com.artfriendly.artfriendly.domain.dambyeolag.mapper.StickerMapper;
import com.artfriendly.artfriendly.domain.dambyeolag.repository.StickerRepository;
import com.artfriendly.artfriendly.domain.dambyeolag.service.dambyeolag.DambyeolagService;
import com.artfriendly.artfriendly.domain.member.entity.Member;
import com.artfriendly.artfriendly.domain.member.service.MemberService;
import com.artfriendly.artfriendly.global.exception.common.BusinessException;
import com.artfriendly.artfriendly.global.exception.common.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StickerServiceImpl implements StickerService{
    private final MemberService memberService;
    private final DambyeolagService dambyeolagService;
    private final StickerMapper stickerMapper;
    private final StickerRepository stickerRepository;

    @Override
    public StickerRspDto getStickerById(long stickerId) {
        Sticker sticker = findStickerById(stickerId);
        return stickerMapper.stickerToStickerRspDto(sticker);
    }

    @Override
    @Transactional
    public void deleteSticker(long memberId, long stickerId) {
        Sticker sticker = findStickerById(stickerId);
        checkOner(sticker, memberId);

        stickerRepository.delete(sticker);
    }

    @Override
    @Transactional
    public void addSticker(StickerReqDto stickerReqDto, long memberId) {
        Member member = memberService.findById(memberId);
        Dambyeolag dambyeolag =  dambyeolagService.findById(stickerReqDto.dambyeolagId());
        Sticker sticker = stickerMapper.stickerReqDtoToSticker(stickerReqDto, member, dambyeolag);

        stickerRepository.save(sticker);
    }

    @Override
    public Sticker findStickerById(long stickerId) {
        Optional<Sticker> sticker = stickerRepository.findById(stickerId);
        return sticker.orElseThrow(() -> new BusinessException(ErrorCode.STICKER_NOT_FOUND));
    }

    private void checkOner(Sticker sticker, long memberId) {
        if(sticker.getMember().getId() != memberId)
            throw new BusinessException(ErrorCode.STICKER_NOT_ACCESS);
    }
}
