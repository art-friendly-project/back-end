package com.artfriendly.artfriendly.domain.exhibition.mapper;

import com.artfriendly.artfriendly.domain.exhibition.dto.ExhibitionDetailsRspDto;
import com.artfriendly.artfriendly.domain.exhibition.dto.ExhibitionInfoRspDto;
import com.artfriendly.artfriendly.domain.exhibition.dto.ExhibitionRankRspDto;
import com.artfriendly.artfriendly.domain.exhibition.dto.ExhibitionRspDto;
import com.artfriendly.artfriendly.domain.exhibition.entity.Exhibition;
import com.artfriendly.artfriendly.domain.exhibition.entity.ExhibitionInfo;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExhibitionMapper {
    default ExhibitionDetailsRspDto exhibitionToExhibitionDetailsRspDto(Exhibition exhibition, String checkTemperature, boolean isLike, boolean hasDambyeolagBeenWritten) {
        return new ExhibitionDetailsRspDto(
                exhibition.getId(),
                exhibition.getTemperature(),
                checkTemperature,
                isLike,
                hasDambyeolagBeenWritten,
                exhibitionInfoToExhibitionInfoRspDto(exhibition.getExhibitionInfo())
        );
    }

    default Page<ExhibitionRspDto> exhibitionPageToExhibitionRspDto(Page<Exhibition> exhibitionPage, long memberId) {
        return exhibitionPage.map(exhibition -> new ExhibitionRspDto(
                exhibition.getId(),
                exhibition.getExhibitionInfo().getTitle(),
                exhibition.getExhibitionInfo().getImageUrl(),
                exhibition.getTemperature(),
                exhibition.getExhibitionInfo().getStartDate(),
                exhibition.getExhibitionInfo().getEndDate(),
                exhibition.getExhibitionInfo().getArea(),
                exhibition.getExhibitionLikeList().stream().anyMatch(exhibitionLike -> exhibitionLike.getMember().getId() == memberId)
        ));
    }

    default List<ExhibitionRspDto> exhibitionsToExhibitionRspDtos(List<Exhibition> exhibitions, long memberId) {
        return exhibitions.stream().map(exhibition -> new ExhibitionRspDto(
                exhibition.getId(),
                exhibition.getExhibitionInfo().getTitle(),
                exhibition.getExhibitionInfo().getImageUrl(),
                exhibition.getTemperature(),
                exhibition.getExhibitionInfo().getStartDate(),
                exhibition.getExhibitionInfo().getEndDate(),
                exhibition.getExhibitionInfo().getArea(),
                exhibition.getExhibitionLikeList().stream().anyMatch(exhibitionLike -> exhibitionLike.getMember().getId() == memberId)
        )).toList();
    }

    default ExhibitionRankRspDto exhibitionToExhibitionRankRspDto(Exhibition exhibition, int rank, String rankShift) {
        return new ExhibitionRankRspDto(
                exhibition.getId(),
                rank,
                exhibition.getExhibitionInfo().getTitle(),
                exhibition.getExhibitionInfo().getImageUrl(),
                exhibition.getExhibitionInfo().getStartDate(),
                exhibition.getExhibitionInfo().getEndDate(),
                exhibition.getExhibitionInfo().getPlace(),
                exhibition.getExhibitionInfo().getArea(),
                rankShift
        );
    }

    ExhibitionInfoRspDto exhibitionInfoToExhibitionInfoRspDto(ExhibitionInfo exhibitionInfo);
}
