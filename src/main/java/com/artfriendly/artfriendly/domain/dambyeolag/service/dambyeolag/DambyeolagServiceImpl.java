package com.artfriendly.artfriendly.domain.dambyeolag.service.dambyeolag;

import com.artfriendly.artfriendly.domain.dambyeolag.dto.dambyeolag.*;
import com.artfriendly.artfriendly.domain.dambyeolag.entity.Dambyeolag;
import com.artfriendly.artfriendly.domain.dambyeolag.entity.DambyeolagBookmark;
import com.artfriendly.artfriendly.domain.dambyeolag.entity.Sticker;
import com.artfriendly.artfriendly.domain.dambyeolag.mapper.DambyeolagMapper;
import com.artfriendly.artfriendly.domain.dambyeolag.mapper.StickerMapper;
import com.artfriendly.artfriendly.domain.dambyeolag.repository.DambyeolagBookmarkRepository;
import com.artfriendly.artfriendly.domain.dambyeolag.repository.DambyeolagRepository;
import com.artfriendly.artfriendly.domain.exhibition.entity.Exhibition;
import com.artfriendly.artfriendly.domain.exhibition.service.ExhibitionService;
import com.artfriendly.artfriendly.domain.member.entity.Member;
import com.artfriendly.artfriendly.domain.member.mapper.MemberMapper;
import com.artfriendly.artfriendly.domain.member.service.MemberService;
import com.artfriendly.artfriendly.global.exception.common.BusinessException;
import com.artfriendly.artfriendly.global.exception.common.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
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
    private final ExhibitionService exhibitionService;
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
    public Page<DambyeolagRspDto> getDambyeolagPageOrderBySortType(int page, long exhibitionId, String sortType) {
        Pageable pageable = PageRequest.of(page, 8);
        Page<Dambyeolag> dambyeolagPage;
        // 인기순 정렬
        if(sortType.equalsIgnoreCase("popular"))
            dambyeolagPage = dambyeolagRepository.findByOrderByStickerCountDesc(pageable, exhibitionId);
        // 최신순 정렬
        else if(sortType.equalsIgnoreCase("recent"))
            dambyeolagPage = dambyeolagRepository.findAllByOrderByCreateTimeDesc(pageable, exhibitionId);
        else
            throw new BusinessException(ErrorCode.SORT_TYPE_NOT_FOUND);


        return dambyeolagMapper.dambyeolagPageTodambyeolagRspDtoPage(dambyeolagPage);
    }

    @Override
    public Page<DambyeolagImageRspDto> getDambyeolagPageByMemberIdOrderByCreateTime(int page, long memberId) {
        Pageable pageable = PageRequest.of(page, 8);
        Page<Dambyeolag> dambyeolagPage = dambyeolagRepository.findDambyeolagByMemberIdOrderByCreateTimeDesc(pageable, memberId);
        return dambyeolagMapper.dambyeolagPageToDambyeolagImageRspDto(dambyeolagPage);
    }

    @Override
    public Page<DambyeolagImageRspDto> getBookmarkDambyeolagPageOrderByCreateTime(int page, long memberId) {
        Pageable pageable = PageRequest.of(page, 8);
        Page<Dambyeolag> dambyeolagPage = dambyeolagRepository.findBookmarkDambyeolagsByMemberIdOrderByLastModifiedTimeDesc(pageable, memberId);
        return dambyeolagMapper.dambyeolagPageToDambyeolagImageRspDto(dambyeolagPage);
    }

    @Override
    public Dambyeolag findById(long dambyeolagId) {
        Optional<Dambyeolag> dambyeolag = dambyeolagRepository.findDambyeolagById(dambyeolagId);

        return dambyeolag.orElseThrow(() -> new BusinessException(ErrorCode.DAMBYEOLAG_NOT_FOUND));
    }

    @Override
    @Transactional
    @CacheEvict(value = "exhibitionDetailsCache", allEntries = true)
    public void createDambyeolag(DambyeolagReqDto dambyeolagReqDto, long memberId) {
        Member member = memberService.findById(memberId);
        Exhibition exhibition = exhibitionService.findExhibitionById(dambyeolagReqDto.exhibitionId());

        if(exhibitionService.hasDambyeolagBeenWritten(dambyeolagReqDto.exhibitionId(), memberId))
            throw new BusinessException(ErrorCode.DAMBYEOLAG_ALREADY_EXIST);

        Dambyeolag newDambyeolag = dambyeolagMapper.dambyeolagReqDtoToDambyeolag(dambyeolagReqDto, member, exhibition);
        dambyeolagRepository.save(newDambyeolag);
    }

    @Override
    @Transactional
    @CacheEvict(value = "exhibitionDetailsCache", allEntries = true)
    public void updateDambyeolag(long memberId, DambyeolagUpdateDto updateDto) {
        Dambyeolag dambyeolag = findById(updateDto.dambyeolagId());
        checkDambyeolagOner(dambyeolag, memberId);

        dambyeolag.updateForm(updateDto);
    }

    @Override
    @Transactional
    @CacheEvict(value = "exhibitionDetailsCache", allEntries = true)
    public void deleteDambyeolag(long memberId, long dambyeolagId) {
        Dambyeolag dambyeolag = findById(dambyeolagId);
        checkDambyeolagOner(dambyeolag, memberId);

        dambyeolagRepository.delete(dambyeolag);
    }

    @Override
    @Transactional
    public void deleteBookmark(long memberId, long dambyeolagId) {
        DambyeolagBookmark dambyeolagBookmark = findDambyeolagBookmarkByDambyeolagIdAndMemberId(memberId, dambyeolagId);

        dambyeolagBookmarkRepository.delete(dambyeolagBookmark);
    }

    @Override
    @Transactional
    public void deleteBookmarksByMember(Member member) {
        List<DambyeolagBookmark> dambyeolagBookmarkList = dambyeolagBookmarkRepository.findDambyeolagBookmarkByMemberId(member.getId());
        member.setDambyeolagBookmarkList(null);

        dambyeolagBookmarkRepository.deleteAll(dambyeolagBookmarkList);
    }

    @Override
    @Transactional
    public void addBookmark(long memberId, long dambyeolagId) {
        Member member = memberService.findById(memberId);
        Dambyeolag dambyeolag = findById(dambyeolagId);

        DambyeolagBookmark dambyeolagBookmark = DambyeolagBookmark.builder()
                .member(member)
                .dambyeolag(dambyeolag)
                .build();

        dambyeolagBookmarkRepository.save(dambyeolagBookmark);
    }

    private DambyeolagBookmark findDambyeolagBookmarkByDambyeolagIdAndMemberId(long memberId, long dambyeolagId) {
        Optional<DambyeolagBookmark> dambyeolagBookmark = dambyeolagBookmarkRepository.findDambyeolagBookmarkByDambyeolagIdAndMemberId(dambyeolagId, memberId);

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

}
