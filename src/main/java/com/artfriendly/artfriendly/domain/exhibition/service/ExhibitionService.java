package com.artfriendly.artfriendly.domain.exhibition.service;

import com.artfriendly.artfriendly.domain.exhibition.dto.ExhibitionDetailsRspDto;
import com.artfriendly.artfriendly.domain.exhibition.dto.ExhibitionRankRspDto;
import com.artfriendly.artfriendly.domain.exhibition.dto.ExhibitionRspDto;
import com.artfriendly.artfriendly.domain.exhibition.entity.Exhibition;
import com.artfriendly.artfriendly.domain.exhibition.entity.ExhibitionInfo;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ExhibitionService {
    void createExhibition(ExhibitionInfo exhibitionInfo);

    void createExhibitionList(List<ExhibitionInfo> exhibitionInfoList);

    Page<ExhibitionRspDto> getExhibitionPageRspDto(long memberId, int page, String area, String progressStatus, String sortType);

    ExhibitionDetailsRspDto getExhibitionDetailsRpsDtoById(long exhibitionId, long memberId);

    Exhibition findExhibitionById(long exhibitionId);

    void addExhibitionLike(long memberId, long exhibitionId);

    @Transactional
    void deleteExhibitionLike(long memberId, long exhibitionId);

    @Transactional
    void addExhibitionHope(long memberId, long exhibitionId, int hopeIndex);

    @Transactional
    void updateExhibitionHope(long memberId, long exhibitionId, int hopeIndex);

    @Transactional
    void deleteExhibitionHope(long memberId, long exhibitionId);

    @Transactional
    void updateExhibitionTemperature(long exhibitionId);

    @Transactional
    void addExhibitionView(long memberId, long exhibitionId);

    List<ExhibitionRspDto> getTop3ExhibitionsByEndingDate(long memberId);

    List<ExhibitionRankRspDto> getTop10PopularExhibitionRankRspDto();

    void updateTop10PopularExhibitionRankRspDto();

    void clearPopularExhibitionCache();

    void updateExhibitionList(List<ExhibitionInfo> updateExhibitionInfoList);
}
