package com.artfriendly.artfriendly.domain.dambyeolag.service.dambyeolag;

import com.artfriendly.artfriendly.domain.dambyeolag.dto.dambyeolag.DambyeolagDetailsRspDto;
import com.artfriendly.artfriendly.domain.dambyeolag.dto.dambyeolag.DambyeolagReqDto;
import com.artfriendly.artfriendly.domain.dambyeolag.dto.dambyeolag.DambyeolagRspDto;
import com.artfriendly.artfriendly.domain.dambyeolag.entity.Dambyeolag;
import com.artfriendly.artfriendly.domain.dambyeolag.entity.DambyeolagBookmark;
import com.artfriendly.artfriendly.domain.dambyeolag.entity.Sticker;
import com.artfriendly.artfriendly.domain.dambyeolag.mapper.DambyeolagMapper;
import com.artfriendly.artfriendly.domain.dambyeolag.mapper.StickerMapper;
import com.artfriendly.artfriendly.domain.dambyeolag.repository.DambyeolagBookmarkRepository;
import com.artfriendly.artfriendly.domain.dambyeolag.repository.DambyeolagRepository;
import com.artfriendly.artfriendly.domain.member.entity.Member;
import com.artfriendly.artfriendly.domain.member.mapper.MemberMapper;
import com.artfriendly.artfriendly.domain.member.service.MemberService;
import com.artfriendly.artfriendly.global.exception.common.BusinessException;
import com.artfriendly.artfriendly.global.exception.common.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DambyeolagServiceImpl implements DambyeolagService {
    private final MemberService memberService;
    private final DambyeolagRepository dambyeolagRepository;
    private final DambyeolagBookmarkRepository dambyeolagBookmarkRepository;
    private final DambyeolagMapper dambyeolagMapper;
    private final StickerMapper stickerMapper;
    private final MemberMapper memberMapper;

    // 담벼락 상세 정보 조회
    @Override
    public DambyeolagDetailsRspDto getDetailsDambyeolag(long memberId, long dambyeolagId) {
        Dambyeolag dambyeolag = findById(dambyeolagId);

        boolean isBookmark = isBookmark(dambyeolag.getBookmarkList(), memberId);
        boolean isSticker = isSticker(dambyeolag.getStickerList(), memberId);

        return dambyeolagMapper.dambyeolagToDambyeolagDetailsRspDto(dambyeolag, isBookmark, isSticker, memberMapper, stickerMapper);
    }

    // 해당 전시 담벼락 조회
    @Override
    public Page<DambyeolagRspDto> getDambyeolagPageOrderByStickerDesc(int page, long exhibitionId) {
        Pageable pageable = PageRequest.of(page, 4);
        Page<Dambyeolag> dambyeolagPage = dambyeolagRepository.findByOrderByStickerCountDesc(pageable, exhibitionId);

        return dambyeolagMapper.dambyeolagPageTodambyeolagRspDtoPage(dambyeolagPage);
    }

    @Override
    @Transactional
    public void createDambyeolag(DambyeolagReqDto dambyeolagReqDto, long memberId) {
        Member member = memberService.findById(memberId);
        Dambyeolag newDambyeolag = dambyeolagMapper.dambyeolagReqDtoToDambyeolag(dambyeolagReqDto, member);

        dambyeolagRepository.save(newDambyeolag);
    }

    @Override
    public void deleteDambyeolag(long memberId, long dambyeolagId) {
        Dambyeolag dambyeolag = findById(dambyeolagId);
        checkDambyeolagOner(dambyeolag, memberId);

        dambyeolagRepository.delete(dambyeolag);
    }

    @Override
    public void deleteBookmark(long memberId, long dambyeolagBookmarkId) {
        DambyeolagBookmark dambyeolagBookmark = findDambyeolagBookmarkById(dambyeolagBookmarkId);
        checkDambyeolagBookmarkOner(dambyeolagBookmark, memberId);

        dambyeolagBookmarkRepository.delete(dambyeolagBookmark);
    }

    @Override
    public void addBookmark(long memberId, long dambyeolagId) {
        Member member = memberService.findById(memberId);
        Dambyeolag dambyeolag = findById(dambyeolagId);

        DambyeolagBookmark dambyeolagBookmark = DambyeolagBookmark.builder()
                .member(member)
                .dambyeolag(dambyeolag)
                .build();

        dambyeolagBookmarkRepository.save(dambyeolagBookmark);
    }

    @Override
    public Dambyeolag findById(long dambyeolagId) {
        Optional<Dambyeolag> dambyeolag = dambyeolagRepository.findDambyeolagById(dambyeolagId);

        return dambyeolag.orElseThrow(() -> new BusinessException(ErrorCode.DAMBYEOLAG_NOT_FOUND));
    }

    private DambyeolagBookmark findDambyeolagBookmarkById(long dambyeolagBookmarkId) {
        Optional<DambyeolagBookmark> dambyeolagBookmark = dambyeolagBookmarkRepository.findById(dambyeolagBookmarkId);

        return dambyeolagBookmark.orElseThrow(() -> new BusinessException(ErrorCode.DAMBYEOLAGBOOKMARK_NOT_FOUND));
    }

    private boolean isBookmark(List<DambyeolagBookmark> bookmarks, long memberId) {
        boolean check = false;

        for(DambyeolagBookmark dambyeolagBookmark : bookmarks) {
            if (dambyeolagBookmark.getMember().getId() == memberId) {
                check = true;
                break;
            }
        }
        return check;
    }

    private boolean isSticker(List<Sticker> stickers, long memberId) {
        boolean check = false;

        for(Sticker sticker : stickers) {
            if (sticker.getMember().getId() == memberId) {
                check = true;
                break;
            }
        }
        return check;
    }

    private void checkDambyeolagOner(Dambyeolag dambyeolag, long memberId) {
        if(dambyeolag.getMember().getId() != memberId)
            throw new BusinessException(ErrorCode.DAMBYEOLAG_NOT_ACCESS);
    }

    private void checkDambyeolagBookmarkOner(DambyeolagBookmark dambyeolagBookmark, long memberId) {
        if(dambyeolagBookmark.getMember().getId() != memberId)
            throw new BusinessException(ErrorCode.DAMBYEOLAGBOOKMARK_NOT_ACCESS);
    }
}
