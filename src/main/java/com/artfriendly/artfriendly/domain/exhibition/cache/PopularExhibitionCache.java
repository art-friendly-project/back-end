package com.artfriendly.artfriendly.domain.exhibition.cache;

import com.artfriendly.artfriendly.domain.exhibition.dto.ExhibitionRankRspDto;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Component
public class PopularExhibitionCache {
    private final Cache<Long, ExhibitionRankRspDto> popularExhibition = Caffeine.newBuilder()
            .initialCapacity(10)
            .maximumSize(10)
            .build();

    private void putExhibitionRankRspDtoInCache(ExhibitionRankRspDto exhibitionRankRspDto) {
        popularExhibition.put(exhibitionRankRspDto.exhibitionId(), exhibitionRankRspDto);
    }

    public void putExhibitionRankRspDtoListInCache(List<ExhibitionRankRspDto> exhibitionRankRspDtoList) {
        for(ExhibitionRankRspDto exhibitionRankRspDto : exhibitionRankRspDtoList) {
            putExhibitionRankRspDtoInCache(exhibitionRankRspDto);
        }
    }

    public ExhibitionRankRspDto getExhibitionRankRspDto(long exhibitionId) {
        return popularExhibition.getIfPresent(exhibitionId);
    }

    public List<ExhibitionRankRspDto> getExhibitionRankRspDtoList() {
        Map<Long, ExhibitionRankRspDto> map = popularExhibition.asMap();
        List<ExhibitionRankRspDto> exhibitionRankRspDtoList = new ArrayList<>(map.values());
        exhibitionRankRspDtoList.sort(Comparator.comparing(ExhibitionRankRspDto::rank));
        return exhibitionRankRspDtoList;
    }

    public void clearPopularExhibitionCache() {
        popularExhibition.cleanUp();
    }
}
