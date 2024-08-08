package com.artfriendly.artfriendly.domain.exhibition.service;

import com.artfriendly.artfriendly.domain.dambyeolag.entity.Dambyeolag;
import com.artfriendly.artfriendly.domain.dambyeolag.repository.DambyeolagRepository;
import com.artfriendly.artfriendly.domain.exhibition.cache.PopularExhibitionCache;
import com.artfriendly.artfriendly.domain.exhibition.dto.ExhibitionDetailsRspDto;
import com.artfriendly.artfriendly.domain.exhibition.dto.ExhibitionRankRspDto;
import com.artfriendly.artfriendly.domain.exhibition.dto.ExhibitionRspDto;
import com.artfriendly.artfriendly.domain.exhibition.entity.*;
import com.artfriendly.artfriendly.domain.exhibition.mapper.ExhibitionMapper;
import com.artfriendly.artfriendly.domain.exhibition.repository.*;
import com.artfriendly.artfriendly.domain.member.entity.Member;
import com.artfriendly.artfriendly.domain.member.service.MemberService;
import com.artfriendly.artfriendly.global.exception.common.BusinessException;
import com.artfriendly.artfriendly.global.exception.common.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Primary
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExhibitionServiceImpl implements ExhibitionService{
    private final PopularExhibitionCache popularExhibitionCache;
    private final MemberService memberService;
    private final ExhibitionRepository exhibitionRepository;
    private final DambyeolagRepository dambyeolagRepository;
    private final ExhibitionInfoRepository exhibitionInfoRepository;
    private final ExhibitionHopeRepository exhibitionHopeRepository;
    private final ExhibitionLikeRepository exhibitionLikeRepository;
    private final ExhibitionViewRepository exhibitionViewRepository;
    private final ExhibitionMapper exhibitionMapper;


    @Override
    @Transactional
    public void createExhibition(ExhibitionInfo exhibitionInfo) {
        Exhibition exhibition = Exhibition.builder()
                .exhibitionInfo(exhibitionInfo)
                .build();

        exhibitionInfo.setExhibition(exhibition);

        exhibitionRepository.save(exhibition);
    }

    @Override
    @Transactional
    public void createExhibitionList(List<ExhibitionInfo> exhibitionInfoList) {
        for(ExhibitionInfo exhibitionInfo : exhibitionInfoList) {
            if(exhibitionInfo == null)
                continue;
            createExhibition(exhibitionInfo);
        }
    }

    @Override
    @Transactional
    @Cacheable(value = "exhibitionDetailsCache" , cacheManager = "exhibitionCache")
    public ExhibitionDetailsRspDto getExhibitionDetailsRpsDtoById(long memberId, long exhibitionId) {
        memberService.findById(memberId);
        Exhibition exhibition = findExhibitionById(exhibitionId);
        String checkTemperature = checkExhibitionTemperature(memberId, exhibition.getExhibitionHopeList());
        boolean isLike = isLike(memberId, exhibition.getExhibitionLikeList());
        boolean hasDambyeolagBeenWritten = hasDambyeolagBeenWritten(exhibitionId, memberId);

        addExhibitionView(memberId, exhibitionId);

        return exhibitionMapper.exhibitionToExhibitionDetailsRspDto(exhibition, checkTemperature, isLike, hasDambyeolagBeenWritten);
    }

    @Override
    @Cacheable(value = "exhibitionPageCache", cacheManager = "exhibitionCache")
    public Page<ExhibitionRspDto> getExhibitionPageRspDto(long memberId, int page, String area, String progressStatus, String sortType) {
        memberService.findById(memberId);

        Pageable pageable = PageRequest.of(page, 8);
        Page<Exhibition> exhibitionPage;

        Map<String, List<String>> areaMap = new HashMap<>();

        areaMap.put("경기/인천", List.of("경기", "인천"));
        areaMap.put("강원", List.of("강원"));
        areaMap.put("경남/부산", List.of("경남", "부산", "울산"));
        areaMap.put("경북/대구", List.of("경북", "대구"));
        areaMap.put("충청/대전", List.of("충북", "세종", "충남", "대전"));
        areaMap.put("전라/광주", List.of("전남", "전북", "광주"));

        List<String> selectedAreas = areaMap.getOrDefault(area, List.of(area)); // 기본값으로 area 사용

        if (sortType.equals("popular")) {
            exhibitionPage = exhibitionRepository.findExhibitionByOrderByTemperatureDesc(pageable, progressStatus, selectedAreas);
        } else if (sortType.equals("recent")) {
            exhibitionPage = exhibitionRepository.findExhibitionByOrderByStartDateDesc(pageable, progressStatus, selectedAreas, LocalDate.now());
        } else {
            exhibitionPage = exhibitionRepository.findExhibitionByOrderByTemperatureDesc(pageable, progressStatus, selectedAreas);
        }

        return exhibitionMapper.exhibitionPageToExhibitionRspDto(exhibitionPage, memberId);
    }

    @Override
    @Cacheable(value = "interestExhibitionPageCache", cacheManager = "exhibitionCache")
    public Page<ExhibitionRspDto> getInterestExhibitionPageRspDto(long memberId, int page) {
        memberService.findById(memberId);

        Pageable pageable = PageRequest.of(page, 8);
        Page<Exhibition> exhibitionPage = exhibitionRepository.findExhibitionByMemberIdOrderByLastModifiedTime(pageable, memberId);

        return exhibitionMapper.exhibitionPageToExhibitionRspDto(exhibitionPage, memberId);
    }

    @Override
    public Exhibition findExhibitionById(long exhibitionId) {
        return findOptionalExhibitionById(exhibitionId).orElseThrow(() -> new BusinessException(ErrorCode.EXHIBITION_NOT_FOUND));
    }

    @Override
    @Transactional
    @Caching(evict =  {
            @CacheEvict(value = "exhibitionDetailsCache", allEntries = true),
            @CacheEvict(value = "exhibitionPageCache", allEntries = true),
            @CacheEvict(value = "interestExhibitionPageCache", allEntries = true),
            @CacheEvict(value = "endingExhibitionCache", allEntries = true, cacheManager = "endingExhibitionCache")
    })
    public void addExhibitionLike(long memberId, long exhibitionId) {
        Member member = memberService.findById(memberId);
        Exhibition exhibition = findExhibitionById(exhibitionId);

        if(findOptionalExhibitionLike(memberId, exhibitionId).isPresent())
            throw new BusinessException(ErrorCode.EXIST_EXHIBITIONLIKE);

        ExhibitionLike exhibitionLike = ExhibitionLike.builder()
                .exhibition(exhibition)
                .member(member)
                .build();

        exhibitionLikeRepository.save(exhibitionLike);
        updateExhibitionTemperature(exhibitionId);
    }

    @Override
    @Transactional
    @Caching(evict =  {
            @CacheEvict(value = "exhibitionDetailsCache", allEntries = true),
            @CacheEvict(value = "exhibitionPageCache", allEntries = true),
            @CacheEvict(value = "interestExhibitionPageCache", allEntries = true),
            @CacheEvict(value = "endingExhibitionCache", allEntries = true, cacheManager = "endingExhibitionCache")
    })
    public void deleteExhibitionLike(long memberId, long exhibitionId) {
        ExhibitionLike exhibitionLike = findExhibitionLikeByMemberIdAndExhibitionId(memberId, exhibitionId);

        exhibitionLikeRepository.delete(exhibitionLike);
        exhibitionLikeRepository.flush(); // 좋아요가 삭제 된 후 DB에 반영 돼야 updateExhibitionTemperature() 메서드가 제대로 동작한다.
        updateExhibitionTemperature(exhibitionId);
    }

    @Override
    @Transactional
    @Caching(evict =  {
            @CacheEvict(value = "exhibitionDetailsCache", allEntries = true),
            @CacheEvict(value = "exhibitionPageCache", allEntries = true),
            @CacheEvict(value = "interestExhibitionPageCache", allEntries = true),
            @CacheEvict(value = "endingExhibitionCache", allEntries = true, cacheManager = "endingExhibitionCache")
    })
    public void addExhibitionHope(long memberId, long exhibitionId, int hopeIndex) {
        Member member = memberService.findById(memberId);
        Exhibition exhibition = findExhibitionById(exhibitionId);

        if(findOptionalExhibitionHope(memberId, exhibitionId).isPresent())
            throw new BusinessException(ErrorCode.EXIST_EXHIBITIONHOPE);

        ExhibitionHope.Hope hope = mapIndexToHope(hopeIndex);
        ExhibitionHope exhibitionHope = ExhibitionHope.builder()
                .member(member)
                .exhibition(exhibition)
                .hope(hope)
                .build();

        exhibitionHopeRepository.save(exhibitionHope);
        updateExhibitionTemperature(exhibitionId);
    }

    @Override
    @Transactional
    @Caching(evict =  {
            @CacheEvict(value = "exhibitionDetailsCache", allEntries = true),
            @CacheEvict(value = "exhibitionPageCache", allEntries = true)
    })
    public void updateExhibitionHope(long memberId, long exhibitionId, int hopeIndex) {
        ExhibitionHope exhibitionHope = findExhibitionHopeByMemberIdAndExhibitionHope(memberId, exhibitionId);
        ExhibitionHope.Hope hope = mapIndexToHope(hopeIndex);

        if(exhibitionHope.getHope() == hope)
            throw new BusinessException(ErrorCode.SAME_EXHIBITIONHOPE);

        exhibitionHope.updateHope(hope);

        exhibitionHopeRepository.save(exhibitionHope);
        updateExhibitionTemperature(exhibitionId);
    }

    @Override
    @Transactional
    @Caching(evict =  {
            @CacheEvict(value = "exhibitionDetailsCache", allEntries = true),
            @CacheEvict(value = "exhibitionPageCache", allEntries = true)
    })
    public void deleteExhibitionHope(long memberId, long exhibitionId) {
        ExhibitionHope exhibitionHope = findExhibitionHopeByMemberIdAndExhibitionHope(memberId, exhibitionId);

        exhibitionHopeRepository.delete(exhibitionHope);
        exhibitionHopeRepository.flush(); // 희망 사항이 삭제 된 후 DB에 반영 돼야 updateExhibitionTemperature() 메서드가 제대로 동작한다.
        updateExhibitionTemperature(exhibitionId);
    }


    @Override
    @Transactional
    public void updateExhibitionTemperature(long exhibitionId) {
        Exhibition exhibition = findExhibitionById(exhibitionId);
        exhibition.updateTemperature();

        exhibitionRepository.save(exhibition);
    }

    @Override
    @Transactional
    public void addExhibitionView(long memberId, long exhibitionId) {
        Optional<ExhibitionView> optionalExhibitionView = findOptionalExhibitionView(memberId, exhibitionId);
        if(optionalExhibitionView.isEmpty()) {
            ExhibitionView exhibitionView = ExhibitionView.builder().
                    member(memberService.findById(memberId))
                    .exhibition(findExhibitionById(exhibitionId))
                    .build();

            exhibitionViewRepository.save(exhibitionView);
            updateExhibitionTemperature(exhibitionId);
        }

    }

    @Override
    @Cacheable(value = "endingExhibitionCache", cacheManager = "endingExhibitionCache")
    public List<ExhibitionRspDto> getTop3ExhibitionsByEndingDate(long memberId) {
        List<Exhibition> exhibitionList = exhibitionRepository.findTop3ByEndDate("inProgress", LocalDate.now());
        return exhibitionMapper.exhibitionsToExhibitionRspDtos(exhibitionList, memberId);
    }

    @Override
    public List<ExhibitionRankRspDto> getTop10PopularExhibitionRankRspDto() {
        return popularExhibitionCache.getExhibitionRankRspDtoList();
    }

    @Override
    public void updateTop10PopularExhibitionRankRspDto() {
        List<ExhibitionRankRspDto> exhibitionRankRspDtoList = new ArrayList<>();
        List<Exhibition> exhibitionList = exhibitionRepository.findTop10ByTemperature("inProgress", LocalDate.now());
        for(int i = 0; i < exhibitionList.size(); i++) {
            Exhibition exhibition = exhibitionList.get(i);
            ExhibitionRankRspDto cacheExhibitionRankRspDto = popularExhibitionCache.getExhibitionRankRspDto(exhibition.getId());
            // 기본 순위에 전시가 없을 경우 새로 입력
            if(cacheExhibitionRankRspDto == null) {
                exhibitionRankRspDtoList.add(exhibitionMapper.exhibitionToExhibitionRankRspDto(exhibition, i+1, "new"));
            }
            else {
                int rankChange = cacheExhibitionRankRspDto.rank() - (i + 1);
                exhibitionRankRspDtoList.add(exhibitionMapper.exhibitionToExhibitionRankRspDto(exhibition, i+1, String.valueOf(rankChange)));
            }
        }
        popularExhibitionCache.clearPopularExhibitionCache();
        popularExhibitionCache.putExhibitionRankRspDtoListInCache(exhibitionRankRspDtoList);
    }

    @Override
    public void clearPopularExhibitionCache() {
        popularExhibitionCache.clearPopularExhibitionCache();
        updateTop10PopularExhibitionRankRspDto();
    }

    @Override
    public void updateExhibitionList(List<ExhibitionInfo> updateExhibitionInfoList) {
        for(ExhibitionInfo exhibitionInfo : updateExhibitionInfoList) {
            if(exhibitionInfo == null)
                continue;
            exhibitionInfoRepository.save(exhibitionInfo);
        }
    }

    @Override
    public boolean hasDambyeolagBeenWritten(long exhibitionId, long memberId) {
        Optional<Dambyeolag> dambyeolag = dambyeolagRepository.findDambyeolagByExhibitionIdAndMemberId(exhibitionId, memberId);
        return dambyeolag.isPresent();
    }

    private ExhibitionHope.Hope mapIndexToHope(int hopeIndex) {
        return switch (hopeIndex) {
            case 1 -> ExhibitionHope.Hope.WANT_TO_SEE;
            case 2 -> ExhibitionHope.Hope.GOOD;
            case 3 -> ExhibitionHope.Hope.INTERESTING;
            case 4 -> ExhibitionHope.Hope.SO_SO;
            case 5 -> ExhibitionHope.Hope.NOT_GOOD;
            default -> throw new BusinessException(ErrorCode.HOPEINDEX_NOT_FOUND);
        };
    }

    private Optional<Exhibition> findOptionalExhibitionById(long exhibitionId) {
        return exhibitionRepository.findById(exhibitionId);
    }

    private String checkExhibitionTemperature(long memberId, List<ExhibitionHope> exhibitionHopeList) {
        for(ExhibitionHope exhibitionHope : exhibitionHopeList) {
            if(exhibitionHope.getMember().getId() == memberId)
                return exhibitionHope.getHope().getMassage();

        }
        return null;
    }

    private boolean isLike(long memberId, List<ExhibitionLike> exhibitionLikeList) {
        for(ExhibitionLike exhibitionLike : exhibitionLikeList) {
            if(exhibitionLike.getMember().getId() == memberId)
                return true;
        }
        return false;
    }

    private ExhibitionLike findExhibitionLikeByMemberIdAndExhibitionId(long memberId, long exhibitionId) {
        Optional<ExhibitionLike> exhibitionLike = findOptionalExhibitionLike(memberId, exhibitionId);
        return exhibitionLike.orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_EXHIBITIONLIKE));
    }

    private ExhibitionHope findExhibitionHopeByMemberIdAndExhibitionHope(long memberId, long exhibitionId) {
        Optional<ExhibitionHope> exhibitionHope = findOptionalExhibitionHope(memberId, exhibitionId);
        return exhibitionHope.orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_EXHIBITIONHOPE));
    }


    private Optional<ExhibitionLike> findOptionalExhibitionLike(long memberId, long exhibitionId) {
       return exhibitionLikeRepository.findExhibitionLikeByMemberIdAndExhibitionId(memberId, exhibitionId);
    }

    private Optional<ExhibitionHope> findOptionalExhibitionHope(long memberId, long exhibitionId) {
        return exhibitionHopeRepository.findExhibitionHopeByMemberIdAndExhibitionId(memberId, exhibitionId);
    }

    private Optional<ExhibitionView> findOptionalExhibitionView(long memberId, long exhibitionId) {
        return exhibitionViewRepository.findExhibitionViewByMemberIdAndExhibitionId(memberId, exhibitionId);
    }

}
